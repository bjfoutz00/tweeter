package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.GetUserTaskObserver;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Message handler (i.e., observer) for GetUserTask.
 */
public class GetUserHandler extends BackgroundTaskHandler<GetUserTaskObserver> {

    public GetUserHandler(GetUserTaskObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, GetUserTaskObserver observer) {
        User user = (User) data.getSerializable(GetUserTask.USER_KEY);
        observer.handleSuccess(user);
    }
}
