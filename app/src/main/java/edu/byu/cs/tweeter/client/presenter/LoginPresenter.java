package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.backgroundTask.observer.AuthenticateUserTaskObserver;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.presenter.views.AuthenticateView;

public class LoginPresenter extends AuthenticatePresenter {

    private UserService userService;

    public LoginPresenter(AuthenticateView view) {
        super(view);
        userService = new UserService();
    }

    @Override
    protected String getErrorPrefix() {
        return "Failed to login";
    }

    public void login(String alias, String password) {
        userService.login(alias, password, new AuthenticateUserTaskObserver(((AuthenticateView) view), getErrorPrefix()));
    }

    public void validateLogin(String alias, String password) {
        validateAlias(alias);
        validatePassword(password);
    }
}
