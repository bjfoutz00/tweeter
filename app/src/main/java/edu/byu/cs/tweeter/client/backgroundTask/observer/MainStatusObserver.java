package edu.byu.cs.tweeter.client.backgroundTask.observer;

import edu.byu.cs.tweeter.client.presenter.Presenter;
import edu.byu.cs.tweeter.client.presenter.views.MainView;

public class MainStatusObserver extends DisplayMessageObserver implements SimpleTaskObserver  {
    public MainStatusObserver(Presenter.View view) {
        super(view, "Failed to post status");
    }

    @Override
    public void handleSuccess() {
        ((MainView) view).onSuccessfulPost();
    }
}