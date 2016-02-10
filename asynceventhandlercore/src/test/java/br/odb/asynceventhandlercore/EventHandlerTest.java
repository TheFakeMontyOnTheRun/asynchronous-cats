package br.odb.asynceventhandlercore;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class EventHandlerTest {

    @Test
    public void testPushingEventWillEventuallyResultInItsExecution() throws InterruptedException {
        EventHandler handler = new EventHandler(0L);
        final EventResultCallback mockCallback = mock( EventResultCallback.class );
        final EventResponse mockResponse = mock( EventResponse.class );

        AbstractAsyncEvent goodEventToPush = new AbstractAsyncEvent( mockCallback ) {
            @Override
            public void perform() {
                reportSuccess(mockResponse);
            }
        };

        handler.startHandling();
        handler.pushEvent(goodEventToPush);
        Thread.sleep(10L);
        verify( mockCallback ).onSuccess(mockResponse);
    }

    @Test
    public void ensureFailedEventsWillReportTheirFailure() throws InterruptedException {
        EventHandler handler = new EventHandler(0L);
        final EventResultCallback mockCallback = mock( EventResultCallback.class );

        AbstractAsyncEvent goodEventToPush = new AbstractAsyncEvent( mockCallback ) {
            @Override
            public void perform() {
                reportFailure();
            }
        };

        handler.startHandling();
        handler.pushEvent(goodEventToPush);
        Thread.sleep(10L);
        verify( mockCallback ).onFailure();
    }

    @Test
    public void verifyStoppingAndStartingTheEventHandling() throws InterruptedException {

        final EventResultCallback mockCallback = mock( EventResultCallback.class );
        final EventResponse mockResponse = mock( EventResponse.class );

        AbstractAsyncEvent goodEventToPush = new AbstractAsyncEvent( mockCallback ) {
            @Override
            public void perform() {
                reportSuccess(mockResponse);
            }
        };

        AbstractAsyncEvent badEventToPush = new AbstractAsyncEvent( mockCallback ) {
            @Override
            public void perform() {
                reportFailure();
            }
        };


        EventHandler handler = new EventHandler(0L);
        handler.pushEvent(goodEventToPush);
        assertFalse(handler.isRunning());
        Thread.sleep(10L);
        verify( mockCallback, never() ).onSuccess(mockResponse);
        handler.startHandling();
        assertTrue(handler.isRunning());
        Thread.sleep(10L);
        verify( mockCallback ).onSuccess(mockResponse);
        handler.stopHandling();
        assertFalse(handler.isRunning());
        Thread.sleep(10L);
        handler.pushEvent(badEventToPush);
        Thread.sleep(10L);
        verify( mockCallback, never() ).onFailure();
    }
}