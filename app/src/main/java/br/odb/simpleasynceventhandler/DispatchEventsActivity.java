package br.odb.simpleasynceventhandler;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import br.odb.asynceventhandlercore.AsyncEventFactory;
import br.odb.asynceventhandlercore.EventHandler;
import br.odb.asynceventhandlercore.EventResponse;
import br.odb.asynceventhandlercore.EventResultCallback;
import br.odb.asynceventhandlercore.FetchCatImageResponse;
import br.odb.asynceventhandlercore.FetchCatUrlResponse;

public class DispatchEventsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mCatImageView1;
    private ImageView mCatImageView2;
    private EventHandler mEventHandler;
    AsyncEventFactory mEventFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_events);

        mCatImageView1 = (ImageView) findViewById(R.id.catImageView1);
        mCatImageView2 = (ImageView) findViewById(R.id.catImageView2);

        this.findViewById( R.id.btnLoadCatImage1 ).setOnClickListener(this);
        this.findViewById( R.id.btnLoadCatImage2 ).setOnClickListener(this);

        mEventHandler = new EventHandler();
        mEventFactory = mEventHandler.getFactory();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mEventHandler.startHandling();

    }

    @Override
    protected void onPause() {
        mEventHandler.stopHandling();

        super.onPause();
    }
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.btnLoadCatImage1:
                loadRandomCatUrlIntoView(mCatImageView1 );
                break;
            case R.id.btnLoadCatImage2:
                loadRandomCatUrlIntoView(mCatImageView2);
                break;
        }
    }

    private void loadImageIntoImageView( final ImageView view, final String url) {
        mEventHandler.pushEvent(mEventFactory.requestCatImage(url, new EventResultCallback() {

            @Override
            public void onFailure() {
                Toast.makeText(DispatchEventsActivity.this, R.string.failed_to_get_cat, Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onSuccess(final EventResponse response) {
                DispatchEventsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bmp = ((FetchCatImageResponse) response).getCatImage();
                        view.setImageBitmap(bmp);
                    }
                });
            }
        }));
    }

    private void loadRandomCatUrlIntoView( final ImageView view ) {
        mEventHandler.pushEvent(mEventFactory.requestCatUrl(new EventResultCallback() {
            @Override
            public void onFailure() {
                Toast.makeText(DispatchEventsActivity.this, R.string.failed_to_find_a_cat, Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onSuccess(EventResponse response) {
                String url = ((FetchCatUrlResponse) response).getUrlString();
                loadImageIntoImageView(view, url);
            }
        }));
    }
}
