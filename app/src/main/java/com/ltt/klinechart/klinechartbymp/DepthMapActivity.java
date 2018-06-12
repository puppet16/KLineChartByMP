package com.ltt.klinechart.klinechartbymp;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mikephil.klinelib.chart.DepthMapView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Desc:
 * Author ltt
 * Email: litt@mixotc.com
 * Date:  2018/6/11.
 */
public class DepthMapActivity extends AppCompatActivity {

    private DepthMapView mDepthMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depthmap);
        mDepthMap = findViewById(R.id.depth_map);
        initData();
    }
    private void initData(){

        ArrayList<Float> xIndex = new ArrayList<>();
        ArrayList<Float> yIndex = new ArrayList<>();

        for (int i = 0; i < 80; i++) {
            xIndex.add((float) i);
            yIndex.add((float) Math.abs(40-i));
        }
        mDepthMap.setData(xIndex,yIndex);
    }
    public void onAddClick(View v) {
        ArrayList<Float> xIndex = new ArrayList<>();
        ArrayList<Float> yIndex = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            xIndex.add((float) i);
            if(i==25) {
                yIndex.add(0f);
            }else {
                yIndex.add((float) (random.nextInt(80-1)+1));
            }
        }
        mDepthMap.setData(xIndex,yIndex);
    }
}
