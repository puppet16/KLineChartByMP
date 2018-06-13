package com.github.mikephil.klinelib.chart;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.klinelib.model.DepthMapData;
import com.github.mikephil.klinelib.util.DateUtils;
import com.github.mikephil.klinelib.util.DoubleUtil;

/**
 * 深度图显示的MarkerView
 */

public class DepthMapChartInfoView extends LinearLayout {

    private TextView mTvType;
    private TextView mTvOrderNumber;
    private TextView mTvPrice;
    private TextView mTvTime;

    public Chart[] mLineCharts;
    protected Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            setVisibility(GONE);
            if (mLineCharts != null) {
                for (Chart chart : mLineCharts) {
                    chart.highlightValue(null);
                }
            }
        }
    };

    public DepthMapChartInfoView(Context context) {
        this(context, null);
    }

    public DepthMapChartInfoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DepthMapChartInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_depth_map_chart_info, this);
        mTvTime = findViewById(R.id.tv_time);
        mTvType = findViewById(R.id.tv_type);
        mTvOrderNumber = findViewById(R.id.tv_order_num);
        mTvPrice = findViewById(R.id.tv_price);
    }

    public void setData(DepthMapData data) {
        mTvTime.setText(DateUtils.formatDate(data.getDate()));
        mTvOrderNumber.setText(data.getVol()+"");
        mTvType.setText(data.getType() == 0 ? getResources().getString(R.string.order_buy) : getResources().getString(R.string.order_sell));
        mTvPrice.setText(DoubleUtil.formatDecimal(data.getPrice()));
        removeCallbacks(mRunnable);
        postDelayed(mRunnable, 2000);
    }

    public void setChart(Chart... chart) {
        mLineCharts = chart;
    }
}
