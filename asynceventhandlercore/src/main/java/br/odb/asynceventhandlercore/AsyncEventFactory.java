package br.odb.asynceventhandlercore;

/**
 * Created by monty on 09/02/16.
 */
public interface AsyncEventFactory {
    AsyncEvent requestCatUrl( EventResultCallback callback );
    AsyncEvent requestCatImage( String catLocation, EventResultCallback callback );
}
