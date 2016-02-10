package br.odb.asynceventhandlercore;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
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
}