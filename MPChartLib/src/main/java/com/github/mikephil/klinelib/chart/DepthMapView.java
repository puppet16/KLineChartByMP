package com.github.mikephil.klinelib.chart;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.github.mikephil.charting.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

/**
 * Desc:
 * Author ltt
 * Email: litt@mixotc.com
 * Date:  2018/6/11.
 */
public class DepthMapView extends LinearLayout {
    protected Context mContext;
    protected CombinedChart mChart;

    public DepthMapView(Context context) {
        this(context, null);
    }

    public DepthMapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DepthMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_depthmap, this);
        mChart = findViewById(R.id.depth_map_chart);
        mChart.getDescription().setEnabled(false);//设置不显示右下角描述
        mChart.setDrawGridBackground(false);//是否绘制网格背景
        mChart.setDrawBorders(false);
        mChart.setTouchEnabled(false); // 设置是否可以触摸
        mChart.setDragEnabled(false);// 是否可以拖拽
        mChart.setScaleEnabled(false);// 是否可以缩放
        //图例说明
        Legend legend = mChart.getLegend();
        legend.setEnabled(false);//不显示图例
        YAxis rightAxis = mChart.getAxisRight();
        YAxis leftAxis = mChart.getAxisLeft();
        //右Y轴设置
        rightAxis.setDrawGridLines(false);//是否绘制轴线
        rightAxis.setAxisMinimum(0f);
        rightAxis.setStartAtZero(true);//设置坐标轴是否从0开始
        //左Y轴设置
        leftAxis.setDrawGridLines(false);//是否绘制轴线
        leftAxis.setAxisMinimum(0f);
        leftAxis.setStartAtZero(true);//设置坐标轴是否从0开始
    }

    public void setData(ArrayList<Float> xIndex, ArrayList<Float> yIndex) {
        ArrayList<Entry> leftValues = new ArrayList<>();
        ArrayList<Entry> rightValues = new ArrayList<>();
        boolean isZero = false;
        for (int i = 0; i < xIndex.size(); i++) {

            if (yIndex.get(i) <= 0) {
                isZero = true;
                leftValues.add(new Entry(xIndex.get(i), 0, getResources().getDrawable(R.drawable.star)));
            }
            if (isZero) {
                rightValues.add(new Entry(xIndex.get(i), yIndex.get(i), getResources().getDrawable(R.drawable.star)));
            } else {
                leftValues.add(new Entry(xIndex.get(i), yIndex.get(i), getResources().getDrawable(R.drawable.star)));
            }
        }

        LineDataSet leftSet, rightSet;
        // create a dataset and give it a type
        leftSet = new LineDataSet(leftValues, "left");
        leftSet.setColor(Color.BLACK);
        leftSet.setLineWidth(1f);
        leftSet.setCircleRadius(0f);
        leftSet.setValueTextSize(9f);
        leftSet.setDrawFilled(true);
        leftSet.setDrawValues(false);
        leftSet.setDrawCircleHole(false);//将此设置为true，以便在每个数据圆中绘制一个孔。
        leftSet.setDrawCircles(false);//设置折线节点圆是否显示
        leftSet.setDrawIcons(false);
        leftSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        rightSet = new LineDataSet(rightValues, "right");
        rightSet.setColor(Color.RED);
        rightSet.setLineWidth(1f);
        rightSet.setCircleRadius(0f);
        rightSet.setValueTextSize(9f);
        rightSet.setDrawFilled(true);
        rightSet.setFormLineWidth(1f);
        rightSet.setDrawValues(false);
        rightSet.setDrawCircleHole(false);//将此设置为true，以便在每个数据圆中绘制一个孔。
        rightSet.setDrawCircles(false);//设置折线节点圆是否显示
        rightSet.setDrawIcons(false);
        rightSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            leftSet.setFillColor(Color.parseColor("#AAEEAA"));
            rightSet.setFillColor(Color.parseColor("#EEAAAA"));
        } else {
            leftSet.setFillColor(Color.BLACK);
            rightSet.setFillColor(Color.BLACK);
        }

        ArrayList<ILineDataSet> dataSet = new ArrayList<ILineDataSet>();
        dataSet.add(leftSet); // add the datasets
        dataSet.add(rightSet);

        LineData lineData = new LineData(dataSet);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        // set data
        mChart.setData(combinedData);
        mChart.invalidate();

    }
}
