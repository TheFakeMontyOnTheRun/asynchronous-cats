package br.odb.simpleasynceventhandler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import br.odb.asynceventhandlercore.EventHandler;

public class DispatchEventsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mCatImageView1;
    ImageView mCatImageView2;
    private EventHandler eventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_events);

        mCatImageView1 = (ImageView) findViewById(R.id.catImageView1);
        mCatImageView2 = (ImageView) findViewById(R.id.catImageView2);

        this.findViewById( R.id.btnLoadCatImage1 ).setOnClickListener(this);
        this.findViewById( R.id.btnLoadCatImage2 ).setOnClickListener(this);

        eventHandler = new EventHandler();

    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.btnLoadCatImage1:
                break;
            case R.id.btnLoadCatImage2:
                break;
        }
    }
}
