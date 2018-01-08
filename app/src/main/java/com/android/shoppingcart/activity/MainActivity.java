package com.android.shoppingcart.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.shoppingcart.R;
import com.android.shoppingcart.adapter.LoaiSPAdapter;
import com.android.shoppingcart.adapter.SanPhamAdapter;
import com.android.shoppingcart.model.LoaiSP;
import com.android.shoppingcart.model.TenSP;
import com.android.shoppingcart.ultil.CheckConnection;
import com.android.shoppingcart.ultil.RecyclerItemClickListener;
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

    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private ListView listView;
    private DrawerLayout drawerLayout;

    private List<LoaiSP> loaiSPList;
    private LoaiSPAdapter loaiSPAdapter;

    private List<TenSP> tenSPList;
    private SanPhamAdapter sanPhamAdapter;
    RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            getDuLieu();
            getDataForRecycleView();
            ActionClickItemRecycle();
            ActionClickMenu();
        } else {
            CheckConnection.showToasLong(getApplicationContext(), "Kiem tra ket noi internet");
        }

    }



    private void ActionClickMenu() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;

                    case 1:
                        Intent phoneIntent = new Intent(MainActivity.this, SmartPhoneActivity.class);
                        phoneIntent.putExtra("SMARTPHONE", loaiSPList.get(i).getId());
                        startActivity(phoneIntent);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;
                    case 2:
                        Intent latopIntent = new Intent(MainActivity.this, LaptopActivity.class);
                        latopIntent.putExtra("LAPTOP", loaiSPList.get(i).getId());
                        startActivity(latopIntent);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;
                    case 3:
                        Intent ContactIntent = new Intent(MainActivity.this, ContactsActivity.class);
                        startActivity(ContactIntent);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;
                    case 4:
                        Intent infoIntent = new Intent(MainActivity.this, InfoActivity.class);
                        startActivity(infoIntent);
                        drawerLayout.closeDrawer(Gravity.START);
                        break;

                }
            }
        });
    }

    private void ActionClickItemRecycle() {

        switch (0) {
            case 0:
                //use this in case of gridlayoutmanager, 2 indicates no. of columns
                mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                recyclerView.setLayoutManager(mLayoutManager);
                break;
            case 1:
                //use this in case of Staggered GridLayoutManager, 2 indicates no. of columns
                mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLayoutManager);
                break;
            case 2:
                //horizontal linear layout
                mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(mLayoutManager);
                break;
        }

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent detailInent = new Intent(getApplicationContext(), DetailActivity.class);
                detailInent.putExtra("THONGTINSANPHAM", tenSPList.get(position));
                startActivity(detailInent);
            }
        }));
    }

    private void getDataForRecycleView() {
        RequestQueue requestQueueTEN = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequestTen = new JsonArrayRequest(Server.buongdantensp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        TenSP tenSP = new TenSP();
                        tenSP.setId(jsonObject.getInt("id"));
                        tenSP.setTensanpham(jsonObject.getString("tensanpham"));
                        tenSP.setGiasanpham(jsonObject.getDouble("giasanpham"));
                        tenSP.setHinhsanpham(jsonObject.getString("hinhsanpham"));
                        tenSP.setMotasanpham(jsonObject.getString("motasanpham"));
                        tenSP.setIdloaisanpham(jsonObject.getInt("idloaisanpham"));
                        tenSPList.add(tenSP);
                        sanPhamAdapter.notifyDataSetChanged();

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
        requestQueueTEN.add(jsonArrayRequestTen);

    }

    private void getDuLieu() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.buongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        LoaiSP loaiSP = new LoaiSP();
                        loaiSP.setId(jsonObject.getInt("id"));
                        loaiSP.setTenloaisanpham(jsonObject.getString("tenloaisp"));
                        loaiSP.setHinhloaisanpham(jsonObject.getString("hinhloaisp"));
                        loaiSPList.add(loaiSP);
                        loaiSPAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if ( i == response.length()-1){
                        LoaiSP home = new LoaiSP();
                        Uri uri = Uri.parse("android.resource://com.android.shoppingcart/" + R.drawable.ic_action_home);
                        home.setHinhloaisanpham(String.valueOf(uri));
                        home.setTenloaisanpham("Trang chủ");
                        loaiSPList.add(0, home);

                        LoaiSP lienhe = new LoaiSP();
                        Uri uriLienhe = Uri.parse("android.resource://com.android.shoppingcart/" + R.drawable.ic_action_contact);
                        lienhe.setHinhloaisanpham(String.valueOf(uriLienhe));
                        lienhe.setTenloaisanpham("Liên hệ");
                        loaiSPList.add(loaiSPList.size(), lienhe);

                        LoaiSP info = new LoaiSP();
                        Uri uriinfo = Uri.parse("android.resource://com.android.shoppingcart/" + R.drawable.ic_action_info);
                        info.setHinhloaisanpham(String.valueOf(uriinfo));
                        info.setTenloaisanpham("Thông Tin");
                        loaiSPList.add(loaiSPList.size(), info);
                        loaiSPAdapter.notifyDataSetChanged();
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
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.toolbar_activity, null);
        actionBar.setCustomView(customView);
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setPadding(0, 0, 0, 0);//for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0, 0);
        ImageButton homeButton = customView.findViewById(R.id.btn_navigation);
        EditText search = customView.findViewById(R.id.edt_search);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
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


        loaiSPList = new ArrayList<>();
        loaiSPAdapter = new LoaiSPAdapter(loaiSPList, MainActivity.this);
        listView.setAdapter(loaiSPAdapter);

        tenSPList = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), tenSPList);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }
        recyclerView.setAdapter(sanPhamAdapter);
    }
}
