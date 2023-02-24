package edu.byu.cs.tweeter.client.presenter.views;

import edu.byu.cs.tweeter.client.presenter.Presenter;
import edu.byu.cs.tweeter.model.domain.User;

public interface AuthenticateView extends Presenter.View {
    void startMainActivity(User authenticatedUser);
}
