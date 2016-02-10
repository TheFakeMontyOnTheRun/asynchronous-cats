package br.odb.asynceventhandlercore.Requests;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.odb.asynceventhandlercore.EventResultCallback;
import br.odb.asynceventhandlercore.Responses.FetchCatUrlResponse;

/**
 * Created by monty on 09/02/16.
 */
public class RequestCatUrlAsyncEventImp extends AbstractAsyncEvent {
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

            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            int response = connection.getResponseCode();

            if ( response == 200 ) {
                InputStream is = connection.getInputStream();
                DataInputStream dis = new DataInputStream( is );
                String catUrl = dis.readLine();
                JSONObject reader = new JSONObject(catUrl);
                String url = reader.get("file").toString();
                reportSuccess( new FetchCatUrlResponse( url ) );
            } else {
                reportFailure();
            }

        } catch (IOException e) {
            reportFailure();
        } catch (JSONException e) {
            reportFailure();
        }
    }
}
