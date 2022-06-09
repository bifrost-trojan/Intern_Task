package com.example.intern_task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private String[] data1;
    private String[] data2;
    private ArrayList<byte[]> bitmap;

    public HomeAdapter(ArrayList<String> data1, ArrayList<String> data2,ArrayList<byte[]> bitmap){
        this.data1 = data1.toArray(new String[0]);
        this.data2 = data2.toArray(new String[0]);
        this.bitmap = bitmap;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_item_layout, parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

        String title2 = data1[position];
        holder.title.setText(title2);
        String title3 = data2[position];
        holder.description.setText(title3);
        if(bitmap != null) {
            byte[] title4 = bitmap.get(position);
            Bitmap bitmap = BitmapFactory.decodeByteArray(title4, 0, title4.length);
            holder.imgIcon.setImageBitmap(bitmap);

        }
        //


    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder{

        ImageView imgIcon;
        TextView title;
        TextView description;

        public HomeViewHolder(View itemView){
            super(itemView);
            imgIcon = itemView.findViewById(R.id.home_item_layout_icon);
            title = itemView.findViewById(R.id.home_item_layout_title);
            description = itemView.findViewById(R.id.home_item_layout_description);
        }
    }

}
