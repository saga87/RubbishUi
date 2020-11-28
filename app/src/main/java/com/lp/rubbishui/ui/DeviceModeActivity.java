package com.lp.rubbishui.ui;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lp.rubbishui.R;
import com.lp.rubbishui.event.SettingFinishEvent;
import com.lp.rubbishui.utils.CommonUtils;
import com.lp.rubbishui.utils.SharedPrefsUtil;
import com.lp.rubbishui.utils.ToastUtil;
import com.lp.rubbishui.utils.Vbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DeviceModeActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView yfBtn,otherBtn,hsBtn,harmBtn;
    private Context context;

    private LinearLayout adminLL,validLL,postLL;

    private ImageView tongIv;
    private TextView cardTipTv;

    private String type = "";

    private boolean isDoorClose = true;
    private TextView backTv;

    boolean state = false;
    Vbar b = new Vbar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_mode);
        context = this;
        EventBus.getDefault().register(this);
        initView();
        type = SharedPrefsUtil.getValue(context,"type","");
        if (!TextUtils.isEmpty(type)){
            adminLL.setVisibility(View.GONE);
            validLL.setVisibility(View.VISIBLE);
            //开启扫码线程
            startQrCode();
            //提示刷卡/扫二维码开门
            solve(type);
            backTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    postLL.setVisibility(View.GONE);
                    validLL.setVisibility(View.VISIBLE);
                    solve(type);
                    isDoorClose = true;
                }
            });
        }
    }


    private void startQrCode(){
        state = b.vbarOpen();
        if (state) {
            ToastUtil.showToast("扫码器开启成功");

            Thread t = new Thread(){
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    super.run();
                    while(true)
                    {
                        final String str = b.getResultsingle();
                        if(str != null)
                        {
                            if (str.contains("CTX2012")){
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        {
                                            if (isDoorClose){
                                                tongIv.setImageDrawable(getResources().getDrawable(R.drawable.open_hs));
                                                cardTipTv.setText("你好，"+str+"桶已经打开，请投递可回收垃圾");
                                                isDoorClose = false;
                                            }
                                        }
                                    }
                                });
                            }

                        }
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            };
            t.start();


        } else {
            ToastUtil.showToast("扫码器开启失败");
        }
    }

    private void solve(String type) {
        if ("yf".equals(type)){
            tongIv.setImageDrawable(getResources().getDrawable(R.drawable.tong_yf));
            cardTipTv.setText("请刷卡/扫码验证身份\n" +
                    "投递易腐垃圾");
        }else if ("other".equals(type)){
            tongIv.setImageDrawable(getResources().getDrawable(R.drawable.tong_other));
            cardTipTv.setText("请刷卡/扫码验证身份\n" +
                    "投递其他垃圾");
        }else if ("hs".equals(type)){
            tongIv.setImageDrawable(getResources().getDrawable(R.drawable.tong_hs));
            cardTipTv.setText("请刷卡/扫码验证身份\n" +
                    "投递可回收垃圾");
        }else {
            tongIv.setImageDrawable(getResources().getDrawable(R.drawable.tong_harm));
            cardTipTv.setText("请刷卡/扫码验证身份\n" +
                    "投递有害垃圾");
        }

        tongIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDoorClose){
//                    tongIv.setImageDrawable(getResources().getDrawable(R.drawable.open_hs));
//                    cardTipTv.setText("你好，李玲丽 桶门已经打开，请投递可回收垃圾");
//                    isDoorClose = false;
                }else {
                    validLL.setVisibility(View.GONE);
                    postLL.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(SettingFinishEvent event) {
        adminLL.setVisibility(View.GONE);
        validLL.setVisibility(View.VISIBLE);
    }

    private void initView() {
        yfBtn = findViewById(R.id.iv_yf);
        otherBtn = findViewById(R.id.iv_other);
        hsBtn = findViewById(R.id.iv_hs);
        harmBtn = findViewById(R.id.iv_harm);
        yfBtn.setOnClickListener(this);
        otherBtn.setOnClickListener(this);
        hsBtn.setOnClickListener(this);
        harmBtn.setOnClickListener(this);
        adminLL = findViewById(R.id.ll_admin);
        validLL = findViewById(R.id.ll_valid);
        tongIv = findViewById(R.id.iv_tong);
        cardTipTv = findViewById(R.id.tv_card_tip);
        backTv = findViewById(R.id.tv_back_main);
        postLL = findViewById(R.id.ll_post_success);
    }

    @Override
    public void onClick(View view) {
        Bundle b = new Bundle();
        switch (view.getId()){
            case R.id.iv_yf:
                b.putString("type","yf");
                CommonUtils.jumpTo(DeviceModeActivity.this,ChooseModeActivity.class,b);
                break;
            case R.id.iv_other:
                b.putString("type","other");
                CommonUtils.jumpTo(DeviceModeActivity.this,ChooseModeActivity.class,b);
                break;
            case R.id.iv_hs:
                b.putString("type","hs");
                CommonUtils.jumpTo(DeviceModeActivity.this,ChooseModeActivity.class,b);
                break;
            case R.id.iv_harm:
                b.putString("type","harm");
                CommonUtils.jumpTo(DeviceModeActivity.this,ChooseModeActivity.class,b);
                break;
        }
    }
}