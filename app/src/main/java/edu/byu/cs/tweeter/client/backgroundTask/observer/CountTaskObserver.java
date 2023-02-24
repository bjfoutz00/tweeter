package edu.byu.cs.tweeter.client.backgroundTask.observer;

public interface CountTaskObserver extends ServiceObserver {
    void handleSuccess(int count);
}
