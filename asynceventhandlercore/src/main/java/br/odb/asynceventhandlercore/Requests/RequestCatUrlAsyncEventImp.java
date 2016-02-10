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

            if ( connection != null ) {
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
            } else {
                reportFailure();
                return;
            }

            int response = connection.getResponseCode();

            if ( response == 200 ) {
                DataInputStream dis;
                InputStream is = connection.getInputStream();
                dis = new DataInputStream( is );

                //had to use readLine since it seems that the service doesn't get along very well with readUTF.
                String catUrl = dis.readLine();
                dis.close();

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
