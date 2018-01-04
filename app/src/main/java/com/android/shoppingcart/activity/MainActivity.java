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
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.shoppingcart.R;
import com.android.shoppingcart.adapter.LoaiSPAdapter;
import com.android.shoppingcart.model.LoaiSP;
import com.android.shoppingcart.ultil.CheckConnection;
import com.android.shoppingcart.ultil.Server;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    List<LoaiSP> loaiSPList = new ArrayList<>();
    LoaiSPAdapter loaiSPAdapter;

    int id = 0;
    String tenloaisanpham = "";
    String hinhloaisanpham = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            getDuLieu();
        } else {
            CheckConnection.showToasLong(getApplicationContext(), "Kiem tra ket noi internet");
        }

    }

    private void getDuLieu() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.buongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        id = jsonObject.getInt("id");
                        tenloaisanpham = jsonObject.getString("tenloaisp");
                        hinhloaisanpham = jsonObject.getString("hinhloaisp");
                        loaiSPList.add(new LoaiSP(id,tenloaisanpham,hinhloaisanpham));
                        loaiSPAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToasLong(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangQuangcao = new ArrayList<>();
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/114111/iphone-x-256gb-400-460copy-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/106211/samsung-galaxy-note8-1-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/92069/sony-xperia-xz-premium-1-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/74110/iphone-7-8-400x460.png");
        mangQuangcao.add("https://cdn.tgdd.vn/Products/Images/42/91059/nokia-8-1-400x460.png");

        for (int i = 0; i < mangQuangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangQuangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(6000);
        viewFlipper.setAutoStart(true);

        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
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

        loaiSPAdapter = new LoaiSPAdapter(loaiSPList, MainActivity.this);
        listView.setAdapter(loaiSPAdapter);
    }
}
