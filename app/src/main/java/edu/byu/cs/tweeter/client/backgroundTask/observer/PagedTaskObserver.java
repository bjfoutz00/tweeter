package edu.byu.cs.tweeter.client.backgroundTask.observer;

import java.util.List;

public interface PagedTaskObserver<T> extends ServiceObserver {
    void handleSuccess(List<T> items, boolean hasMorePages);
    void displayMessage(String message);
}