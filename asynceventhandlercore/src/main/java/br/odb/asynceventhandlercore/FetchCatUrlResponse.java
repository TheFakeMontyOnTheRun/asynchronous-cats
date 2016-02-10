package br.odb.asynceventhandlercore;

import br.odb.asynceventhandlercore.EventResponse;

/**
 * Created by monty on 09/02/16.
 */
public class FetchCatUrlResponse implements EventResponse {
    private final String mUrlString;

    public FetchCatUrlResponse(String url) {
        this.mUrlString = url;
    }

    public String getUrlString() {
        return mUrlString;
    }
}
