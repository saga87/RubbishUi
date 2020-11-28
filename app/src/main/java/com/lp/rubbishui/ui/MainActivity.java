package com.lp.rubbishui.ui;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lp.rubbishui.R;
import com.lp.rubbishui.utils.Vbar;
import com.sun.jna.ptr.IntByReference;

public class MainActivity extends AppCompatActivity {

    boolean state = false;
    boolean devicestate = false;
    IntByReference device;
    Vbar b = new Vbar();

    Button connectBtn;
    TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectBtn = findViewById(R.id.btn_connect);
        resultTv = findViewById(R.id.tv_result);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect();
            }
        });
    }

    private void connect(){
        state = b.vbarOpen();
        if (state) {
            AlertDialog.Builder builders  = new AlertDialog.Builder(MainActivity.this);
            builders.setTitle("设备连接状态" ) ;
            builders.setMessage("连接成功" ) ;
            builders.setPositiveButton("确认" , null );
            builders.show();

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
                                        resultTv.setText("扫码结果: "+str);
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
            AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("设备连接状态" ) ;
            builder.setMessage("连接失败" ) ;
            builder.setPositiveButton("确认" , null );
            builder.show();
            devicestate = false;
        }
    }
}
