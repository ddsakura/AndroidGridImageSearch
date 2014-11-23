
package com.cire.gridimagesearch;

import android.app.Activity;
import android.os.Bundle;

import com.squareup.picasso.Picasso;

public class FullScreenActivity extends Activity {

    TouchImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen);

        Bundle bundle = this.getIntent().getExtras();
        String url = bundle.getString("url");
        img = (TouchImageView)findViewById(R.id.img);
        Picasso.with(this).load(url).into(img);

    }

}
