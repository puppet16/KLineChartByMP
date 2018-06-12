package com.ltt.klinechart.klinechartbymp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_kline:
                intent = new Intent(this,KLineActivity.class);
                break;
            case R.id.btn_fen:
                intent = new Intent(this,TimeLineActivity.class);
                break;
            case R.id.btn_depthmap:
                intent = new Intent(this,DepthMapActivity.class);
                break;
        }
        startActivity(intent);
    }
}
