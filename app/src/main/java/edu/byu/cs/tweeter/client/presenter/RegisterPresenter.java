package edu.byu.cs.tweeter.client.presenter;

import android.graphics.drawable.Drawable;

import edu.byu.cs.tweeter.client.backgroundTask.observer.AuthenticateUserTaskObserver;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.presenter.views.AuthenticateView;

public class RegisterPresenter extends AuthenticatePresenter {

    private UserService userService;

    public RegisterPresenter(View view) {
        super(view);
        userService = new UserService();
    }

    @Override
    protected String getErrorPrefix() {
        return "Failed to register";
    }

    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64) {
        userService.register(firstName, lastName, alias, password, imageBytesBase64, new AuthenticateUserTaskObserver(((AuthenticateView) view), getErrorPrefix()));
    }

    public void validateRegistration(String firstName, String lastName, String alias, String password, Drawable imageToUpload) {
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        validateAlias(alias);
        validatePassword(password);
        if (imageToUpload == null) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }
}
