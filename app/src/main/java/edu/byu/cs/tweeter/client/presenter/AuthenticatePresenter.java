package edu.byu.cs.tweeter.client.presenter;

public abstract class AuthenticatePresenter extends Presenter {

    public AuthenticatePresenter(View view) {
        super(view);
    }

    protected void validateAlias(String alias) {
        if (alias.length() == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
    }

    protected void validatePassword(String password) {
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }
}
