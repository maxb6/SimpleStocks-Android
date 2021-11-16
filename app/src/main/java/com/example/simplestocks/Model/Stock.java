package com.example.simplestocks.Model;

import java.lang.reflect.Array;
import java.util.stream.DoubleStream;

public class Stock {
    private long id;
    private String ticker;
    private String name;
    private String price;
    private String marketCap;
    private String pe;
    private String pb;
    private String peg;
    private String divYield;
    private String ocf;

    private String debt;
    private String growthRate;
    private String totalCash;

    public Stock(){}

    public Stock(long id,String ticker, String name, String price, String marketCap, String pe, String pb, String peg, String divYield,String ocf) {
        this.id = id;
        this.ticker = ticker;
        this.name = name;
        this.price = price;
        this.marketCap = marketCap;
        this.pe = pe;
        this.pb = pb;
        this.peg = peg;
        this.divYield = divYield;
        this.ocf = ocf;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(String growthRate) {
        this.growthRate = growthRate;
    }

    public String getOcf() {
        return ocf;
    }

    public void setOcf(String ocf) {
        this.ocf = ocf;
    }

    public String getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(String totalCash) {
        this.totalCash = totalCash;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "ticker='" + ticker + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", marketCap='" + marketCap + '\'' +
                ", pe='" + pe + '\'' +
                ", pb='" + pb + '\'' +
                ", peg='" + peg + '\'' +
                ", divYield='" + divYield + '\'' +
                ", ocf='" + ocf + '\'' +
                '}';
    }

    public String getPe() {
        return pe;
    }

    public void setPe(String pe) {
        this.pe = pe;
    }

    public String getPb() {
        return pb;
    }

    public void setPb(String pb) {
        this.pb = pb;
    }

    public String getPeg() {
        return peg;
    }

    public void setPeg(String peg) {
        this.peg = peg;
    }

    public String getDivYield() {
        return divYield;
    }

    public void setDivYield(String divYield) {
        this.divYield = divYield;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String findIntrinsicValue(double discountRate){

        double terminalValue = 15;
        double fcf = Double.parseDouble(ocf);
        double totCash = Double.parseDouble(totalCash);
        double gRate = Double.parseDouble(growthRate);
        double ivMarketCap;
        double iv;
        double[] presentValues = new double[0];
        double dRate=1+(discountRate*0.01);

        //grow free cash flow for 5 years at growth rate
        //calculate present values in an array for each year
        for(int years =1;years<=6;years++){
            presentValues[years]=fcf/(Math.pow(dRate,years));
            fcf = fcf+(fcf*gRate);
        }

        double totalPresentValue = 0;
        //find total value of array
        for(int i=0; i<presentValues.length;i++){
            totalPresentValue=presentValues[i];
        }

        //multiply free cash flow at year 5 times terminal value to find saleValue
        double fiveYearSaleValue = fcf * terminalValue;
        double fiveYearPV = fiveYearSaleValue/(Math.pow(dRate,6));
        double pvOfFutureCashFlows = totalPresentValue+fiveYearPV;

        iv = pvOfFutureCashFlows+totCash;
        String intrinsicValue = Double.toString(iv);

        return intrinsicValue;
    }

}
