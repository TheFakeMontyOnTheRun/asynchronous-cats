package br.odb.asynceventhandlercore;

import java.util.Vector;

/**
 * Created by monty on 09/02/16.
 */
public class EventHandler {
    private final Vector< AsyncEvent > events = new Vector<>();
    private final long mDesiredLatencyInMillis;
    private final AsyncEventFactory mEventFactory = new AsyncEventFactoryImpl();

    private final Thread eventHandlerThread = new Thread(new Runnable() {
        @Override
        public void run() {

            while( true ) {
                try {
                    if ( running ) {
                        consumeFromQueue();
                    }
                    Thread.sleep(mDesiredLatencyInMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    private boolean running = true;

    public EventHandler( long desiredLatency ) {
        mDesiredLatencyInMillis = desiredLatency;
        eventHandlerThread.start();
    }

    public void startHandling() {
        running = true;
    }

    public void stopHandling() {
        running = false;
    }

    private void consumeFromQueue() {
        if ( !events.isEmpty() ) {
            AsyncEvent toConsume = events.firstElement();
            events.removeElementAt(0);
            toConsume.perform();
        }
    }

    public void pushEvent( AsyncEvent event ) {
        events.add( event );
    }

    public AsyncEventFactory getFactory() {
        return mEventFactory;
    }

    public boolean isRunning() {
        return running;
    }
}
