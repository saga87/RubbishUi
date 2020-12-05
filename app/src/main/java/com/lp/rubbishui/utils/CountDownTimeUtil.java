package com.lp.rubbishui.utils;

import android.os.CountDownTimer;
import android.widget.TextView;


import com.lp.rubbishui.event.BackEvent;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

public class CountDownTimeUtil {

    private WeakReference<TextView> tvCodeWr;//控件软引用，防止内存泄漏
    private CountDownTimer timer;

    private boolean isTicking = false;

    public void setFinishListener(FinishListener finishListener) {
        this.finishListener = finishListener;
    }

    private FinishListener finishListener;


    public CountDownTimeUtil(TextView tvCode) {
        super();
        this.tvCodeWr = new WeakReference(tvCode);
    }

    public void runTimer() {
        timer = new CountDownTimer(15 * 1000 - 1, 1000) {
            @Override
            public void onFinish() {
                if (tvCodeWr.get() != null) {
                    tvCodeWr.get().setClickable(true);
                    tvCodeWr.get().setEnabled(true);
                }
                isTicking = false;
                if(finishListener != null){
                    finishListener.onFinish();
                }
                cancel();
                EventBus.getDefault().post(new BackEvent());
            }

            @Override
            public void onTick(long millisUntilFinished) {
                if (tvCodeWr.get() != null) {
                    isTicking = true;
                    tvCodeWr.get().setClickable(false);
                    tvCodeWr.get().setEnabled(false);
                    tvCodeWr.get().setText("回到首页("+millisUntilFinished / 1000+"s)");
                }
            }
        }.start();
    }

    public void cancel() {
        if (timer != null) {
            timer.onFinish();
            timer.cancel();
            timer = null;
        }
    }

    public boolean isTicking() {
        return isTicking;
    }

    public interface FinishListener{
        void onFinish();
    }
}