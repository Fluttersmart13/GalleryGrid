package com.example.gallerygrid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {


    private List<GalleryModel> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;
    private String key;

    MyListAdapter(Context context, List<GalleryModel>  arrayList) {
        this.mInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GalleryModel model = arrayList.get(position);
        holder.id.setText(model.getAlt_description());
        //Glide.with(context).load(model.getThumb()).apply(new RequestOptions().override(model.getWidth(), model.getHeight())).into(holder.image);
        Glide.with(context).load(model.getThumb()).into(holder.image);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,OpenNewImageActivity.class);
                intent.putExtra("regilar",model.getRegular());
                context.startActivity(intent);
            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,id;
        CardView cardview;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            cardview = itemView.findViewById(R.id.cardview);
            image = itemView.findViewById(R.id.image);
            id = itemView.findViewById(R.id.id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}