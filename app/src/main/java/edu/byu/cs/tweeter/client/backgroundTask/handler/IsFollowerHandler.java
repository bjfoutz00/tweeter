package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.IsFollowerTaskObserver;

public class IsFollowerHandler extends BackgroundTaskHandler<IsFollowerTaskObserver> {

    public IsFollowerHandler(IsFollowerTaskObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, IsFollowerTaskObserver observer) {
        boolean isFollower = data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
        observer.handleSuccess(isFollower);
    }
}
