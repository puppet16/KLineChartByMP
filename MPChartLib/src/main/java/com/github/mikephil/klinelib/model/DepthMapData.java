package com.github.mikephil.klinelib.model;

/**
 * Desc: 深度图数据
 * Author ltt
 * Email: litt@mixotc.com
 * Date:  2018/6/12.
 */
public class DepthMapData {
    private double price;//收盘价
    private long vol;//数量
    private long date;//时间
    private int type;//类型：0买；1卖

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getVol() {
        return vol;
    }

    public void setVol(long vol) {
        this.vol = vol;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DepthMapData{" +
                "price=" + price +
                ", vol=" + vol +
                ", date=" + date +
                ", type=" + type +
                '}';
    }
}
