package com.ltt.klinechart.klinechartbymp;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.klinelib.chart.KLineView;
import com.github.mikephil.klinelib.model.HisData;
import com.ltt.klinechart.klinechartbymp.util.Util;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Desc:
 * Author ltt
 * Email: litt@mixotc.com
 * Date:  2018/6/11.
 */
public class KLineActivity extends AppCompatActivity {
    private static final String TAG = "KLineActivity";
    private KLineView mKLineView;
    private int mDay = 7;
    private LinearLayout mLlParentView;
    private View mView;
    private int mLocation = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = LayoutInflater.from(this).inflate(R.layout.activity_kline_chart, null);
        setContentView(mView);
        mKLineView = findViewById(R.id.kline);
        mLlParentView = findViewById(R.id.ll_kline_parent);
        RadioGroup rgIndex = findViewById(R.id.rg_index);
        mKLineView.setDateFormat("yyyy-MM-dd");
        mLocation = mKLineView.MIN_COUNT;
        rgIndex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.cb_vol) {
                    showVolume();
                } else if (checkedId == R.id.cb_macd) {
                    showMacd();
                } else if (checkedId == R.id.cb_kdj) {
                    showKdj();
                }
            }
        });
        initData();
        ((RadioButton) rgIndex.getChildAt(0)).setChecked(true);
    }

    public void showVolume() {

        mKLineView.post(new Runnable() {
            @Override
            public void run() {
                mKLineView.showVolume();
            }
        });
    }

    public void showMacd() {
        mKLineView.post(new Runnable() {
            @Override
            public void run() {
                mKLineView.showMacd();
            }
        });
    }

    public void showKdj() {
        mKLineView.post(new Runnable() {
            @Override
            public void run() {
                mKLineView.showKdj();
            }
        });
    }

    protected void initData() {
        final List<HisData> hisDataList = Util.getK(this, mDay);
        mKLineView.initData(hisDataList);
//        mKLineView.setOnChartScrollEndListener(new CoupleChartGestureListener.OnChartScrollEndListener() {
//            @Override
//            public void onChartScrollLeftEnd() {
//                Log.d(TAG, "到头了，加载呐");
//                int index = (int) (Math.random() * hisDataList.size());
//                HisData data = hisDataList.get(index);
//                HisData lastData = hisDataList.get(hisDataList.size() - 1);
//                HisData newData = new HisData();
//                newData.setVol(data.getVol());
//                newData.setClose(data.getClose());
//                newData.setHigh(Math.max(data.getHigh(), lastData.getClose()));
//                newData.setLow(Math.min(data.getLow(), lastData.getClose()));
//                newData.setOpen(lastData.getClose());
//                newData.setDate(System.currentTimeMillis());
//                hisDataList.add(0,newData);
//                mKLineView.addData(newData);
//            }
//        });
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mKLineView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = (int) (Math.random() * hisDataList.size());
                        HisData data = hisDataList.get(index);
                        HisData lastData = hisDataList.get(hisDataList.size() - 1);
                        HisData newData = new HisData();
                        newData.setVol(data.getVol());
                        newData.setClose(data.getClose());
                        newData.setHigh(Math.max(data.getHigh(), lastData.getClose()));
                        newData.setLow(Math.min(data.getLow(), lastData.getClose()));
                        newData.setOpen(lastData.getClose());
                        newData.setDate(System.currentTimeMillis());
                        hisDataList.add(newData);
                        mKLineView.addData(newData);
                    }
                });
            }
        }, 1000, 1000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mKLineView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = (int) (Math.random() * (hisDataList.size()));
                        HisData data = hisDataList.get(index);
                        mKLineView.refreshData((float) data.getClose());
                    }
                });
            }
        }, 500, 1000);
    }

    public void btnOnClick(View v) {
        boolean isVertical = (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
        if (isVertical) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mLocation = mKLineView.getLowestVisibleX();
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLlParentView.removeView(mKLineView);
            setContentView(mKLineView);
            mKLineView.moveViewToX(mLocation);
            Log.d(TAG, "切成横屏  mLocation:"+mLocation);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ((ViewGroup) mKLineView.getParent()).removeView(mKLineView);//先得到横屏时的右侧x坐标
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mKLineView.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            mKLineView.setLayoutParams(layoutParams);
            mLlParentView.addView(mKLineView);
            setContentView(mView);
            mKLineView.moveViewToX(mLocation);
            Log.d(TAG, "切成竖屏  mLocation:"+mLocation);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        } else {
            super.onBackPressed();
        }
    }
}
