package com.android.shoppingcart.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.shoppingcart.R;
import com.android.shoppingcart.adapter.SanPhamAdapter;
import com.android.shoppingcart.model.TenSP;
import com.android.shoppingcart.ultil.CheckConnection;
import com.android.shoppingcart.ultil.Server;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecycelActivity extends AppCompatActivity {
    private List<TenSP> tenSPList;
    private SanPhamAdapter sanPhamAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycel);

        recyclerView = findViewById(R.id.recycleView);
        tenSPList = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), tenSPList);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(sanPhamAdapter);
        getDataForRecycleView();
    }


    private void getDataForRecycleView() {
        RequestQueue requestQueueTEN = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequestTen = new JsonArrayRequest(Server.buongdantensp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        TenSP tenSP = new TenSP();
                        tenSP.setId(jsonObject.getInt("id"));
                        tenSP.setTensanpham(jsonObject.getString("tensanpham"));
                        tenSP.setGiasanpham(jsonObject.getDouble("giasanpham"));
                        tenSP.setHinhsanpham(jsonObject.getString("hinhsanpham"));
//                        tenSP.setMotasanpham(jsonObject.getString("motasanpham"));
//                        tenSP.setIdloaisanpham(jsonObject.getInt("idloaisanpham"));


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
}
