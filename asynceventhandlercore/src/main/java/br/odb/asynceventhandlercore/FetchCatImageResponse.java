package br.odb.asynceventhandlercore;

import android.graphics.Bitmap;

/**
 * Created by monty on 09/02/16.
 */
public class FetchCatImageResponse implements EventResponse {
    private final Bitmap mCatImage;

    public FetchCatImageResponse(Bitmap catImage) {
        this.mCatImage = catImage;
    }

    public Bitmap getCatImage() {
        return mCatImage;
    }
}
