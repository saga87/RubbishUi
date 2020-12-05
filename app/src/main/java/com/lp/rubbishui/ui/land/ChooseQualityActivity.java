package com.lp.rubbishui.ui.land;

import android.Manifest;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lp.rubbishui.R;
import com.lp.rubbishui.utils.CommonUtils;
import com.lp.rubbishui.utils.ImageUtils;
import com.lp.rubbishui.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import io.reactivex.functions.Consumer;

public class ChooseQualityActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tipTv;
    private TextView hgTv,ybTv,bhgTv;
    private TextView chooseTv;
    private TextView cxTv,sureTv;

    private LinearLayout chooseLL,chooseSureLL;

    private String code = "";


    private SurfaceHolder surfaceholder2;
    private SurfaceView surfaceView;
    private Camera camera2;
    private String path;

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

    private void takePic() {
        camera2.takePicture(new Camera.ShutterCallback() {
            @Override
            public void onShutter() {

            }
        }, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                if (bitmap != null) {
                    bitmap = ImageUtils.getRotatedBitmap(bitmap, 0);
                    File dir = new File(Environment.getExternalStorageDirectory() + "/landrub");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    path = Environment.getExternalStorageDirectory() + "/landrub/"
                            + System.currentTimeMillis() + ".jpg";
                    try {
                        FileOutputStream fout = new FileOutputStream(path);
                        BufferedOutputStream bos = new BufferedOutputStream(fout);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        bos.flush();
                        bos.close();
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

//                uploadPic();
                camera2.stopPreview();
                camera2.startPreview();
            }
        });
    }

    class surfaceholderCallbackFont1 implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // 获取camera对象
            int cameraCount = Camera.getNumberOfCameras();
            try {
                if (cameraCount == 2) {
                    camera2 = Camera.open(0);
                } else if (cameraCount == 3) {
                    camera2 = Camera.open(3);
                } else {
                    camera2 = Camera.open(0);
                }
                // 设置预览监听
                camera2.setPreviewDisplay(holder);
                Camera.Parameters parameters = camera2.getParameters();

                if (ChooseQualityActivity.this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    parameters.set("orientation", "portrait");
                    camera2.setDisplayOrientation(270);
                    parameters.setRotation(270);
                } else {
                    parameters.set("orientation", "landscape");
                    camera2.setDisplayOrientation(0);
                    parameters.setRotation(0);
                }
                camera2.setParameters(parameters);
                // 启动摄像头预览
                camera2.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
                camera2.release();
            } catch (RuntimeException e1) {
                e1.printStackTrace();
                ToastUtil.showToast("打开摄像头错误");
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            /*camera2.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {
                        parameters = camera2.getParameters();
                        parameters.setPictureFormat(PixelFormat.JPEG);
                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 1连续对焦
                        setDispaly(parameters, camera2);
                        camera2.setParameters(parameters);
                        camera2.startPreview();
                        camera2.cancelAutoFocus();// 2如果要实现连续的自动对焦，这一句必须加上
                        camera.cancelAutoFocus();// 只有加上了这一句，才会自动对焦。
                    }
                }
            });*/

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
//            Log.e("AAAAA", "=========surfaceDestroyed");
        }

        // 控制图像的正确显示方向
        private void setDispaly(Camera.Parameters parameters, Camera camera) {
            if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
                setDisplayOrientation(camera, 0);
            } else {
                parameters.setRotation(0);
            }

        }

        // 实现的图像的正确显示
        private void setDisplayOrientation(Camera camera, int i) {
            Method downPolymorphic;
            try {
                downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});
                if (downPolymorphic != null) {
                    downPolymorphic.invoke(camera, new Object[]{i});
                }
            } catch (Exception e) {
                ToastUtil.showToast("图像错误");
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (camera2 != null) {
            camera2.release();
        }
        super.onDestroy();
    }


    private void initView() {
        surfaceView = findViewById(R.id.surfaceview2);
        surfaceholder2 = surfaceView.getHolder();

        final RxPermissions rxPermissions = new RxPermissions(ChooseQualityActivity.this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    surfaceholder2.addCallback(new ChooseQualityActivity.surfaceholderCallbackFont1());
                } else {
                    ToastUtil.showToast("未授权，部分功能无法使用");
                }
            }
        });
        
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
                takePic();
                CommonUtils.jumpTo(ChooseQualityActivity.this,ChooseQualityActivity.class);
                finish();
                break;
        }
    }
}
