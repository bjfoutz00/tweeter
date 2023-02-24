package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.AuthenticationTaskHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.LogoutHandler;
import edu.byu.cs.tweeter.client.backgroundTask.observer.AuthenticateUserTaskObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.GetUserTaskObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleTaskObserver;
import edu.byu.cs.tweeter.client.cache.Cache;

public class UserService {
    public void getUser(String userAlias, GetUserTaskObserver observer) {
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                userAlias, new GetUserHandler(observer));
        TaskExecutor.executeTask(getUserTask);
    }

    /**
     * Sends the login request
     */
    public void login(String alias, String password, AuthenticateUserTaskObserver observer) {
        LoginTask loginTask = new LoginTask(alias, password, new AuthenticationTaskHandler(observer));
        TaskExecutor.executeTask(loginTask);
    }

    /**
     * Sends the register request
     */
    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64, AuthenticateUserTaskObserver observer) {
        RegisterTask registerTask = new RegisterTask(firstName, lastName, alias, password,
                imageBytesBase64, new AuthenticationTaskHandler(observer));
        TaskExecutor.executeTask(registerTask);
    }

    public void logout(SimpleTaskObserver observer) {
        LogoutTask logoutTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new LogoutHandler(observer));
        TaskExecutor.executeTask(logoutTask);
    }
}
