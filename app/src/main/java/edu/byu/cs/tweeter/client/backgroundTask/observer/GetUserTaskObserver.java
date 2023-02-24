package edu.byu.cs.tweeter.client.backgroundTask.observer;

import edu.byu.cs.tweeter.client.presenter.views.PagedView;
import edu.byu.cs.tweeter.model.domain.User;

public class GetUserTaskObserver<T> extends DisplayMessageObserver implements UserTaskObserver {
    public GetUserTaskObserver(PagedView<T> view) {
        super(view, "Failed to get user's profile");
    }

    @Override
    public void handleSuccess(User user) {
        ((PagedView<T>) view).startUserActivity(user);
    }
}
