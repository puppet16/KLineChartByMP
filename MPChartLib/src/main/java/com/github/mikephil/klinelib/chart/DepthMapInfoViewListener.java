package com.github.mikephil.klinelib.chart;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.klinelib.model.DepthMapData;
import com.github.mikephil.klinelib.model.HisData;
import com.github.mikephil.klinelib.util.DisplayUtils;

import java.util.List;

/**
 * 深度图值选中事件处理
 */

public class DepthMapInfoViewListener implements OnChartValueSelectedListener {

    private List<DepthMapData> mList;
    private DepthMapChartInfoView mInfoView;
    private int mWidth;

    public DepthMapInfoViewListener(Context context,List<DepthMapData> list, DepthMapChartInfoView infoView) {
        mWidth = DisplayUtils.getWidthHeight(context)[0];
        mList = list;
        mInfoView = infoView;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        int x = (int) e.getX();
        if (x < mList.size()) {
            mInfoView.setVisibility(View.VISIBLE);
            mInfoView.setData(mList.get(x));
        }
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mInfoView.getLayoutParams();
        if (h.getXPx() < mWidth / 2) {
            lp.gravity = Gravity.RIGHT;
        } else {
            lp.gravity = Gravity.LEFT;
        }
        mInfoView.setLayoutParams(lp);
    }

    @Override
    public void onNothingSelected() {
        mInfoView.setVisibility(View.GONE);
    }
}
