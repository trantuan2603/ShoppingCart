package com.android.shoppingcart.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.shoppingcart.R;
import com.android.shoppingcart.adapter.SmartPhoneAdapter;
import com.android.shoppingcart.model.TenSP;
import com.android.shoppingcart.ultil.CheckConnection;
import com.android.shoppingcart.ultil.Server;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartPhoneActivity extends AppCompatActivity {

    private ListView lvPhone;
    private SmartPhoneAdapter smartPhoneAdapter;
    private List<TenSP> tenSPList;
    private Toolbar toolbar;
    private int mID = -1;
    private int mPage = 1;

    private ProgressBar progressBar;
    private View footer;
    private boolean loading = false;
    private boolean limitData = false;
    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        mapWidget();
        actionToolbar();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getIdProduct();
            getData(mPage);
            loadMoreData();
        } else {
            CheckConnection.showToasLong(getApplicationContext(), "Kiem tra ket noi internet");
        }
    }

    private void loadMoreData() {

        lvPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailInent = new Intent(getApplicationContext(), DetailActivity.class);
                detailInent.putExtra("THONGTINSANPHAM",tenSPList.get(i));
                startActivity(detailInent);
            }
        });

        lvPhone.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0 && loading == false && limitData == false){
                    loading = true;
                    MyThread myThread = new MyThread();
                    myThread.start();

                }
            }
        });
    }

    private void getData(int Page) {

        RequestQueue requestQueueTEN = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.getsanphamtheoloaisp + String.valueOf(Page);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response het du lieu se tra ve 3 ngoac vuong hoac la 2 ngoac nhon
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //Log.d("tab", "onResponse: "+jsonObject);
                            TenSP tenSP = new TenSP();
                            tenSP.setId(jsonObject.getInt("id"));
                            tenSP.setTensanpham(jsonObject.getString("tensanpham"));
                            tenSP.setGiasanpham(jsonObject.getDouble("giasanpham"));
                            tenSP.setHinhsanpham(jsonObject.getString("hinhsanpham"));
                            tenSP.setMotasanpham(jsonObject.getString("motasanpham"));
                            tenSP.setIdloaisanpham(jsonObject.getInt("idloaisanpham"));
                            tenSPList.add(tenSP);
                            smartPhoneAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitData = true;
                    lvPhone.removeFooterView(footer);
                    CheckConnection.showToasLong(getApplicationContext(),"da het du lieu de load");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToasLong(getApplicationContext(), error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mParams = new HashMap<String, String>();
                mParams.put("idsanpham", String.valueOf(mID));
                return mParams;
            }
        };
        requestQueueTEN.add(stringRequest);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Điện thoại");
//        //Không hiện tiêu đề
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.ic_phone_android_black_24dp);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View customView = getLayoutInflater().inflate(R.layout.toolbar_laptop, null);
        actionBar.setCustomView(customView);
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setPadding(0, 0, 0, 0);//for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0, 0);
        ImageButton homeButton = customView.findViewById(R.id.btn_home_bar);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(SmartPhoneActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }

    private void getIdProduct() {
        mID = getIntent().getIntExtra("SMARTPHONE", -1);
    }

    private void mapWidget() {
        toolbar = findViewById(R.id.toobar_smartphone);

        lvPhone = findViewById(R.id.lv_smartphone);

        tenSPList = new ArrayList<>();
        smartPhoneAdapter = new SmartPhoneAdapter(SmartPhoneActivity.this, tenSPList);
        lvPhone.setAdapter(smartPhoneAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footer = inflater.inflate(R.layout.progressbar,null);
        myHandler = new MyHandler();
    }

    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvPhone.addFooterView(footer);
                    break;
                case 1:
                    getData(++mPage);
                    loading = false;
                    break;
            }

            super.handleMessage(msg);
        }
    }

    public class MyThread extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message message = myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
            super.run();
        }
    }
}
