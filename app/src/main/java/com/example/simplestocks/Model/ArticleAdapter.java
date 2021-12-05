package com.example.simplestocks.Model;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplestocks.Fragments.ArticleFragment;
import com.example.simplestocks.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    public interface OnItemClickListener {
        void onItemClicked(View v);
    }

    private OnItemClickListener listener;

    private List<Article> articleList;

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        public TextView articleNameTV;
        public TextView articleSourceTV;
        public ImageView articleImage;

        //buttons
        public Button articleButton;

        public ArticleViewHolder(View itemView){
            super(itemView);

            //image
            articleImage = itemView.findViewById(R.id.articleImageView);
            //textviews
            articleNameTV = itemView.findViewById(R.id.videoTitleTV);
            articleSourceTV = itemView.findViewById(R.id.articleSourceTV);

            //buttons
            articleButton = itemView.findViewById(R.id.articleButton);

        }

    }

    public ArticleAdapter(List<Article> marticleList) {
        articleList = marticleList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        ArticleViewHolder avh = new ArticleViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article currentItem = articleList.get(position);

        holder.articleNameTV.setText(currentItem.getArticleName());
        holder.articleSourceTV.setText(currentItem.getArticleSource());

        holder.articleImage.setImageBitmap(currentItem.getArticleImage());

        holder.articleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleFragment dialog = new ArticleFragment();
                AppCompatActivity activity = ((AppCompatActivity)v.getContext());
                Bundle args = new Bundle();
                args.putString("articleName",currentItem.getArticleName());
                args.putString("articleSource",currentItem.getArticleSource());
                args.putString("articleText", currentItem.getArticleText());

                //convert image bitmap into bytearray
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                currentItem.getArticleImage().compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] byteArray = stream.toByteArray();
                args.putByteArray("articleImage",byteArray);

                dialog.setArguments(args);
                dialog.show(activity.getSupportFragmentManager(),null);
            }
        });

    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }

}
