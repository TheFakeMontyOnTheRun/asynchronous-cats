package br.odb.simpleasynceventhandler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import br.odb.asynceventhandlercore.AsyncEventFactory;
import br.odb.asynceventhandlercore.AsyncEventFactoryImpl;
import br.odb.asynceventhandlercore.EventHandler;
import br.odb.asynceventhandlercore.EventResponse;
import br.odb.asynceventhandlercore.EventResultCallback;
import br.odb.asynceventhandlercore.FetchCatUrlResponse;
import br.odb.asynceventhandlercore.RequestCatUrlAsyncEventImp;

public class DispatchEventsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mCatImageView1;
    ImageView mCatImageView2;
    private EventHandler eventHandler;
    AsyncEventFactory mEventFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_events);

        mCatImageView1 = (ImageView) findViewById(R.id.catImageView1);
        mCatImageView2 = (ImageView) findViewById(R.id.catImageView2);

        this.findViewById( R.id.btnLoadCatImage1 ).setOnClickListener(this);
        this.findViewById( R.id.btnLoadCatImage2 ).setOnClickListener(this);

        eventHandler = new EventHandler();
        eventHandler.startHandling();
        mEventFactory = new AsyncEventFactoryImpl();
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.btnLoadCatImage1:
                loadRandomCatUrlIntoView(mCatImageView1 );
                break;
            case R.id.btnLoadCatImage2:
                loadRandomCatUrlIntoView(mCatImageView2 );
                break;
        }
    }

    private void loadImageIntoImageView( final ImageView view, final String url) {
    }

    private void loadRandomCatUrlIntoView( final ImageView view ) {
        eventHandler.pushEvent(mEventFactory.requestCatUrl(new EventResultCallback() {
            @Override
            public void onFailure() {
            }

            @Override
            public void onSuccess(EventResponse response) {
                String url = ((FetchCatUrlResponse) response).getUrlString();
                Log.d("Monty", url);
                loadImageIntoImageView( view, url );
            }
        }));
    }
}
