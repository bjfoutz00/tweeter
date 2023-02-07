package edu.byu.cs.tweeter.client.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.domain.User;
import android.view.View;

public class GetFollowingPresenter {
    private static final int PAGE_SIZE = 10;

    public interface View {
        void setLoadingFooter(boolean value);
        void displayMessage(String message);
        void addMoreItems(List<User> followees);
        void startUserActivity(User user);
    }

    private View view;
    private FollowService followService;
    private UserService userService;
    private User lastFollowee;
    private boolean hasMorePages;
    private boolean isLoading = false;

    public GetFollowingPresenter(View view) {
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

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            followService.loadMoreItems(user, PAGE_SIZE, lastFollowee, new GetFollowingObserver());
        }
    }

    public void onUserClick(String userAlias) {
        userService.onUserClick(userAlias, new GetUserObserver());
    }

    private class GetFollowingObserver implements FollowService.Observer {
        @Override
        public void addFollowees(List<User> followees, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            lastFollowee = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
            setHasMorePages(hasMorePages);
            view.addMoreItems(followees);
        }

        @Override
        public void displayMessage(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage(message);
        }
    }

    private class GetUserObserver implements UserService.Observer {
        @Override
        public void startUserActivity(User user) {
            view.startUserActivity(user);
        }

        @Override
        public void displayMessage(String message) {
            view.displayMessage(message);
        }
    }

}
