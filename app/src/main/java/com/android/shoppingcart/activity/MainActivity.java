package com.android.shoppingcart.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.shoppingcart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();
        ActionBar();
        ActionViewFlipper();
    }

    private void ActionViewFlipper() {
        ArrayList<String>  mangQuangcao = new ArrayList<>();
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/114111/iphone-x-256gb-400-460copy-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/106211/samsung-galaxy-note8-1-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/92069/sony-xperia-xz-premium-1-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/74110/iphone-7-8-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/91059/nokia-8-1-400x460.png");

        for (int i = 0;  i < mangQuangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangQuangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(6000);
        viewFlipper.setAutoStart(true);

        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbar_main);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerView = findViewById(R.id.recycleView);
        navigationView = findViewById(R.id.navigationView);
        listView = findViewById(R.id.listview);
        drawerLayout = findViewById(R.id.drawerLayout);
    }
}
