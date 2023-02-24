package edu.byu.cs.tweeter.client.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.observer.CountTaskObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.DisplayMessageObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.IsFollowerTaskObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.MainStatusObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleTaskObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.presenter.views.MainView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter {
    private static final String LOG_TAG = "MainActivity";

    private MainView view;
    private FollowService followService;
    private UserService userService;
    private StatusService statusService;
    private User selectedUser;

    public MainPresenter(MainView view) {
        this.view = view;
        followService = new FollowService();
        userService = new UserService();
        statusService = new StatusService();
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;

        if (selectedUser == null) {
            throw new RuntimeException("User not passed to activity");
        }
    }

    public void updateSelectedUserFollowingAndFollowers() {
        followService.updateSelectedUserFollowingAndFollowers(selectedUser, new GetFollowersCountObserver(view), new GetFollowingCountObserver(view));
    }

    public void determineIsFollower() {
        if (selectedUser.compareTo(Cache.getInstance().getCurrUser()) == 0) {
            view.setFollowButtonVisibility(false);
        } else {
            view.setFollowButtonVisibility(true);
            followService.determineIsFollower(selectedUser, new IsFollowerObserver(view));
        }
    }

    public void onFollowButtonClick(String followButtonText, String following) {
        view.enableFollowButton(false);
        if (followButtonText.equals(following)) {
            followService.unfollowUser(selectedUser, new UnfollowObserver(view));
            view.displayMessage("Removing " + selectedUser.getName() + "...");
        } else {
            followService.followUser(selectedUser, new FollowObserver(view));
            view.displayMessage("Adding " + selectedUser.getName() + "...");
        }
    }

    public void logout() {
        userService.logout(new LogoutObserver(view));
    }

    public void postStatus(String post) {
        try {
            view.displayMessage("Posting Status...");
            Status newStatus = new Status(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post));
            getStatusService().postStatus(newStatus, getStatusObserver());

        } catch (Exception ex) {
//            Log.e(LOG_TAG, ex.getMessage(), ex);
            view.displayMessage("Failed to post status because of exception: " + ex.getMessage());
        }
    }

    public MainStatusObserver getStatusObserver() {
        return new MainStatusObserver(view);
    }

    public StatusService getStatusService() {
        return statusService;
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

    public void setIsFollower(boolean isFollower) {
        view.setFollowButton(isFollower);
    }

    private class FollowObserver extends DisplayMessageObserver implements SimpleTaskObserver {
        public FollowObserver(Presenter.View view) {
            super(view, "Failed to follow user");
        }

        @Override
        public void handleSuccess() {
            updateSelectedUserFollowingAndFollowers();
            setIsFollower(true);
            view.enableFollowButton(true);
        }
    }

    private class UnfollowObserver extends DisplayMessageObserver implements SimpleTaskObserver {
        public UnfollowObserver(Presenter.View view) {
            super(view, "Failed to unfollow user");
        }

        @Override
        public void handleSuccess() {
            updateSelectedUserFollowingAndFollowers();
            setIsFollower(false);
            view.enableFollowButton(true);
        }
    }

    private class IsFollowerObserver extends DisplayMessageObserver implements IsFollowerTaskObserver {
        public IsFollowerObserver(Presenter.View view) {
            super(view, "Failed to verify if following user");
        }

        @Override
        public void handleSuccess(boolean isFollower) {
            setIsFollower(true);
        }
    }

    private class GetFollowingCountObserver extends DisplayMessageObserver implements CountTaskObserver {
        public GetFollowingCountObserver(Presenter.View view) {
            super(view, "Failed to get following count");
        }

        @Override
        public void handleSuccess(int count) {
            view.setFolloweeCount(count);
        }
    }

    private class GetFollowersCountObserver extends DisplayMessageObserver implements CountTaskObserver {
        public GetFollowersCountObserver(Presenter.View view) {
            super(view, "Failed to get followers count");
        }

        @Override
        public void handleSuccess(int count) {
            view.setFollowerCount(count);
        }
    }

    private class LogoutObserver extends DisplayMessageObserver implements SimpleTaskObserver {
        public LogoutObserver(Presenter.View view) {
            super(view, "Failed to logout user");
        }

        @Override
        public void handleSuccess() {
            view.logoutUser();
        }
    }
}
