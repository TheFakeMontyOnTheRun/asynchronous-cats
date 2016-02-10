package br.odb.asynceventhandlercore;

import java.util.Vector;

/**
 * Created by monty on 09/02/16.
 */
public class EventHandler implements Runnable {
    final Vector< AsyncEvent > events = new Vector<>();
    final Thread eventHandlerThread = new Thread( this );
    private boolean running = true;
    private AsyncEventFactory mEventFactory = new AsyncEventFactoryImpl();

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
                Thread.sleep( 1000 );
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
