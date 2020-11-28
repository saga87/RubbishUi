package com.lp.rubbishui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class CommonUtils {


    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }



    public static void showSoftInput(View view, Activity activity) {
        ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE))
                .toggleSoftInput(0, InputMethodManager
                        .HIDE_NOT_ALWAYS);
    }

    public static void hideSoftInput(View view, Activity activity) {
        ((InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow
                        (activity.getCurrentFocus().getWindowToken(), InputMethodManager
                                .HIDE_NOT_ALWAYS);
    }

    public static void jumpTo(Context context, Class<?> c) {
        Intent intent = new Intent();
        intent.setClass(context, c);
        context.startActivity(intent);
    }

    public static void jumpTo(Context context, Class<?> c, Bundle bundle) {
        jumpTo(context, c, bundle, null);
    }

    public static void jumpTo(Context context, Class<?> c, Bundle bundle, Integer flags) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (flags != null) {
            intent.setFlags(flags);
        }
        intent.setClass(context, c);
        context.startActivity(intent);
    }


    public static boolean isSdcardExit() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


}
