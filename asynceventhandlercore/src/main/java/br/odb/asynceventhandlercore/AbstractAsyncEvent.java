package br.odb.asynceventhandlercore;

import br.odb.asynceventhandlercore.AsyncEvent;
import br.odb.asynceventhandlercore.EventResponse;
import br.odb.asynceventhandlercore.EventResultCallback;

/**
 * Created by monty on 09/02/16.
 */
public abstract class AbstractAsyncEvent implements AsyncEvent {

    final EventResultCallback mResultCallback;

    AbstractAsyncEvent( EventResultCallback callback ) {
        mResultCallback = callback;
    }

    void reportFailure() {
        if ( mResultCallback != null ) {
            mResultCallback.onFailure();
        }
    }

    void reportSuccess( EventResponse response ) {
        if ( mResultCallback != null ) {
            mResultCallback.onSuccess( response );
        }
    }
}
