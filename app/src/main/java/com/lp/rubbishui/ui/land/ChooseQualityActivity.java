package com.lp.rubbishui.ui.land;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lp.rubbishui.R;
import com.lp.rubbishui.utils.CommonUtils;

public class ChooseQualityActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tipTv;
    private TextView hgTv,ybTv,bhgTv;
    private TextView chooseTv;
    private TextView cxTv,sureTv;

    private LinearLayout chooseLL,chooseSureLL;

    private String code = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_quality);
        Bundle b = getIntent().getExtras();
        initView();
        if (b!=null){
            code = b.getString("code");
            tipTv.setText("你好：  "+code);
        }
    }

    private void initView() {
        tipTv = findViewById(R.id.tv_tip);
        hgTv = findViewById(R.id.tv_hg);
        ybTv = findViewById(R.id.tv_yb);
        bhgTv = findViewById(R.id.tv_bhg);

        sureTv = findViewById(R.id.tv_sure);
        cxTv = findViewById(R.id.tv_cx);

        chooseTv = findViewById(R.id.tv_choose);
        chooseLL = findViewById(R.id.ll_choose);
        chooseSureLL = findViewById(R.id.ll_choose_sure);

        hgTv.setOnClickListener(this);
        ybTv.setOnClickListener(this);
        bhgTv.setOnClickListener(this);

        sureTv.setOnClickListener(this);
        cxTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_hg:
                chooseTv.setText("你选择了 合格 ，请确认操作");
                chooseLL.setVisibility(View.GONE);
                chooseSureLL.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_yb:
                chooseTv.setText("你选择了 一般 ，请确认操作");
                chooseLL.setVisibility(View.GONE);
                chooseSureLL.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_bhg:
                chooseTv.setText("你选择了 不合格 ，请确认操作");
                chooseLL.setVisibility(View.GONE);
                chooseSureLL.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_cx:
                chooseTv.setText("请判断垃圾分类是否合格");
                chooseLL.setVisibility(View.VISIBLE);
                chooseSureLL.setVisibility(View.GONE);
                break;
            case R.id.tv_sure:
                CommonUtils.jumpTo(ChooseQualityActivity.this,ResultActivity.class);
                finish();
                break;
        }
    }
}
