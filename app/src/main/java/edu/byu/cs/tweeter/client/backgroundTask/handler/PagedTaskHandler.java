package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.PagedTaskObserver;

public class PagedTaskHandler<T> extends BackgroundTaskHandler<PagedTaskObserver<T>>{
    public PagedTaskHandler(PagedTaskObserver<T> observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, PagedTaskObserver<T> observer) {
        List<T> items = (List<T>) data.getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(PagedTask.MORE_PAGES_KEY);
        observer.handleSuccess(items, hasMorePages);
    }
}
