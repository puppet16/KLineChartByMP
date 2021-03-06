package com.ltt.klinechart.klinechartbymp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.klinelib.chart.TimeLineView;
import com.github.mikephil.klinelib.model.HisData;
import com.ltt.klinechart.klinechartbymp.util.Util;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class TimeLineActivity extends AppCompatActivity {

    private TimeLineView mTimeLineView;
    private int mType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_timeline);
        mTimeLineView = findViewById(R.id.timeLine);
        mTimeLineView.setDateFormat("HH:mm");
        int count = 241;
        mTimeLineView.setCount(count, count, count);
        initData();
    }

    protected void initData() {
        final List<HisData> hisData = Util.get1Day(this);
        mTimeLineView.setLastClose(hisData.get(0).getClose());
        mTimeLineView.initData(hisData);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mTimeLineView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = (int) (Math.random() * 100);
                        HisData data = hisData.get(index);
                        HisData lastData = hisData.get(hisData.size() - 1);
                        HisData newData = new HisData();
                        newData.setVol(data.getVol());
                        newData.setClose(data.getClose());
                        newData.setHigh(Math.max(data.getHigh(), lastData.getClose()));
                        newData.setLow(Math.min(data.getLow(), lastData.getClose()));
                        newData.setOpen(lastData.getClose());
                        newData.setDate(System.currentTimeMillis());
                        hisData.add(newData);
                        mTimeLineView.addData(newData);
                    }
                });
            }
        }, 1000, 500);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mTimeLineView.post(new Runnable() {
                    @Override
                    public void run() {
                        mTimeLineView.refreshData((float) (hisData.get(0).getClose() + 10 * Math.random()));
                    }
                });
            }
        }, 1000, 1000);
    }

}
