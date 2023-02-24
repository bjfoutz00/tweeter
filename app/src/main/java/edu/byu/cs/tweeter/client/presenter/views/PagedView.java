package edu.byu.cs.tweeter.client.presenter.views;

import java.util.List;

import edu.byu.cs.tweeter.client.presenter.Presenter;
import edu.byu.cs.tweeter.model.domain.User;

public interface PagedView<T> extends Presenter.View {
    void setLoadingFooter(boolean value);
    void addMoreItems(List<T> items);
    void startUserActivity(User user);
}
