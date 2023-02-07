package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter {
    public interface View {
        void setErrorText(String text);
        void displayMessage(String message);
        void displayLoginMessage();
        void startMainActivity(User loggedInUser);
    }

    private View view;
    private UserService userService;

    public LoginPresenter(View view) {
        this.view = view;
        userService = new UserService();
    }

    public void onLoginClick(String alias, String password) {
        try {
            validateLogin(alias, password);

            view.setErrorText(null);
            view.displayLoginMessage();

            userService.onLoginClick(alias, password, new LoginObserver());

        } catch (Exception e) {
            view.setErrorText(e.getMessage());
        }
    }

    public void validateLogin(String alias, String password) {
        if (alias.length() > 0 && alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }

    private class LoginObserver implements UserService.Observer {
        @Override
        public void startActivity(User user) {
            view.startMainActivity(user);
        }

        @Override
        public void displayMessage(String message) {
            view.displayMessage(message);
        }
    }
}
