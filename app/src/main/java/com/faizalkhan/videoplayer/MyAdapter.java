package com.faizalkhan.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import static java.lang.System.out;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    ArrayList<File> videoList;

    public MyAdapter(Context context, ArrayList<File> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.mVidText.setText(MainActivity.fileArrayList.get(position).getName());

        Bitmap bitmapthumb = ThumbnailUtils.createVideoThumbnail(videoList.get(position).getPath(), MediaStore.Images.Thumbnails.MINI_KIND);


        holder.mVidthumb.setImageBitmap(bitmapthumb);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayer.class);
                intent.putExtra("position", holder.getAdapterPosition());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mVidthumb;
        TextView mVidText;
        CardView cardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mVidthumb = itemView.findViewById(R.id.vid_thumb);
            mVidText = itemView.findViewById(R.id.vid_name);
            cardView = itemView.findViewById(R.id.card);
        }
    }

}
