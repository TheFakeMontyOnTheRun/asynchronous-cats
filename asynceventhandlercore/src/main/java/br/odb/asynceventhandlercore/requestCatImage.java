package br.odb.asynceventhandlercore;

import android.net.Uri;

/**
 * Created by monty on 09/02/16.
 */
public class requestCatImage extends AbstractAsyncEvent {
    private final Uri mCatImageUri;

    public requestCatImage( Uri catLocation, EventResultCallback callback) {
        super( callback );

        mCatImageUri = catLocation;
    }

    @Override
    public void perform() {

    }
}
