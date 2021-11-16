package com.example.simplestocks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simplestocks.Database.DatabaseHelper;
import com.example.simplestocks.Model.Stock;
import com.example.simplestocks.Model.StockAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private EditText editTextStock;
    private Button selectStockButton;
    private static final String TAG = "MainActivity";
    private String stockString;
    private String stockTyped;
    private static final String STOCK_LIST = "stock_list";

    //stock objects
    private Stock stockChosen = new Stock();
    protected DatabaseHelper dbHelper;

    protected List<Stock> stockList;

    //recyclerview
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private Context context;

    SwipeRefreshLayout refreshLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextStock = findViewById(R.id.editTextStock);
        selectStockButton = findViewById(R.id.selectStockButton);
        //refreshLayout = findViewById(R.id.refreshLayout);

        //drawer layout menu system
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(this);


        navigationView.setCheckedItem(R.id.nav_home);

        //shared preferences
        SharedPreferences pref = getSharedPreferences("StockPrefs", Context.MODE_PRIVATE);
        stockString = pref.getString("stock_string2",null);

        selectStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take stock typed as input
                stockTyped = editTextStock.getText().toString().trim();
                //stockTyped will have its json file be parsed and then sent to sql db as a stock object
                callStockClient(stockTyped);

            }

        });
        dbHelper = new DatabaseHelper(this);
        context=this;
        loadStocks();

       /* refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refreshStocks();
                refreshLayout.setRefreshing(false);
            }

        });

        */
    }


    // Functions for menu system
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_home:
                break ;
            case R.id.nav_article:
                goToArticleActivity();
                break;
            case R.id.nav_profile:
                goToProfileActivity();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    //Functions for stock api

    private void loadStocks() {

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        stockList = dbHelper.getAllStocks();
        mAdapter = new StockAdapter(stockList);
        Log.i(TAG,"STOCK LIST: "+stockList.toString());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.notifyDataSetChanged();
        new ItemTouchHelper (simpleCallback).attachToRecyclerView(mRecyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAdapterPosition();
            Stock recoverStock = stockList.get(position);
            String stockID = String.valueOf(stockList.get(position).getId());
            Log.i(TAG, "stockID  " + stockID);
            dbHelper.deleteStockByID(stockID);
            mAdapter.notifyItemRemoved(position);
            loadStocks();
            Toast.makeText(getApplicationContext(),"Stock deleted successfully",Toast.LENGTH_SHORT).show();
        }
    };

    private void refreshStocks() {

        for (Stock stockChosen : stockList){
           updateStockClient(stockChosen);
        }


    }


    protected void callStockClient(String stockTyped) {

        //make an okhttp client request to use yahoo finance api.
        //the api will return all stock profile info in a json file

        String url = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-summary?symbol="+stockTyped+"&region=US";

        //***********************************************************************************
        //API KEYS SENSITIVE DATA

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //.url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-profile?symbol=tsla&region=US")
                .url(url)
                .get()
                .addHeader("x-rapidapi-key", "cc7d5a3a94mshf02a8d7f7daf522p1286adjsnc17159b46ff3")
                .addHeader("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .build();

        //***********************************************************************************


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                     String myResponse = response.body().string();
                     MainActivity.this.runOnUiThread(new Runnable() {
                         @Override
                         public void run() {

                             try {

                                 // stock object is created by parsing through json file
                                 createStockObject(myResponse);
                                 Log.i(TAG,"Stock Object Created");
                                 loadStocks();

                             } catch (JSONException e) {
                                 e.printStackTrace();
                                 Log.i(TAG,"Object not created");
                             }

                            //edit shared preferences to set activity_executed to true
                             SharedPreferences pref = getSharedPreferences("StockPrefs", Context.MODE_PRIVATE);
                             SharedPreferences.Editor edt = pref.edit();
                             edt.putString("stock_string7", myResponse);
                             edt.apply();
                             Toast.makeText(getApplicationContext(),"Stock added successfully",Toast.LENGTH_SHORT).show();

                         }
                     });
                }
            }
        });
    }


    protected void createStockObject(String jsonString) throws JSONException {

        //JSON object created from json string parameter
        //parse through object to find current ticker, stock name and stock price
        JSONObject obj = new JSONObject(jsonString);
        String stockTicker = obj.getJSONObject("price").getString("symbol");
        String stockName = obj.getJSONObject("price").getString("shortName");
        String stockPrice = obj.getJSONObject("price").getJSONObject("regularMarketPrice").getString("fmt");
        String marketCap = obj.getJSONObject("price").getJSONObject("marketCap").getString("fmt");

        //check if pe exists by checking if pe slot is empty or by checking if trailingPe object exists at all
        String pe;
        if(!obj.getJSONObject("summaryDetail").has("trailingPE")
                || obj.getJSONObject("summaryDetail").getJSONObject("trailingPE").length()==0)
        {
            pe = "No P/E";
        }else{
            pe = obj.getJSONObject("summaryDetail").getJSONObject("trailingPE").getString("fmt");
        }

        //check if pb exists
        String pb;
        if(obj.getJSONObject("defaultKeyStatistics").getJSONObject("priceToBook").length() == 0
                || !obj.getJSONObject("defaultKeyStatistics").has("priceToBook"))
        {
            pb = "No P/B";
        }else{
            pb = obj.getJSONObject("defaultKeyStatistics").getJSONObject("priceToBook").getString("fmt");
        }

        //check if peg exists
        String peg;
        if(obj.getJSONObject("defaultKeyStatistics").getJSONObject("pegRatio").length()==0
                || !obj.getJSONObject("defaultKeyStatistics").has("pegRatio"))
        {
            peg = "No PEG";
        }else{
            peg = obj.getJSONObject("defaultKeyStatistics").getJSONObject("pegRatio").getString("fmt");
        }

        //check if divYield exists
        String divYield;
        if(obj.getJSONObject("summaryDetail").getJSONObject("trailingAnnualDividendYield").length()==0
                || !obj.getJSONObject("summaryDetail").has("trailingAnnualDividendYield"))
        {
            divYield = "No Dividend";
        }else{
            divYield = obj.getJSONObject("summaryDetail").getJSONObject("trailingAnnualDividendYield").getString("fmt");
        }

        //check if fcf exists
        String ocf;
        if(obj.getJSONObject("financialData").getJSONObject("operatingCashflow").length()==0
                || !obj.getJSONObject("financialData").has("operatingCashflow"))
        {
            ocf = "No Free Cash Flow";
        }else{
            ocf = obj.getJSONObject("financialData").getJSONObject("operatingCashflow").getString("fmt");
        }

        //String debt = obj.getJSONObject("financialData").getJSONObject("totalDebt").getString("fmt");

        Stock stock = new Stock(-1,stockTicker,stockName,stockPrice,marketCap,pe,pb,peg,divYield,ocf);

        //save stock to database
        dbHelper.insertStock(stock);

        /*
        //find peg ratio and forward eps to calculate growth rate
        String pegRatioRaw = obj.getJSONObject("defaultKeyStatistics").getJSONObject("pegRatio").getString("raw");
        String epsForward = obj.getJSONObject("defaultKeyStatistics").getJSONObject("forwardEps").getString("raw");

        //convert strings to doubles
        double pegRatioDouble = Double.parseDouble(pegRatioRaw);
        double epsForwardDouble = Double.parseDouble(epsForward);
        double priceInt = Double.parseDouble(stockPrice);
        //calculate growth rate
        double growthRateDouble = (priceInt/epsForwardDouble)/pegRatioDouble;
        String growthRate = Double.toString(growthRateDouble);

        //obj.getString("freeCashFlow");
        String freeCashFlow = obj.getJSONObject("financialData").getJSONObject("freeCashflow").getString("raw");
        String totalCashRaw = obj.getJSONObject("financialData").getJSONObject("totalCash").getString("raw");;

        other variables used for calculation in stock class
        minimumRateofReturn =15;
        marginOfSafety  50%
        peRatio = double growthrate;

       ---------value investing discounted cash flow
        growth rate 5 yrs
        discount rate: rate of return you'd like to make
        terminal value: 10-15 times free cash flow
        latest free cash flow
        total cash (mrq)



        //create a stock object with the info obtained
        Stock stockValue = new Stock(stockTicker,stockName,stockPrice,growthRate,freeCashFlow,totalCashRaw);


         */

    }

    protected void updateStockClient(Stock stockChosen) {

        //make an okhttp client request to use yahoo finance api.
        //the api will return all stock profile info in a json file

        String url = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-summary?symbol="+stockChosen.getTicker()+"&region=US";

        //***********************************************************************************
        //API KEYS SENSITIVE DATA

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //.url("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-profile?symbol=tsla&region=US")
                .url(url)
                .get()
                .addHeader("x-rapidapi-key", "cc7d5a3a94mshf02a8d7f7daf522p1286adjsnc17159b46ff3")
                .addHeader("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .build();

        //***********************************************************************************


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String myResponse = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                // stock object is created by parsing through json file
                                Stock stock = updateStockObject(myResponse);
                                stock.setId(stockChosen.getId());
                                //save stock to database
                                dbHelper.updateStock(stock);
                                Log.i(TAG,"Stock Object Updated");
                                loadStocks();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.i(TAG,"Stock object not updated");
                            }

                        }
                    });
                }
            }
        });
    }

    protected Stock updateStockObject(String jsonString) throws JSONException {

        //JSON object created from jsonstring parameter
        //parse through object to find current ticker, stock name and stock price
        JSONObject obj = new JSONObject(jsonString);
        String stockTicker = obj.getJSONObject("price").getString("symbol");
        String stockName = obj.getJSONObject("price").getString("shortName");
        String stockPrice = obj.getJSONObject("price").getJSONObject("regularMarketPrice").getString("fmt");
        String marketCap = obj.getJSONObject("price").getJSONObject("marketCap").getString("fmt");

        //check if pe exists by checking if pe slot is empty or by checking if trailingPe object exists at all
        String pe;
        if (!obj.getJSONObject("summaryDetail").has("trailingPE")
                || obj.getJSONObject("summaryDetail").getJSONObject("trailingPE").length() == 0) {
            pe = "No P/E";
        } else {
            pe = obj.getJSONObject("summaryDetail").getJSONObject("trailingPE").getString("fmt");
        }

        //check if pb exists
        String pb;
        if (obj.getJSONObject("defaultKeyStatistics").getJSONObject("priceToBook").length() == 0
                || !obj.getJSONObject("defaultKeyStatistics").has("priceToBook")) {
            pb = "No P/B";
        } else {
            pb = obj.getJSONObject("defaultKeyStatistics").getJSONObject("priceToBook").getString("fmt");
        }

        //check if peg exists
        String peg;
        if (obj.getJSONObject("defaultKeyStatistics").getJSONObject("pegRatio").length() == 0
                || !obj.getJSONObject("defaultKeyStatistics").has("pegRatio")) {
            peg = "No PEG";
        } else {
            peg = obj.getJSONObject("defaultKeyStatistics").getJSONObject("pegRatio").getString("fmt");
        }

        //check if divYield exists
        String divYield;
        if (obj.getJSONObject("summaryDetail").getJSONObject("trailingAnnualDividendYield").length() == 0
                || !obj.getJSONObject("summaryDetail").has("trailingAnnualDividendYield")) {
            divYield = "No Dividend";
        } else {
            divYield = obj.getJSONObject("summaryDetail").getJSONObject("trailingAnnualDividendYield").getString("fmt");
        }

        //check if fcf exists
        String ocf;
        if (obj.getJSONObject("financialData").getJSONObject("operatingCashflow").length() == 0
                || !obj.getJSONObject("financialData").has("operatingCashflow")) {
            ocf = "No Free Cash Flow";
        } else {
            ocf = obj.getJSONObject("financialData").getJSONObject("operatingCashflow").getString("fmt");
        }

        //String debt = obj.getJSONObject("financialData").getJSONObject("totalDebt").getString("fmt");

        Stock stock = new Stock(-1, stockTicker, stockName, stockPrice, marketCap, pe, pb, peg, divYield, ocf);

        return stock;

    }

    private void goToProfileActivity(){
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void goToArticleActivity(){
        Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
        startActivity(intent);
    }


}