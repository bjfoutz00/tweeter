package edu.byu.cs.tweeter.client.backgroundTask.observer;

import edu.byu.cs.tweeter.client.presenter.PagedView;
import edu.byu.cs.tweeter.client.presenter.Presenter;
import edu.byu.cs.tweeter.model.domain.User;

public class UserTaskObserver<T> implements ServiceObserver {
    PagedView<T> view;

    public UserTaskObserver(PagedView<T> view) {
        this.view = view;
    }
    public void handleSuccess(User user) {
        view.startUserActivity(user);
    }

    @Override
    public void handleFailure(String message) {
        view.displayMessage("Failed to get user's profile: " + message);
    }

    @Override
    public void handleException(Exception ex) {
        view.displayMessage("Failed to get user's profile because of exception: " + ex.getMessage());
    }
}
