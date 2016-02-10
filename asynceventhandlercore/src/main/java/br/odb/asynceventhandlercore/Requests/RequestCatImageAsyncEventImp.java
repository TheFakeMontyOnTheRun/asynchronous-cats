package br.odb.asynceventhandlercore.Requests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import br.odb.asynceventhandlercore.EventResultCallback;
import br.odb.asynceventhandlercore.Responses.FetchCatImageResponse;

/**
 * Created by monty on 09/02/16.
 */
public class RequestCatImageAsyncEventImp extends AbstractAsyncEvent {
    private final String mCatImageUrl;

    public RequestCatImageAsyncEventImp(String catLocation, EventResultCallback callback) {
        super( callback );

        mCatImageUrl = catLocation;
    }

    @Override
    public void perform() {

        URL url = null;
        try {
            url = new URL(mCatImageUrl);
            URLConnection connection = url.openConnection();
            Bitmap bmp = BitmapFactory.decodeStream(connection.getInputStream());
            reportSuccess( new FetchCatImageResponse( bmp ) );
        } catch (MalformedURLException e) {
            reportFailure();
        } catch (IOException e) {
            reportFailure();
        }
    }
}
