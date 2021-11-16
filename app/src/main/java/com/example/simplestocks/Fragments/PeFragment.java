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


public class PeFragment extends DialogFragment {

    private static final String TAG = "priceearnings";
    private ImageView closeFragment;
    private TextView peTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_pe, container);

        //get pe and stock name from activity
        Bundle args = getArguments();
        String stock = args.getString("stock");
        String pe = args.getString("pe");

        peTv = view.findViewById(R.id.peText);
        peTv.setMovementMethod(new ScrollingMovementMethod());

        //analyze market cap amount

        Log.d(TAG, pe);

        String firstCharacter = pe.substring(0,1);
        Log.d(TAG, firstCharacter);

        //for no pe
        if(firstCharacter.equals("N")){
            peTv.append("\n\n\t With no P/E ratio, "+stock+" currently has no earnings or is currently losing money. ");

        }
        //if there is a pe
        else{
            double peNum = Double.parseDouble(pe);
            //for low pe
            if(peNum < 13){
                peTv.append("\n\nWith a P/E ratio of "+pe+" , "+stock+" has a relatively low P/E ratio compared to the S/P 500" );
            }

            //for mid pe
            if(peNum >=13 && peNum <= 15){
                peTv.append("\n\nWith a P/E ratio of "+pe+" , "+stock+" has an average P/E ratio compared to the S/P 500." );
            }

            //for high pe
            if(peNum >15 && peNum < 60){
                peTv.append("\n\nWith a P/E ratio of "+pe+" , "+stock+" has a relatively high P/E ratio compared to the S/P 500." );
            }

            //for very high pe
            if(peNum >= 60){
                peTv.append("\n\nWith a P/E ratio of "+pe+" , "+stock+" has an extremely high P/E ratio compared to the S/P 500." );
            }
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
