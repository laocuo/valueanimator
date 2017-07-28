package com.tt.test;

import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

import com.tt.test.view.Ball;
import com.tt.test.view.BottleView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "laocuo";
    private Ball mBall;
    private Toolbar mToolbar;
    private ImageView mImageView;
    private BottleView mBottleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        mBall = (Ball) findViewById(R.id.ball);
        mImageView = (ImageView) findViewById(R.id.image);
        mBottleView = (BottleView) findViewById(R.id.bottle);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Title");
//        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitle("Subtitle");
//        mToolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
//        mToolbar.setNavigationIcon(R.drawable.app_icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mBall.animateStart();
//        Drawable d = mImageView.getDrawable();
//        if (d instanceof Animatable) {
//            ((Animatable) d).start();
//        }
        mBottleView.performAnim();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Drawable d = mImageView.getDrawable();
//        if (d instanceof Animatable) {
//            ((Animatable) d).stop();
//        }
    }
}
