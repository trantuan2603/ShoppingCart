package com.android.shoppingcart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shoppingcart.R;
import com.android.shoppingcart.model.LoaiSP;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by TRANTUAN on 04-Jan-18.
 */

public class LoaiSPAdapter extends BaseAdapter {

    List<LoaiSP> loaiSPS;
    Context context;

    public LoaiSPAdapter(List<LoaiSP> loaiSPS, Context context) {
        this.loaiSPS = loaiSPS;
        this.context = context;
    }

    @Override
    public int getCount() {
        return loaiSPS.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiSPS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView tenLoaisp;
        ImageView anhLoaiSP;

        public ViewHolder(View view) {
            this.tenLoaisp = view.findViewById(R.id.tv_ten_loaisp);
            this.anhLoaiSP = view.findViewById(R.id.image_loaisp);
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.dong_listview_loai_sp, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();

        }

        LoaiSP loaiSP = loaiSPS.get(i);
        viewHolder.tenLoaisp.setText(loaiSP.getTenloaisanpham());
        Picasso.with(context).load(loaiSP.getHinhloaisanpham())
                .error(R.mipmap.ic_launcher_round).placeholder(R.mipmap
                .ic_launcher_round)
                .resize(70,70)
                .into(viewHolder.anhLoaiSP);

        return view;
    }
}
