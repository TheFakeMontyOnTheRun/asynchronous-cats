package br.odb.asynceventhandlercore;

import android.net.Uri;

/**
 * Created by monty on 09/02/16.
 */
public class AsyncEventFactoryImpl implements AsyncEventFactory {

    @Override
    public AsyncEvent requestCatUrl(EventResultCallback callback) {
        return new RequestCatUrlAsyncEventImp( callback );
    }

    @Override
    public AsyncEvent requestCatImage(Uri catLocation, EventResultCallback callback) {
        return new requestCatImage( catLocation, callback );
    }
}
