package com.example.simplestocks.Model;

import android.os.Bundle;
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

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    public interface OnItemClickListener {
        void onItemClicked(View v);
    }

    private OnItemClickListener listener;

    private List<Stock> stockList;

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        public TextView stockTickerTV;
        public TextView stockPriceTV;
        public TextView stockNameTV;
        public TextView marketCapTV;
        public TextView peTV;
        public TextView pbTV;
        public TextView pegTV;
        public TextView dividendTV;
        public TextView ocfTV;

        //buttons
        public Button mcButton;
        public Button peButton;
        public Button pbButton;
        public Button pegButton;
        public Button divButton;
        public Button ocfButton;

        public StockViewHolder(View itemView){
            super(itemView);

            //textviews
            stockTickerTV = itemView.findViewById(R.id.stockTickerTV);
            stockNameTV = itemView.findViewById(R.id.stockNameTV);
            stockPriceTV = itemView.findViewById(R.id.stockPriceTV);
            marketCapTV = itemView.findViewById(R.id.marketCapTV);
            peTV = itemView.findViewById(R.id.peTV);
            pegTV = itemView.findViewById(R.id.pegTV);
            pbTV = itemView.findViewById(R.id.pbTV);
            dividendTV = itemView.findViewById(R.id.divTV);
            ocfTV = itemView.findViewById(R.id.ocfTV);

            //buttons
            mcButton = itemView.findViewById(R.id.mcButton);
            peButton = itemView.findViewById(R.id.peButton);
            pbButton = itemView.findViewById(R.id.pbButton);
            pegButton = itemView.findViewById(R.id.pegButton);
            divButton = itemView.findViewById(R.id.divButton);
            ocfButton = itemView.findViewById(R.id.ocfButton);


        }

    }

    public StockAdapter (List<Stock> mStockList) {
        stockList = mStockList;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_item, parent, false);
        StockViewHolder svh = new StockViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        Stock currentItem = stockList.get(position);

        holder.stockTickerTV.setText(currentItem.getTicker());
        holder.stockNameTV.setText(currentItem.getName());
        holder.stockPriceTV.setText(currentItem.getPrice());
        holder.marketCapTV.setText(currentItem.getMarketCap());
        holder.peTV.setText(currentItem.getPe());
        holder.pbTV.setText(currentItem.getPb());
        holder.pegTV.setText(currentItem.getPeg());
        holder.dividendTV.setText(currentItem.getDivYield());
        holder.ocfTV.setText(currentItem.getOcf());

        holder.mcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketCapFragment dialog = new MarketCapFragment();
                AppCompatActivity activity = ((AppCompatActivity)v.getContext());
                Bundle args = new Bundle();
                args.putString("stock",currentItem.getName());
                args.putString("mc",currentItem.getMarketCap());
                dialog.setArguments(args);
                dialog.show(activity.getSupportFragmentManager(),null);
            }
        });

        holder.peButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PeFragment dialog = new PeFragment();
                AppCompatActivity activity = ((AppCompatActivity)v.getContext());
                Bundle args = new Bundle();
                args.putString("stock",currentItem.getName());
                args.putString("pe",currentItem.getPe());
                dialog.setArguments(args);
                dialog.show(activity.getSupportFragmentManager(),null);
            }
        });

        holder.pbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PbFragment dialog = new PbFragment();
                AppCompatActivity activity = ((AppCompatActivity)v.getContext());
                Bundle args = new Bundle();
                args.putString("stock",currentItem.getName());
                args.putString("pb",currentItem.getPb());
                dialog.setArguments(args);
                dialog.show(activity.getSupportFragmentManager(),null);
            }
        });

        holder.pegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PegFragment dialog = new PegFragment();
                AppCompatActivity activity = ((AppCompatActivity)v.getContext());
                Bundle args = new Bundle();
                args.putString("stock",currentItem.getName());
                args.putString("peg",currentItem.getPeg());
                dialog.setArguments(args);
                dialog.show(activity.getSupportFragmentManager(),null);
            }
        });

        holder.divButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YieldFragment dialog = new YieldFragment();
                AppCompatActivity activity = ((AppCompatActivity)v.getContext());
                Bundle args = new Bundle();
                args.putString("stock",currentItem.getName());
                args.putString("div",currentItem.getDivYield());
                dialog.setArguments(args);
                dialog.show(activity.getSupportFragmentManager(),null);
            }
        });

        holder.ocfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               OcfFragment dialog = new OcfFragment();
                AppCompatActivity activity = ((AppCompatActivity)v.getContext());
                Bundle args = new Bundle();
                args.putString("stock",currentItem.getName());
                args.putString("ocf",currentItem.getOcf());
                dialog.setArguments(args);
                dialog.show(activity.getSupportFragmentManager(),null);
            }
        });

    }



    @Override
    public int getItemCount() {
        return stockList.size();
    }

}
