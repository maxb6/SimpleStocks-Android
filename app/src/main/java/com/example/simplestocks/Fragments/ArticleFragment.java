package com.example.simplestocks.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.simplestocks.R;


public class ArticleFragment extends DialogFragment {

    private static final String TAG = "Market Cap";
    private ImageView closeFragment;
    private ImageView articleImage;
    private TextView articleNameTV;
    private TextView articleSourceTV;
    private TextView articleTextTV;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article, container);

        //get article name and source from activity
        Bundle args = getArguments();
        String articleName = args.getString("articleName");
        String articleSource = args.getString("articleSource");
        String articleText = args.getString("articleText");

        articleNameTV = view.findViewById(R.id.articleNameFragmentTV);
        articleNameTV.setText(articleName);

        articleSourceTV = view.findViewById(R.id.articleSourceFragmentTV);
        articleSourceTV.setText(articleSource);

        articleTextTV = view.findViewById(R.id.articleTextFragmentTV);
        articleTextTV.setText(articleText);
        articleTextTV.setMovementMethod(new ScrollingMovementMethod());

        articleImage = view.findViewById(R.id.articleImageFragment);
        byte[] byteArray = getArguments().getByteArray("articleImage");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
        articleImage.setImageBitmap(bmp);

        closeFragment = view.findViewById(R.id.closeFragment);
        closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}
