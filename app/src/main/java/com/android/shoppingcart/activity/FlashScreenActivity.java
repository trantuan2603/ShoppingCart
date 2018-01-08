package com.android.shoppingcart.activity;

import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.android.shoppingcart.R;
import com.android.shoppingcart.model.CustomViewFlipper;
import com.android.shoppingcart.model.ViewFlipperIndicator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FlashScreenActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private ViewFlipperIndicator flipper;
    private Animation lInAnim;
    private Animation lOutAnim;

    private String KEY_FLIPPER_POSITION = "flipper_position";

    private GestureDetector detector = null;

    private Handler myHandler = new Handler();


    // flipper restarts flipping
    private Runnable flipController = new Runnable() {
        @Override
        public void run() {
            flipper.setInAnimation(lInAnim);
            flipper.setOutAnimation(lOutAnim);
            flipper.showNext();
            flipper.startFlipping();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        flipper = findViewById(R.id.cusviewflipper);


        ActionViewFlipper();
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangQuangcao = new ArrayList<>();
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/114111/iphone-x-256gb-400-460copy-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/106211/samsung-galaxy-note8-1-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/92069/sony-xperia-xz-premium-1-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/74110/iphone-7-8-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/91059/nokia-8-1-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/114111/iphone-x-256gb-400-460copy-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/106211/samsung-galaxy-note8-1-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/92069/sony-xperia-xz-premium-1-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/74110/iphone-7-8-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/91059/nokia-8-1-400x460.png");



        for (int i = 0; i < mangQuangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangQuangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            flipper.addView(imageView);
        }

        flipper.setRadius(10);
        flipper.setMargin(10);

        // set colors for the indicators
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(android.R.color.white));
        flipper.setPaintCurrent(paint);

        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        flipper.setPaintNormal(paint);

        lInAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        lOutAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        flipper.setInAnimation(lInAnim);
        flipper.setOutAnimation(lOutAnim);

        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);


        flipper.startFlipping();

        detector = new GestureDetector(FlashScreenActivity.this, (GestureDetector.OnGestureListener) FlashScreenActivity.this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_FLIPPER_POSITION, flipper.getDisplayedChild());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // you can change this value
        float sensitivity = 50;

        if ((e1.getX() - e2.getX()) > sensitivity) {

            flipper.showNext();

            // first stops flipping
            flipper.stopFlipping();

            // then flipper restarts flipping in 3 seconds
            myHandler.postDelayed(flipController, 3000);

            return true;
        } else if ((e2.getX() - e1.getX()) > sensitivity) {
            Animation rInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
            Animation rOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
            flipper.setInAnimation(rInAnim);
            flipper.setOutAnimation(rOutAnim);
            flipper.showPrevious();

            // first stops flipping
            flipper.stopFlipping();

            // then flipper restarts flipping in 3 seconds
            myHandler.postDelayed(flipController, 3000);

            return true;
        }

        return true;
    }
}