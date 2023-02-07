package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter {
    private static final int PAGE_SIZE = 10;

    public interface View {
        void setLoadingFooter(boolean value);
        void displayMessage(String message);
        void addMoreItems(List<User> followers);
        void startUserActivity(User user);
    }

    private View view;
    private FollowService followService;
    private UserService userService;
    private User lastFollower;
    private boolean hasMorePages;
    private boolean isLoading = false;

    public FollowersPresenter(View view) {
        this.view = view;
        followService = new FollowService();
        userService = new UserService();
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }
    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }
    public boolean isLoading() {
        return isLoading;
    }
    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void loadMoreFollowers(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            followService.loadMoreFollowers(user, PAGE_SIZE, lastFollower, new GetFollowersObserver());
        }
    }

    public void onUserClick(String userAlias) {
        userService.onUserClick(userAlias, new GetUserObserver());
    }

    private class GetFollowersObserver implements FollowService.Observer {
        @Override
        public void addMoreItems(List<User> followers, boolean hasMorePages) {
            setHasMorePages(hasMorePages);
            setLoading(false);
            view.setLoadingFooter(false);
            lastFollower = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
            view.addMoreItems(followers);
        }

        @Override
        public void displayMessage(String message) {
            setLoading(false);
            view.setLoadingFooter(false);
            view.displayMessage(message);
        }
    }

    private class GetUserObserver implements UserService.Observer {
        @Override
        public void startActivity(User user) {
            view.startUserActivity(user);
        }

        @Override
        public void displayMessage(String message) {
            view.displayMessage(message);
        }
    }
}
