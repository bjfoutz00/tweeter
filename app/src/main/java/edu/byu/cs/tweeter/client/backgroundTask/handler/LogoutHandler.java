package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleTaskObserver;
import edu.byu.cs.tweeter.client.cache.Cache;

public class LogoutHandler extends BackgroundTaskHandler<SimpleTaskObserver> {

    public LogoutHandler(SimpleTaskObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, SimpleTaskObserver observer) {
        // Clear user data (cached data).
        Cache.getInstance().clearCache();
        observer.handleSuccess();
    }
}
