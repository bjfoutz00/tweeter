package edu.byu.cs.tweeter.client.presenter;


import edu.byu.cs.tweeter.client.backgroundTask.observer.GetPageTaskObserver;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.presenter.views.PagedView;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends PagedPresenter<User> {

    private FollowService followService;

    public FollowersPresenter(PagedView<User> view) {
        super(view);
        followService = new FollowService();
    }

    @Override
    protected void getItems(User user) {
        followService.getFollowers(user, PAGE_SIZE, getLastItem(),
                new GetPageTaskObserver<>(this, ((PagedView<User>) view), getErrorPrefix()));
    }

    @Override
    protected String getErrorPrefix() {
        return "Failed to get followers";
    }
}
