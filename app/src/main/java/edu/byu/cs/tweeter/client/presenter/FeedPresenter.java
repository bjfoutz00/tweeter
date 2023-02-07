package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter {
    private static final int PAGE_SIZE = 10;

    public interface View {
        void setLoadingFooter(boolean value);
        void displayMessage(String message);
        void addMoreItems(List<Status> statuses);
        void startUserActivity(User user);
    }

    private View view;
    private UserService userService;
    private StatusService statusService;
    private Status lastStatus;
    private boolean hasMorePages;
    private boolean isLoading = false;

    public FeedPresenter(View view) {
        this.view = view;
        userService = new UserService();
        statusService = new StatusService();
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
    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void loadMoreStatuses(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            statusService.loadMoreFeedStatuses(user, PAGE_SIZE, lastStatus, new GetStatusObserver());
        }
    }

    public void onStatusClick(String userAlias) {
        userService.onUserClick(userAlias, new GetUserObserver());
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

    private class GetStatusObserver implements StatusService.Observer {
        @Override
        public void displayMessage(String message) {
            setLoading(false);
            view.setLoadingFooter(false);
            view.displayMessage(message);
        }

        @Override
        public void addMoreItems(List<Status> statuses, boolean hasMorePages) {
            setHasMorePages(hasMorePages);
            setLoading(false);
            view.setLoadingFooter(false);
            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            view.addMoreItems(statuses);
        }
    }
}
