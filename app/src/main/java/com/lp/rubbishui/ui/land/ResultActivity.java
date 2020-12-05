package com.lp.rubbishui.ui.land;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lp.rubbishui.R;
import com.lp.rubbishui.event.BackEvent;
import com.lp.rubbishui.utils.CountDownTimeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class ResultActivity extends AppCompatActivity {

    private TextView jfTv,resultTv,backTv;

    private CountDownTimeUtil timeUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        jfTv = findViewById(R.id.tv_jf);
        resultTv = findViewById(R.id.tv_result);
        backTv = findViewById(R.id.tv_back);
        EventBus.getDefault().register(this);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeUtil.cancel();
                finish();
            }
        });

        timeUtil = new CountDownTimeUtil(backTv);
        timeUtil.runTimer();
    }

    @Subscribe
    public void onEventMainThread(BackEvent event) {
            ((Activity)ResultActivity.this).finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
