package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.backgroundTask.observer.GetPageTaskObserver;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.presenter.views.PagedView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status> {

    private StatusService statusService;

    public StoryPresenter(PagedView<Status> view) {
        super(view);
        statusService = new StatusService();
    }

    @Override
    protected void getItems(User user) {
        statusService.getStoryStatuses(user, PAGE_SIZE, getLastItem(),
                new GetPageTaskObserver<>(this, ((PagedView<Status>) view), getErrorPrefix()));
    }

    @Override
    protected String getErrorPrefix() {
        return "Failed to get story";
    }
}
