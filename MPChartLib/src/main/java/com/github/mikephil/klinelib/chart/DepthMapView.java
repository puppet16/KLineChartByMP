package com.github.mikephil.klinelib.chart;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.github.mikephil.charting.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.klinelib.model.DepthMapData;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Author ltt
 * Email: litt@mixotc.com
 * Date:  2018/6/11.
 */
public class DepthMapView extends LinearLayout {
    protected Context mContext;
    protected CombinedChart mChart;
    protected DepthMapChartInfoView mMarkerView;
    protected List<DepthMapData> mData = new ArrayList<>();

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
        mMarkerView = findViewById(R.id.d_info);
        initChart();
    }

    private void initChart() {
        mMarkerView.setChart(mChart);
        mChart.getDescription().setEnabled(false);//设置不显示右下角描述
        mChart.setDrawGridBackground(false);//是否绘制网格背景
        mChart.setDrawBorders(false);
        mChart.setTouchEnabled(true); // 设置是否可以触摸
        mChart.setDragEnabled(false);// 是否可以拖拽
        mChart.setScaleEnabled(false);// 是否可以缩放
        //图例说明
        Legend legend = mChart.getLegend();
        legend.setEnabled(true);//不显示图例
        //设置图例文字大小
        legend.setTextSize(50f);
        legend.setTextColor(Color.WHITE);
        //设置图例位置
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        //设置图例形状:如SQUARE、CIRCLE、LINE、DEFAULT
        legend.setForm(Legend.LegendForm.SQUARE);
        //设置图例XY方向的间隔宽度
        legend.setXEntrySpace(20f);
        legend.setYEntrySpace(20f);
        //设置图例标签到文字之间的距离
        legend.setFormToTextSpace(20f);
        //设置文本包装
        legend.setWordWrapEnabled(true);

        YAxis rightAxis = mChart.getAxisRight();
        YAxis leftAxis = mChart.getAxisLeft();
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(getResources().getColor(R.color.axis_color));
        //右Y轴设置
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);//是否绘制轴线
        rightAxis.setAxisMinimum(0f);
        rightAxis.setStartAtZero(true);//设置坐标轴是否从0开始
        rightAxis.setTextColor(getResources().getColor(R.color.axis_color));
        //左Y轴设置
        leftAxis.setDrawGridLines(false);//是否绘制轴线
        leftAxis.setAxisMinimum(0f);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setStartAtZero(true);//设置坐标轴是否从0开始
        leftAxis.setTextColor(getResources().getColor(R.color.axis_color));
        mChart.setOnChartValueSelectedListener(new DepthMapInfoViewListener(mContext, mData, mMarkerView));
    }

    public void setData(ArrayList<DepthMapData> buyData, ArrayList<DepthMapData> sellData) {
        mData.addAll(buyData);
        mData.addAll(sellData);
        for (int i = 0; i < mData.size(); i++) {
            Log.d("DepthMapView:", mData.get(i).toString());
        }
        ArrayList<Entry> leftValues = new ArrayList<>();
        ArrayList<Entry> rightValues = new ArrayList<>();
        for (int i = 0; i < buyData.size(); i++) {
            leftValues.add(new Entry((float) buyData.get(i).getPrice(), buyData.get(i).getVol(), getResources().getDrawable(R.drawable.star)));
        }
        for (int j = 0; j < sellData.size(); j++) {
            rightValues.add(new Entry((float) sellData.get(j).getPrice(), sellData.get(j).getVol(), getResources().getDrawable(R.drawable.star)));
        }

        LineDataSet leftSet, rightSet;
        // create a dataset and give it a type
        leftSet = new LineDataSet(leftValues, "买");
        leftSet.setColor(Color.parseColor("#AAEEAA"));
        leftSet.setLineWidth(1f);
        leftSet.setCircleRadius(0f);
        leftSet.setValueTextSize(9f);
        leftSet.setDrawFilled(true);
        leftSet.setDrawValues(false);
        leftSet.setDrawCircleHole(false);//将此设置为true，以便在每个数据圆中绘制一个孔。
        leftSet.setDrawCircles(false);//设置折线节点圆是否显示
        leftSet.setDrawIcons(false);
        leftSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        rightSet = new LineDataSet(rightValues, "卖");
        rightSet.setColor(Color.parseColor("#EEAAAA"));
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
