package br.odb.asynceventhandlercore;

import java.util.Vector;

import br.odb.asynceventhandlercore.Factories.AsyncEventFactoryImpl;

/**
 * Created by monty on 09/02/16.
 */
public class EventHandler implements Runnable {
    private final Vector< AsyncEvent > events = new Vector<>();
    private final Thread eventHandlerThread = new Thread( this );
    private final long mDesiredLatencyInMilis;
    private final AsyncEventFactory mEventFactory = new AsyncEventFactoryImpl();

    private boolean running = true;

    public EventHandler(long desiredLatency) {
        mDesiredLatencyInMilis = desiredLatency;
    }

    public void startHandling() {
        running = true;
        eventHandlerThread.start();
    }

    void stopHandling() {
        running = false;
    }


    @Override
    public void run() {
        while( running ) {
            try {
                consumeFromQueue();
                Thread.sleep(mDesiredLatencyInMilis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
}
