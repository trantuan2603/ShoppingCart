package com.android.shoppingcart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shoppingcart.R;
import com.android.shoppingcart.model.TenSP;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by TRANTUAN on 07-Jan-18.
 */

public class SmartPhoneAdapter extends BaseAdapter {

    Context context;
    List<TenSP> tenSPList;

    public SmartPhoneAdapter(Context context, List<TenSP> tenSPList) {
        this.context = context;
        this.tenSPList = tenSPList;
    }

    @Override
    public int getCount() {
        return tenSPList.size();
    }

    @Override
    public Object getItem(int i) {
        return tenSPList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ItemHolder {
        ImageView imagesp;
        TextView tensp, giasp, motasp;

        public ItemHolder(View itemView) {
            imagesp = itemView.findViewById(R.id.image_sp);
            tensp = itemView.findViewById(R.id.tv_tensp);
            giasp = itemView.findViewById(R.id.tv_giasp);
            motasp = itemView.findViewById(R.id.tv_motosanpham);
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemHolder itemHolder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.row_phone,viewGroup,false);
            itemHolder = new ItemHolder(view);
            view.setTag(itemHolder);
        }else {
            itemHolder = (ItemHolder) view.getTag();
        }

        TenSP tenSP = tenSPList.get(i);
        itemHolder.tensp.setText(tenSP.getTensanpham());
        itemHolder.motasp.setText(tenSP.getMotasanpham());
        itemHolder.motasp.setMaxLines(2);
        itemHolder.motasp.setEllipsize(TextUtils.TruncateAt.END);


        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
        itemHolder.giasp.setText("Gia: " + decimalFormat.format(tenSP.getGiasanpham()) + "VND");

        Picasso.with(context).load(tenSP.getHinhsanpham())
                .placeholder(R.mipmap.ic_launcher_round)
                .resize(100,100)
                .error(R.drawable.error)
                .into(itemHolder.imagesp);

        return view;
    }
}
