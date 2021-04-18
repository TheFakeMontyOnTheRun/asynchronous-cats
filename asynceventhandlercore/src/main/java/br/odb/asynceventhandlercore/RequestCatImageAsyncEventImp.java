package br.odb.asynceventhandlercore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by monty on 09/02/16.
 */
class RequestCatImageAsyncEventImp extends AbstractAsyncEvent {
    private final String mCatImageUrl;

    public RequestCatImageAsyncEventImp(String catLocation, EventResultCallback callback) {
        super( callback );

        mCatImageUrl = catLocation;
    }

    @Override
    public void perform() {

        URL url;
        try {
            url = new URL(mCatImageUrl);
            URLConnection connection = url.openConnection();
            Bitmap bmp = BitmapFactory.decodeStream(connection.getInputStream());
            reportSuccess( new FetchCatImageResponse( bmp ) );
        } catch (IOException e) {
            reportFailure();
        }
    }
}
