package br.odb.asynceventhandlercore;

/**
 * Created by monty on 09/02/16.
 */
public class AsyncEventFactoryImpl implements AsyncEventFactory {

    @Override
    public AsyncEvent requestCatUrl(EventResultCallback callback) {
        return new RequestCatUrlAsyncEventImp( callback );
    }

    @Override
    public AsyncEvent requestCatImage(String catLocation, EventResultCallback callback) {
        return new RequestCatImageAsyncEventImp( catLocation, callback );
    }
}
