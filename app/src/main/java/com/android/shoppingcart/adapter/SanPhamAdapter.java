package com.android.shoppingcart.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shoppingcart.R;
import com.android.shoppingcart.model.TenSP;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.DecimalFormat;
import java.util.List;

import jp.wasabeef.picasso.transformations.ColorFilterTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by TRANTUAN on 05-Jan-18.
 */

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.itemHolder> {

    Context context;
    List<TenSP> tenSPList;

    public SanPhamAdapter(Context context, List<TenSP> tenSPList) {
        this.context = context;
        this.tenSPList = tenSPList;
    }

    @Override
    public itemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tensp, null);
        itemHolder hf = new itemHolder(view);
        return hf;
    }

    @Override
    public void onBindViewHolder(itemHolder holder, int position) {
        TenSP tenSP = tenSPList.get(position);

        holder.tensp.setText(tenSPList.get(position).getTensanpham());

        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");

        holder.giasp.setText("Gia: " + decimalFormat.format(tenSP.getGiasanpham()) + "VND");

        int color = Color.parseColor("#339b59b6");

        Picasso.with(context).load(tenSPList.get(position).getHinhsanpham())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.drawable.error)
//                .transform(new CropSquareTransformation())
                .transform(new RoundedTransformation( 100, 1))
               //.transform(new ColorFilterTransformation(color))
                //.transform(new CropCircleTransformation())
                .into(holder.imagesp);
    }

    @Override
    public int getItemCount() {
        return tenSPList.size();
    }

    public class itemHolder extends RecyclerView.ViewHolder {
        ImageView imagesp;
        TextView tensp, giasp;

        public itemHolder(View itemView) {
            super(itemView);
            imagesp = itemView.findViewById(R.id.image_sp);
            tensp = itemView.findViewById(R.id.tv_tensp);
            giasp = itemView.findViewById(R.id.tv_giasp);
        }
    }

    public class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "square()";
        }
    }

    public class RoundedTransformation implements com.squareup.picasso.Transformation {
        private final int radius;
        private final int margin;  // dp

        // radius is corner radii in dp
        // margin is the board in dp
        public RoundedTransformation(final int radius, final int margin) {
            this.radius = radius;
            this.margin = margin;
        }

        @Override
        public Bitmap transform(final Bitmap source) {
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin, source.getHeight() - margin), radius, radius, paint);

            if (source != output) {
                source.recycle();
            }

            return output;
        }

        @Override
        public String key() {
            return "rounded";
        }
    }
}


