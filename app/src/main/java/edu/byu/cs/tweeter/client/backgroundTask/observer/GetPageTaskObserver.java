package edu.byu.cs.tweeter.client.backgroundTask.observer;

import java.util.List;

import edu.byu.cs.tweeter.client.presenter.PagedPresenter;
import edu.byu.cs.tweeter.client.presenter.views.PagedView;

public class GetPageTaskObserver<T> implements PagedTaskObserver<T> {
    PagedPresenter<T> presenter;
    PagedView<T> view;
    String errorPrefix;

    public GetPageTaskObserver(PagedPresenter<T> presenter, PagedView<T> view, String errorPrefix) {
        this.presenter = presenter;
        this.view = view;
        this.errorPrefix = errorPrefix;
    }

    @Override
    public void handleSuccess(List<T> items, boolean hasMorePages) {
        presenter.setHasMorePages(hasMorePages);
        presenter.setLoading(false);
        view.setLoadingFooter(false);
        presenter.setLastItem((items.size() > 0) ? items.get(items.size() - 1) : null);
        view.addMoreItems(items);
    }

    @Override
    public void handleFailure(String message) {
        view.displayMessage(errorPrefix + ": " + message);
    }

    @Override
    public void handleException(Exception ex) {
        view.displayMessage(errorPrefix + " because of exception: " + ex.getMessage());
    }

    @Override
    public void displayMessage(String message) {
        presenter.setLoading(false);
        view.setLoadingFooter(false);
        view.displayMessage(message);
    }
}
