package br.odb.asynceventhandlercore;

/**
 * Created by monty on 09/02/16.
 */
public abstract class AbstractAsyncEvent implements AsyncEvent  {

    final EventResultCallback mResultCallback;

    AbstractAsyncEvent( EventResultCallback callback ) {
        mResultCallback = callback;
    }
}
