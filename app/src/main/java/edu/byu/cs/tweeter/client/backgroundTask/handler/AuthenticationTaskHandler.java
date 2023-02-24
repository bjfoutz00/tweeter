package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.AuthenticationTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.AuthenticateUserTaskObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthenticationTaskHandler extends BackgroundTaskHandler<AuthenticateUserTaskObserver> {

    public AuthenticationTaskHandler(AuthenticateUserTaskObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, AuthenticateUserTaskObserver observer) {
        User authenticatedUser = (User) data.getSerializable(AuthenticationTask.USER_KEY);
        AuthToken authToken = (AuthToken) data.getSerializable(AuthenticationTask.AUTH_TOKEN_KEY);

        Cache.getInstance().setCurrUser(authenticatedUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);

        observer.handleSuccess(authenticatedUser);
    }
}
