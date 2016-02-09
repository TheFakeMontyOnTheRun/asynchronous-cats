package br.odb.asynceventhandlercore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by monty on 09/02/16.
 */
public class requestCatImage extends AbstractAsyncEvent {
    private final String mCatImageUrl;

    public requestCatImage( String catLocation, EventResultCallback callback) {
        super( callback );

        mCatImageUrl = catLocation;
    }

    @Override
    public void perform() {

        URL url = null;
        try {
            url = new URL(mCatImageUrl);
            URLConnection connection = url.openConnection();
            Bitmap bmp = BitmapFactory.decodeStream( connection.getInputStream());
        } catch (MalformedURLException e) {
            reportFailure();
        } catch (IOException e) {
            reportFailure();
        }
    }
}
