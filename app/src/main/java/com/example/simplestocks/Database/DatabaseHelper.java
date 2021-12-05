package com.example.simplestocks.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.simplestocks.Model.Stock;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = Config.DATABASE_NAME;
    private static final int DATABASE_VERSION = 1;

    private Context context;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        //CREATE THE TABLES

        String CREATE_STOCK_TABLE = "CREATE TABLE " + Config.TABLE_STOCK_LIST + " ("
                + Config.COLUMN_STOCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_STOCK_TICKER + " TEXT NOT NULL, "
                + Config.COLUMN_STOCK_NAME + " TEXT NOT NULL,"
                + Config.COLUMN_STOCK_PRICE + " TEXT NOT NULL, "
                + Config.COLUMN_STOCK_MC + " TEXT NOT NULL, "
                + Config.COLUMN_STOCK_PE + " TEXT NOT NULL,"
                + Config.COLUMN_STOCK_PB + " TEXT NOT NULL, "
                + Config.COLUMN_STOCK_PEG + " TEXT NOT NULL,"
                + Config.COLUMN_STOCK_DIV + " TEXT NOT NULL, "
                + Config.COLUMN_STOCK_OCF + " TEXT NOT NULL"
                + ")";

        Log.d(TAG, "Table created with this query: " + CREATE_STOCK_TABLE);

        sqLiteDatabase.execSQL(CREATE_STOCK_TABLE);

        Log.d(TAG, "Course table created");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // ALTER THE DESIGN FOR AN UPDATE

    }

    public long insertStock(Stock stock) {
        long id = -1;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_STOCK_TICKER, stock.getTicker());
        contentValues.put(Config.COLUMN_STOCK_NAME, stock.getName());
        contentValues.put(Config.COLUMN_STOCK_PRICE, stock.getPrice());
        contentValues.put(Config.COLUMN_STOCK_MC, stock.getMarketCap());
        contentValues.put(Config.COLUMN_STOCK_PE, stock.getPe());
        contentValues.put(Config.COLUMN_STOCK_PB, stock.getPb());
        contentValues.put(Config.COLUMN_STOCK_PEG, stock.getPeg());
        contentValues.put(Config.COLUMN_STOCK_DIV, stock.getDivYield());
        contentValues.put(Config.COLUMN_STOCK_OCF, stock.getOcf());

        try {
            id = db.insertOrThrow(Config.TABLE_STOCK_LIST, null, contentValues);
        } catch (SQLiteException e) {
            Log.d(TAG, "Exception: " + e.getMessage());
            Toast.makeText(context, "Operation Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }

        return id;
    }

    public List<Stock> getAllStocks() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        try {

            cursor = db.query(Config.TABLE_STOCK_LIST, null, null, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    List<Stock> stockList = new ArrayList<>();

                    do {
                        //getting information from cursor

                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STOCK_ID));
                        String ticker = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_TICKER));
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_NAME));
                        String price = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_PRICE));
                        String mc = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_MC));
                        String pe = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_PE));
                        String pb = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_PB));
                        String peg = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_PEG));
                        String div = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_DIV));
                        String ocf = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_OCF));

                        //adding this stock to stockList
                        stockList.add(0,new Stock(id, ticker, name, price, mc, pe, pb, peg, div, ocf));

                    } while (cursor.moveToNext());

                    //returns an arrayList of all stocks in the database
                    return stockList;
                }

            }
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
            db.close();
        }
        return Collections.emptyList();
    }

    public void deleteStockByID(String ID) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Config.TABLE_STOCK_LIST, Config.COLUMN_STOCK_ID + " =?", new String[]{ID});

    }

    public List<String> getStockTickers() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        try {

            cursor = db.query(Config.TABLE_STOCK_LIST, null, null, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    List<String> stockTickerList = new ArrayList<>();

                    do {
                        //getting information from cursor
                        String ticker = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_TICKER));

                        stockTickerList.add(ticker);

                    } while (cursor.moveToNext());

                    Log.i(TAG,stockTickerList.toString());
                    return stockTickerList;
                }
            }
        }catch (Exception e) {
            Log.d(TAG, "Exception: " + e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
            db.close();
        }
        return Collections.emptyList();
    }

    public void updateStock(Stock stock) {
        SQLiteDatabase db = this.getWritableDatabase();

        String stockID = String.valueOf(stock.getId());

        db.delete(Config.TABLE_STOCK_LIST,Config.COLUMN_STOCK_ID + " =?",new String[]{stockID});

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_STOCK_TICKER, stock.getTicker());
        contentValues.put(Config.COLUMN_STOCK_NAME, stock.getName());
        contentValues.put(Config.COLUMN_STOCK_PRICE, stock.getPrice());
        contentValues.put(Config.COLUMN_STOCK_MC, stock.getMarketCap());
        contentValues.put(Config.COLUMN_STOCK_PE, stock.getPe());
        contentValues.put(Config.COLUMN_STOCK_PB, stock.getPb());
        contentValues.put(Config.COLUMN_STOCK_PEG, stock.getPeg());
        contentValues.put(Config.COLUMN_STOCK_DIV, stock.getDivYield());
        contentValues.put(Config.COLUMN_STOCK_OCF, stock.getOcf());

        long result = db.update(Config.TABLE_STOCK_LIST,contentValues,Config.COLUMN_STOCK_ID + " =?",new String[]{stockID});
        if(result ==-1){
            Toast.makeText(context,"Failed to update.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Data updated succesfully", Toast.LENGTH_SHORT).show();
        }

    }

}



