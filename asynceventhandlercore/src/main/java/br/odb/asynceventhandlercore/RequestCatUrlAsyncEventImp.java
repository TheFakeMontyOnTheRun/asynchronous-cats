package br.odb.asynceventhandlercore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by monty on 09/02/16.
 */
class RequestCatUrlAsyncEventImp extends AbstractAsyncEvent {

    private static final String CAT_IMAGE_URL_FIELD = "file";

    public RequestCatUrlAsyncEventImp(EventResultCallback callback) {
        super( callback );
    }

    @Override
    public void perform() {
        URL randomCatsApi = null;

        try {
            randomCatsApi = new URL( "http://random.cat/meow" );
        } catch (MalformedURLException e) {
            reportFailure();
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) randomCatsApi.openConnection();

            if ( connection != null ) {
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
            } else {
                reportFailure();
                return;
            }

            int response = connection.getResponseCode();

            if ( response == HttpURLConnection.HTTP_OK ) {
                InputStream is = connection.getInputStream();
                Scanner scanner = new Scanner( is );
                String catUrl = scanner.nextLine();
                is.close();
                JSONObject reader = new JSONObject(catUrl);
                String url = reader.get(CAT_IMAGE_URL_FIELD).toString();
                reportSuccess( new FetchCatUrlResponse( url ) );
            } else {
                reportFailure();
            }

        } catch (IOException | JSONException e) {
            reportFailure();
        }
    }
}
