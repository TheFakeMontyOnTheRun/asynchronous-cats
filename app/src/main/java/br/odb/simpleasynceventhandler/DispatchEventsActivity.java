package br.odb.simpleasynceventhandler;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import br.odb.asynceventhandlercore.AsyncEventFactory;
import br.odb.asynceventhandlercore.EventHandler;
import br.odb.asynceventhandlercore.EventResponse;
import br.odb.asynceventhandlercore.EventResultCallback;
import br.odb.asynceventhandlercore.FetchCatImageResponse;
import br.odb.asynceventhandlercore.FetchCatUrlResponse;

public class DispatchEventsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final long RANDOCAT_INTERVAL = 2000L;

    private ImageView mCatImageView1;
    private ImageView mCatImageView2;
    private ImageView mCatImageView3;
    private AsyncEventFactory mEventFactory;
    private EventHandler mEventHandler;
    private final Timer mTimer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_events);

        mCatImageView1 = (ImageView) findViewById(R.id.catImageView1);
        mCatImageView2 = (ImageView) findViewById(R.id.catImageView2);
        mCatImageView3 = (ImageView) findViewById(R.id.catImageView3);

        this.findViewById( R.id.btnLoadCatImage1 ).setOnClickListener(this);
        this.findViewById( R.id.btnLoadCatImage2 ).setOnClickListener(this);

        mEventHandler = new EventHandler( 100L );
        mEventFactory = mEventHandler.getFactory();
        startRandoCatTimer();
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

    private void startRandoCatTimer() {
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mEventHandler.isRunning()) {
                    loadRandomCatUrlIntoView(mCatImageView3);
                }
            }
        }, RANDOCAT_INTERVAL, RANDOCAT_INTERVAL);
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
                reportErrorToFetchUrl();
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

    private void reportErrorToFetchUrl() {
        DispatchEventsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DispatchEventsActivity.this, R.string.failed_to_get_cat, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRandomCatUrlIntoView( final ImageView view ) {
        mEventHandler.pushEvent(mEventFactory.requestCatUrl(new EventResultCallback() {
            @Override
            public void onFailure() {
                reportErrorToDownloadImage();
            }

            @Override
            public void onSuccess(EventResponse response) {
                String url = ((FetchCatUrlResponse) response).getUrlString();
                loadImageIntoImageView(view, url);
            }
        }));
    }

    private void reportErrorToDownloadImage() {
        DispatchEventsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DispatchEventsActivity.this, R.string.failed_to_find_a_cat, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
