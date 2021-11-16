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


public class PegFragment extends DialogFragment {

    private ImageView closeFragment;
    private TextView pegTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_peg, container);

        //get peg and stock name from activity
        Bundle args = getArguments();
        String stock = args.getString("stock");
        String peg = args.getString("peg");

        pegTv = view.findViewById(R.id.pegText);
        pegTv.setMovementMethod(new ScrollingMovementMethod());

        String firstCharacter = peg.substring(0,1);

        //for no peg
        if(firstCharacter.equals("N")){
            pegTv.append("\n\n\t With no PEG ratio, "+stock+" currently has no earnings per share growth. ");

        }
        //if there is a pe
        else{
            double pegNum = Double.parseDouble(peg);
            //for low pe
            if(pegNum <= 1){
                pegTv.append("\n\nWith a PEG ratio of "+peg+" , "+stock+" has a low PEG ratio and may be undervalued" );
            }

            //for mid pe
            if(pegNum >=1 && pegNum <= 1.25){
                pegTv.append("\n\nWith a PEG ratio of "+peg+" , "+stock+" has an average PEG ratio and may be fairly valued." );
            }

            //for high pe
            if(pegNum >1.25 ){
                pegTv.append("\n\nWith a PEG ratio of "+peg+" , "+stock+" has a high PEG ratio and may be overvalued." );
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
