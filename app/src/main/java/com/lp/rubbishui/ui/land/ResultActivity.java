package com.lp.rubbishui.ui.land;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lp.rubbishui.R;

public class ResultActivity extends AppCompatActivity {

    private TextView jfTv,resultTv,backTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        jfTv = findViewById(R.id.tv_jf);
        resultTv = findViewById(R.id.tv_result);
        backTv = findViewById(R.id.tv_back);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
