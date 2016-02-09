package br.odb.asynceventhandlercore;

/**
 * Created by monty on 09/02/16.
 */
public class FetchCatUrlResponse implements EventResponse {
    private final String mUrlString;

    FetchCatUrlResponse( String url ) {
        this.mUrlString = url;
    }

    public String getUrlString() {
        return mUrlString;
    }
}
