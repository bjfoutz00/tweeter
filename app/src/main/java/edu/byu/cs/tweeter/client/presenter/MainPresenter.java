package edu.byu.cs.tweeter.client.presenter;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter {
    private static final String LOG_TAG = "MainActivity";

    public interface View {
        void displayMessage(String message);
        void setFollowerCount(int count);
        void setFolloweeCount(int count);
        void setFollowButtonVisibility(boolean value);
        void setFollowButton(boolean isFollower);
        void enableFollowButton(boolean enable);
        void logoutUser();
        void onSuccessfulPost();
    }

    private View view;
    private FollowService followService;
    private UserService userService;
    private StatusService statusService;

    public MainPresenter(View view) {
        this.view = view;
        followService = new FollowService();
        userService = new UserService();
        statusService = new StatusService();
    }

    public void updateSelectedUserFollowingAndFollowers(User selectedUser) {
        followService.updateSelectedUserFollowingAndFollowers(selectedUser, new MainFollowObserver());
    }

    public void determineIsFollower(User selectedUser) {
        if (selectedUser.compareTo(Cache.getInstance().getCurrUser()) == 0) {
            view.setFollowButtonVisibility(false);
        } else {
            view.setFollowButtonVisibility(true);
            followService.determineIsFollower(selectedUser, new MainFollowObserver());
        }
    }

    public void onFollowButtonClick(User selectedUser, String followButtonText, String following) {
        view.enableFollowButton(false);
        if (followButtonText.equals(following)) {
            followService.unfollowUser(selectedUser, new MainFollowObserver());
            view.displayMessage("Removing " + selectedUser.getName() + "...");
        } else {
            followService.followUser(selectedUser, new MainFollowObserver());
            view.displayMessage("Adding " + selectedUser.getName() + "...");
        }
    }

    public void logout() {
        userService.logout(new MainUserObserver());
    }

    public void onStatusPosted(String post) {
        try {
            Status newStatus = new Status(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post));
            statusService.onStatusPosted(newStatus, new MainStatusObserver());

        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            view.displayMessage("Failed to post the status because of exception: " + ex.getMessage());
        }
    }

    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }


    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);
                containedMentions.add(word);
            }
        }
        return containedMentions;
    }

    public List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {
                int index = findUrlEndIndex(word);
                word = word.substring(0, index);
                containedUrls.add(word);
            }
        }
        return containedUrls;
    }

    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    private class MainFollowObserver implements FollowService.MainObserver {
        @Override
        public void displayMessage(String message) {
            view.displayMessage(message);
        }

        @Override
        public void addMoreItems(List<User> items, boolean hasMorePages) {

        }

        @Override
        public void setFollowerCount(int count) {
            view.setFollowerCount(count);
        }

        @Override
        public void setFolloweeCount(int count) {
            view.setFolloweeCount(count);
        }

        @Override
        public void setIsFollower(boolean isFollower) {
            view.setFollowButton(isFollower);
        }

        @Override
        public void enableFollowButton(boolean enable) {
            view.enableFollowButton(enable);
        }
    }

    private class MainUserObserver implements UserService.MainObserver {
        @Override
        public void logoutUser() {
            view.logoutUser();
        }

        @Override
        public void displayMessage(String message) {
            view.displayMessage(message);
        }
    }

    private class MainStatusObserver implements StatusService.MainObserver  {
        @Override
        public void displayMessage(String message) {
            view.displayMessage(message);
        }

        @Override
        public void onSuccessfulPost() {
            view.onSuccessfulPost();
        }
    }
}
