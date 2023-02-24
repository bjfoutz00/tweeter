package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticationTask {

    private static final String LOG_TAG = "LoginTask";

    public LoginTask(String username, String password, Handler messageHandler) {
        super(messageHandler, username, password);
    }
}
