package edu.byu.cs.tweeter.client.presenter.views;

import edu.byu.cs.tweeter.client.presenter.Presenter;

public interface MainView extends Presenter.View {
    void setFollowerCount(int count);
    void setFolloweeCount(int count);
    void setFollowButtonVisibility(boolean value);
    void setFollowButton(boolean isFollower);
    void enableFollowButton(boolean enable);
    void logoutUser();
    void onSuccessfulPost();
}