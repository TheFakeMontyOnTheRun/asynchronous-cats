package br.odb.asynceventhandlercore.Responses;

import android.graphics.Bitmap;

import br.odb.asynceventhandlercore.EventResponse;

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
