package br.odb.asynceventhandlercore.Factories;

import br.odb.asynceventhandlercore.AsyncEvent;
import br.odb.asynceventhandlercore.AsyncEventFactory;
import br.odb.asynceventhandlercore.EventResultCallback;
import br.odb.asynceventhandlercore.Requests.RequestCatUrlAsyncEventImp;
import br.odb.asynceventhandlercore.Requests.RequestCatImageAsyncEventImp;

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
