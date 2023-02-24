package edu.byu.cs.tweeter.client.model.service;

import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.client.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.CountTaskHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.PagedTaskHandler;
import edu.byu.cs.tweeter.client.backgroundTask.handler.SimpleTaskHandler;
import edu.byu.cs.tweeter.client.backgroundTask.observer.CountTaskObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.IsFollowerTaskObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.PagedTaskObserver;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleTaskObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {
    public void getFollowees(User user, int pageSize, User lastFollowee, PagedTaskObserver<User> observer) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollowee, new PagedTaskHandler<>(observer));
        TaskExecutor.executeTask(getFollowingTask);
    }

    public void getFollowers(User user, int pageSize, User lastFollower, PagedTaskObserver<User> observer) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollower, new PagedTaskHandler<>(observer));
        TaskExecutor.executeTask(getFollowersTask);
    }

    public void updateSelectedUserFollowingAndFollowers(User selectedUser,
            CountTaskObserver followersCountObserver, CountTaskObserver followingCountObserver) {
        // Get count of most recently selected user's followers.
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new CountTaskHandler(followersCountObserver));

        // Get count of most recently selected user's followees (who they are following)
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new CountTaskHandler(followingCountObserver));

        List<BackgroundTask> tasks = Arrays.asList(followersCountTask, followingCountTask);
        TaskExecutor.executeTasks(tasks);
    }

    public void determineIsFollower(User selectedUser, IsFollowerTaskObserver observer) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerHandler(observer));
        TaskExecutor.executeTask(isFollowerTask);
    }

    public void unfollowUser(User selectedUser, SimpleTaskObserver observer) {
        UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new SimpleTaskHandler(observer));
        TaskExecutor.executeTask(unfollowTask);
    }

    public void followUser(User selectedUser, SimpleTaskObserver observer) {
        FollowTask followTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new SimpleTaskHandler(observer));
        TaskExecutor.executeTask(followTask);
    }
}

