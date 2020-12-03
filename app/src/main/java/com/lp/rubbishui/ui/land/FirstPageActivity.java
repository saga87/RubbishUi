package com.lp.rubbishui.ui.land;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lp.rubbishui.R;
import com.lp.rubbishui.utils.CommonUtils;
import com.lp.rubbishui.utils.ToastUtil;
import com.lp.rubbishui.utils.Vbar;

public class FirstPageActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv0,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,clearTv,sureTv;
    private TextView mobileTv;
    private String mobile = "";

    boolean state = false;
    Vbar b = new Vbar();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        initView();
        startQrCode();
    }

    private void initView() {
        tv0 = findViewById(R.id.tv0);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        tv7 = findViewById(R.id.tv7);
        tv8 = findViewById(R.id.tv8);
        tv9 = findViewById(R.id.tv9);
        clearTv = findViewById(R.id.tv_clear);
        sureTv = findViewById(R.id.tv_sure);
        mobileTv = findViewById(R.id.tv_phone);
        tv0.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);
        clearTv.setOnClickListener(this);
        sureTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv0:
                if (checkMobile(mobile)){
                    mobile = mobile+"0";
                    mobileTv.setText(mobile);
                }
                break;
            case R.id.tv1:
                if (checkMobile(mobile)){
                    mobile = mobile+"1";
                    mobileTv.setText(mobile);
                }
                break;
            case R.id.tv2:
                if (checkMobile(mobile)){
                    mobile = mobile+"2";
                    mobileTv.setText(mobile);
                }
                break;
            case R.id.tv3:
                if (checkMobile(mobile)){
                    mobile = mobile+"3";
                    mobileTv.setText(mobile);
                }
                break;
            case R.id.tv4:
                if (checkMobile(mobile)){
                    mobile = mobile+"4";
                    mobileTv.setText(mobile);
                }
                break;
            case R.id.tv5:
                if (checkMobile(mobile)){
                    mobile = mobile+"5";
                    mobileTv.setText(mobile);
                }
                break;
            case R.id.tv6:
                if (checkMobile(mobile)){
                    mobile = mobile+"6";
                    mobileTv.setText(mobile);
                }
                break;
            case R.id.tv7:
                if (checkMobile(mobile)){
                    mobile = mobile+"7";
                    mobileTv.setText(mobile);
                }
                break;
            case R.id.tv8:
                if (checkMobile(mobile)){
                    mobile = mobile+"8";
                    mobileTv.setText(mobile);
                }
                break;
            case R.id.tv9:
                if (checkMobile(mobile)){
                    mobile = mobile+"9";
                    mobileTv.setText(mobile);
                }
                break;
            case R.id.tv_clear:
                mobile = "";
                mobileTv.setText(mobile);
                break;
            case R.id.tv_sure:
                if (mobile.length()==11){
                    mobile = "";
                    mobileTv.setText(mobile);
                    CommonUtils.jumpTo(FirstPageActivity.this,ChooseQualityActivity.class);
                }else {
                    ToastUtil.showToast("手机号码有误");
                }
                break;
        }
    }

    private boolean checkMobile(String mobile) {
        if (mobile.length()==11){
            ToastUtil.showToast("手机号码最多11位");
            return false;
        }
        return true;
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
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        {
                                            Bundle b = new Bundle();
                                            b.putString("code",str);
                                            CommonUtils.jumpTo(FirstPageActivity.this,ChooseQualityActivity.class,b);
                                        }
                                    }
                                });

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

}
