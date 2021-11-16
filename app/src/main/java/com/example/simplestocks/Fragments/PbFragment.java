package com.example.simplestocks.Fragments;

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


public class PbFragment extends DialogFragment {

    private ImageView closeFragment;
    private TextView pbTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_pb, container);

        //get pb and stock name from activity
        Bundle args = getArguments();
        String stock = args.getString("stock");
        String pb = args.getString("pb");

        pbTv = view.findViewById(R.id.pbText);
        pbTv.setMovementMethod(new ScrollingMovementMethod());

        String firstCharacter = pb.substring(0,1);

        //for no pb
        if(firstCharacter.equals("N")){
            pbTv.append("\n\n\t With no P/B ratio, "+stock+" currently has no book value or is posting negative earnings. ");

        }
        //if there is a pb
        else{
            double pbNum = Double.parseDouble(pb);
            //for low pb
            if(pbNum < 2){
                pbTv.append("\n\nWith a P/B ratio of "+pb+" , "+stock+" has a relatively low P/B ratio and may be undervalued" );
            }

            //for mid pb
            if(pbNum >=2 && pbNum <= 3){
                pbTv.append("\n\nWith a P/B ratio of "+pb+" , "+stock+" has an average P/B ratio and may be fairly valued." );
            }

            //for high pb
            if(pbNum >3 ){
                pbTv.append("\n\nWith a P/B ratio of "+pb+" , "+stock+" has a higher then average P/B ratio and may be overvalued." );
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
