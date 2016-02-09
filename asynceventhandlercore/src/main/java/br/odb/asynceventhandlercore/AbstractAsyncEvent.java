package br.odb.asynceventhandlercore;

/**
 * Created by monty on 09/02/16.
 */
public abstract class AbstractAsyncEvent implements AsyncEvent  {

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
