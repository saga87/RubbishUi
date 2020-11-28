package com.lp.rubbishui.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lp.rubbishui.R;
import com.lp.rubbishui.event.SettingFinishEvent;

import org.greenrobot.eventbus.EventBus;

public class SettingSuccessActivity extends AppCompatActivity {

    private TextView quitAdminTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_success);
        quitAdminTv = findViewById(R.id.tv_quit_admin);
        quitAdminTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new SettingFinishEvent());
                SettingSuccessActivity.this.finish();
            }
        });
    }
}
