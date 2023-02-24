package edu.byu.cs.tweeter.client.backgroundTask.observer;

import edu.byu.cs.tweeter.client.presenter.views.AuthenticateView;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthenticateUserTaskObserver extends DisplayMessageObserver implements UserTaskObserver {

    public AuthenticateUserTaskObserver(AuthenticateView view,  String errorPrefix) {
        super(view, errorPrefix);
    }

    @Override
    public void handleSuccess(User user) {
        ((AuthenticateView) view).startMainActivity(user);
    }
}
