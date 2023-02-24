package edu.byu.cs.tweeter.client.backgroundTask.observer;

import edu.byu.cs.tweeter.client.presenter.Presenter;

public class DisplayMessageObserver implements ServiceObserver {
    String errorPrefix;
    Presenter.View view;

    public DisplayMessageObserver(Presenter.View view, String errorPrefix) {
        this.errorPrefix = errorPrefix;
        this.view = view;
    }

    public void displayMessage(String message) {
        view.displayMessage(message);
    }

    @Override
    public void handleFailure(String message) {
        view.displayMessage(errorPrefix + ": " + message);
    }

    @Override
    public void handleException(Exception ex) {
        view.displayMessage(errorPrefix + " because of exception: " + ex.getMessage());
    }
}
