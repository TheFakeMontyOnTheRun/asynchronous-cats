package br.odb.asynceventhandlercore;


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

    }
}
