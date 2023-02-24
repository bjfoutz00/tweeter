package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.backgroundTask.observer.GetUserTaskObserver;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.presenter.views.PagedView;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends Presenter {
    public static final int PAGE_SIZE = 10;
    private T lastItem;
    private boolean hasMorePages;
    private boolean isLoading = false;
    private UserService userService;

    public PagedPresenter(PagedView<T> view) {
        super(view);
        this.userService = new UserService(); // might only need instance in getUser
    }

    public T getLastItem() {
        return lastItem;
    }
    public void setLastItem(T lastItem) {
        this.lastItem = lastItem;
    }
    public boolean hasMorePages() {
        return hasMorePages;
    }
    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }
    public boolean isLoading() {
        return isLoading;
    }
    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            ((PagedView<T>) view).setLoadingFooter(true);
            getItems(user);
        }
    }

    public void getUser(String userAlias) {
        userService.getUser(userAlias, new GetUserTaskObserver<>(((PagedView<T>) view)));
    }

    protected abstract void getItems(User user);
}
