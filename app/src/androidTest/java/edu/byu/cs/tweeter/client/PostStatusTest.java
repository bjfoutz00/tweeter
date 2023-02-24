package edu.byu.cs.tweeter.client;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.text.ParseException;
import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.observer.MainStatusObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.client.presenter.views.MainView;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusTest {
    MainPresenter spyPresenter;
    MainView mockView;
    StatusService mockStatusService;
    Status status;
    MainStatusObserver observer;
    String post;
    String postingMessage;

    @BeforeEach
    public void setUp() throws ParseException {
        mockStatusService = mock(StatusService.class);
        mockView = mock(MainView.class);
        spyPresenter = Mockito.spy(new MainPresenter(mockView));
        post = "My friend @bob likes this website https://byu.edu";
        postingMessage = "Posting Status...";

        String dateTime = "07/04/1776";
        List<String> urls = List.of("https://byu.edu");
        List<String> mentions = List.of("@bob");

        status = new Status(post, Cache.getInstance().getCurrUser(), dateTime, urls, mentions);
        observer = new MainStatusObserver(mockView);

        doReturn(mockStatusService).when(spyPresenter).getStatusService();
        doReturn(dateTime).when(spyPresenter).getFormattedDateTime();
        doReturn(observer).when(spyPresenter).getStatusObserver();
    }

    @Test
    public void successfulPostStatus() throws ParseException {
        spyPresenter.postStatus(post);

        // todo: see if need to call this or if there's some way to make mockStatusService call it
        observer.handleSuccess();

        verify(mockView).displayMessage(postingMessage);
        verify(mockView).onSuccessfulPost();

        // checks that postStatus was called with exact properties of status
        verify(mockStatusService).postStatus(status, observer);
    }

    @Test
    public void failedPostStatus() {
        String failureMessage = "test failure";

        spyPresenter.postStatus(post);
        observer.handleFailure(failureMessage);

        verify(mockView).displayMessage(postingMessage);
        verify(mockView).displayMessage("Failed to post status: " + failureMessage);

        // checks that postStatus was called with exact properties of status
        verify(mockStatusService).postStatus(status, observer);
    }

    @Test
    public void failedExceptionPostStatus() throws ParseException {
        String exceptionMessage = "Invalid date: test";
        when(spyPresenter.getFormattedDateTime()).thenThrow(new ParseException(exceptionMessage, 0));

        spyPresenter.postStatus(post);

        verify(mockView).displayMessage(postingMessage);
        verify(mockView).displayMessage("Failed to post status because of exception: " + exceptionMessage);
        verifyNoInteractions(mockStatusService);
    }
}
