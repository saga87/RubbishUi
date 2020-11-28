package com.lp.rubbishui.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lp.rubbishui.R;
import com.lp.rubbishui.event.SettingFinishEvent;
import com.lp.rubbishui.utils.CommonUtils;
import com.lp.rubbishui.utils.SharedPrefsUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ChooseModeActivity extends AppCompatActivity {

    private String type = "";
    private ImageView chooseIv;
    private TextView chooseTv,chooseTipTv,backTv,sureTv;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);
        context = this;
        EventBus.getDefault().register(this);
        initView();
        Bundle b = getIntent().getExtras();
        if (b!=null){
            type = b.getString("type");
            if (!TextUtils.isEmpty(type)){
                solve(type);
            }
        }
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.jumpTo(ChooseModeActivity.this,SettingSuccessActivity.class);
                SharedPrefsUtil.putValue(context,"type",type);
            }
        });
    }

    @Subscribe
    public void onEventMainThread(SettingFinishEvent event) {
        if (context!=null){
            ((Activity)context).finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        chooseIv = findViewById(R.id.iv_choose);
        chooseTv = findViewById(R.id.tv_choose);
        chooseTipTv = findViewById(R.id.tv_choose_tip);
        backTv = findViewById(R.id.tv_back);
        sureTv = findViewById(R.id.tv_sure);
    }

    private void solve(String type) {
        if ("yf".equals(type)){
            chooseIv.setImageDrawable(getResources().getDrawable(R.drawable.tong_yf));
            chooseTv.setText("你选择的是  易腐垃圾");
            chooseTipTv.setText("设置成功后该设备的桶内只能投放易腐垃圾");
        }else if ("other".equals(type)){
            chooseIv.setImageDrawable(getResources().getDrawable(R.drawable.tong_other));
            chooseTv.setText("你选择的是  其他垃圾");
            chooseTipTv.setText("设置成功后该设备的桶内只能投放其他垃圾");
        }else if ("hs".equals(type)){
            chooseIv.setImageDrawable(getResources().getDrawable(R.drawable.tong_hs));
            chooseTv.setText("你选择的是  可回收物");
            chooseTipTv.setText("设置成功后该设备的桶内只能投放可回收物");
        }else {
            chooseIv.setImageDrawable(getResources().getDrawable(R.drawable.tong_harm));
            chooseTv.setText("你选择的是  有害垃圾");
            chooseTipTv.setText("设置成功后该设备的桶内只能投放有害垃圾");
        }
    }
}
