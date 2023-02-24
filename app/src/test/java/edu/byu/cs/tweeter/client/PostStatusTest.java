package edu.byu.cs.tweeter.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.client.presenter.views.MainView;

public class PostStatusTest {
    MainPresenter spyPresenter;
    MainView mockView;
    StatusService mockStatusService;
    String post;

    @BeforeEach
    public void setUp() {
        mockStatusService = mock(StatusService.class);
        mockView = mock(MainView.class);

        MainPresenter presenter = new MainPresenter(mockView);
        spyPresenter = Mockito.spy(presenter);
        post = "Hello, world!";
    }

    @Test
    public void successfulPostStatus() {
        // need to stub out display message? or just verify was called
        // verify:
        // view.displayMessage (1) or (2) if failed
        // view.onSuccessfulPost (1) or (0) if failed
        // service.postStatus (1)

        spyPresenter.postStatus(post);

        String successMessage = "Posting Status...";
        verify(mockView).displayMessage(successMessage);
        verify(mockView).onSuccessfulPost();
    }

    @Test
    public void failedPostStatus() {

    }

    @Test
    public void failedExceptionPostStatus() {

    }
}
