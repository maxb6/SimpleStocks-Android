package com.example.simplestocks.Model;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplestocks.Fragments.MarketCapFragment;
import com.example.simplestocks.Fragments.OcfFragment;
import com.example.simplestocks.Fragments.PbFragment;
import com.example.simplestocks.Fragments.PeFragment;
import com.example.simplestocks.Fragments.PegFragment;
import com.example.simplestocks.Fragments.YieldFragment;
import com.example.simplestocks.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    public interface OnItemClickListener {
        void onItemClicked(View v);
    }

    private OnItemClickListener listener;

    private List<Video> videoList;

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        public TextView videoTitleTV;

        public VideoViewHolder(View itemView){
            super(itemView);

            //textviews
            videoTitleTV = itemView.findViewById(R.id.videoTitleTV);
        }

    }

    public VideoAdapter (List<Video> mVideoList) {
        videoList = mVideoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        VideoViewHolder vvh = new VideoViewHolder(v);
        return vvh;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video currentItem = videoList.get(position);

        holder.videoTitleTV.setText(currentItem.getTitle());


    }



    @Override
    public int getItemCount() {
        return videoList.size();
    }

}
