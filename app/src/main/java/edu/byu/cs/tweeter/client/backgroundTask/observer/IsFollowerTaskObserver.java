package edu.byu.cs.tweeter.client.backgroundTask.observer;

public interface IsFollowerTaskObserver extends ServiceObserver {
    void handleSuccess(boolean isFollower);
}
