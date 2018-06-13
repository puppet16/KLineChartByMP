package com.ltt.klinechart.klinechartbymp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.mikephil.klinelib.chart.DepthMapView;
import com.github.mikephil.klinelib.model.DepthMapData;

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
        setContentView(R.layout.activity_demo_depthmap);
        mDepthMap = findViewById(R.id.depth_map);
        initData();
    }
    private void initData(){

        ArrayList<DepthMapData> leftIndex = new ArrayList<>();
        ArrayList<DepthMapData> rightIndex = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            DepthMapData depthMapData = new DepthMapData();
            depthMapData.setPrice(i);
            depthMapData.setType(0);
            depthMapData.setVol(random.nextInt(80-70)+70);
            leftIndex.add(depthMapData);

            Log.d("LeftData:",depthMapData.toString());
        }
        DepthMapData depthMapData1 = new DepthMapData();
        depthMapData1.setPrice(20);
        depthMapData1.setType(0);
        depthMapData1.setVol(10);
        leftIndex.add(depthMapData1);

//        DepthMapData depthMapData2 = new DepthMapData();
//        depthMapData2.setPrice(21);
//        depthMapData2.setType(0);
//        depthMapData2.setVol(0);
//        leftIndex.add(depthMapData2);
//
//        DepthMapData depthMapData4 = new DepthMapData();
//        depthMapData4.setPrice(22);
//        depthMapData4.setType(0);
//        depthMapData4.setVol(0);
//        rightIndex.add(depthMapData4);
//
//        DepthMapData depthMapData3 = new DepthMapData();
//        depthMapData3.setPrice(23);
//        depthMapData3.setType(0);
//        depthMapData3.setVol(10);
//        rightIndex.add(depthMapData3);
        for (int i = 0; i < 20; i++) {
            DepthMapData depthMapData = new DepthMapData();
            depthMapData.setPrice(21+i);
            depthMapData.setType(1);
            depthMapData.setVol(random.nextInt(80-60)+60);
            rightIndex.add(depthMapData);

            Log.d("RightData:",depthMapData.toString());
        }
        mDepthMap.setData(leftIndex,rightIndex);
    }
    public void onAddClick(View v) {
//        ArrayList<Float> xIndex = new ArrayList<>();
//        ArrayList<Float> yIndex = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 50; i++) {
//            xIndex.add((float) i);
//            if(i==25) {
//                yIndex.add(0f);
//            }else {
//                yIndex.add((float) (random.nextInt(80-1)+1));
//            }
//        }
//        mDepthMap.setData(xIndex,yIndex);
    }
}
