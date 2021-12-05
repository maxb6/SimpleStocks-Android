package com.example.simplestocks.Fragments;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.simplestocks.R;


public class MarketCapFragment extends DialogFragment {

    private static final String TAG = "Market Cap";
    private ImageView closeFragment;
    private TextView mcTv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_market_cap, container);

        //get market cap and stock name from activity
        Bundle args = getArguments();
        String stock = args.getString("stock");
        String mc = args.getString("mc");
        mcTv = view.findViewById(R.id.articleTextFragmentTV);
        mcTv.setMovementMethod(new ScrollingMovementMethod());

        //analyze market cap amount
        String endCharacter = mc.substring(mc.length()-1,mc.length()-0);
        Log.d(TAG, endCharacter);

        String numberAmount = mc.substring(0,mc.length()-1);
        Log.d(TAG, numberAmount);

        float naFloat = Float.valueOf(numberAmount);
        int na = (int) Math.floor(naFloat);
        Log.d(TAG, "na: "+ na);
        //compare size of company
        //for small cap stocks (less then 2 B)
        if(endCharacter.equals("M") || endCharacter.equals("B") && na < 2 ){
            mcTv.append("\n\n\nWith a market cap of "+mc+" , "+stock+" is a small cap stock. ");
        }

        //for mid cap stocks (2B-10B)
        if(endCharacter.equals("B") && na >= 2 && na<10 ){
            mcTv.append("\n\n\nWith a market cap of "+mc+" , "+stock+" is a mid cap stock. ");
        }

        //for large cap stocks
        if(endCharacter.equals("B") && na>=10 ){
            mcTv.append("\n\n\nWith a market cap of "+mc+" , "+stock+" is a large cap stock. " );
        }

        //for mega-cap stocks (10B+)
        if(endCharacter.equals("T")){
            mcTv.append("\n\n\nWith a market cap of "+mc+" , "+stock+" is a MEGA-large cap stock. " );
        }

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
