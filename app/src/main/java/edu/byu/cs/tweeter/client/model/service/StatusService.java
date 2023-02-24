package edu.byu.cs.tweeter.client.model.service;


import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.PagedTaskHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.SimpleTaskHandler;
import edu.byu.cs.tweeter.client.backgroundTask.observer.PagedTaskObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleTaskObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService {
    public void getFeedStatuses(User user, int pageSize, Status lastStatus, PagedTaskObserver<Status> observer) {
        GetFeedTask getFeedTask = new GetFeedTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastStatus, new PagedTaskHandler<>(observer));
        TaskExecutor.executeTask(getFeedTask);
    }

    public void getStoryStatuses(User user, int pageSize, Status lastStatus, PagedTaskObserver<Status> observer) {
        GetStoryTask getStoryTask = new GetStoryTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastStatus, new PagedTaskHandler<>(observer));
        TaskExecutor.executeTask(getStoryTask);
    }

    public void postStatus(Status newStatus, SimpleTaskObserver observer) {
        PostStatusTask statusTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
                newStatus, new SimpleTaskHandler(observer));
        TaskExecutor.executeTask(statusTask);
    }
}
