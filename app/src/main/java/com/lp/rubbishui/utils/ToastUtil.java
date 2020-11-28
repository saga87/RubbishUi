package com.lp.rubbishui.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lp.rubbishui.R;
import com.lp.rubbishui.application.MyApp;


public class ToastUtil {

    private static Toast toast = null;

    public static void showToast(String text) {
        if (toast == null) {
            View view = LayoutInflater.from(MyApp.app()).inflate(R.layout.global_view_toast, null);
            TextView toastTv = view.findViewById(R.id.tv_toast);
            toastTv.setText(text);
            toast = new Toast(MyApp.app());
            toast.setDuration(Toast.LENGTH_SHORT);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
            toast.setView(view); //添加视图文件
        } else {
            ((TextView)toast.getView().findViewById(R.id.tv_toast)).setText(text);
        }
        toast.show();
    }
}
