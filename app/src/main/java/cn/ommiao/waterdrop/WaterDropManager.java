package cn.ommiao.waterdrop;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;

import cn.ommiao.waterdrop.widget.WaterDropView;

public class WaterDropManager {

    private WindowManager mWindowManager;
    private static WaterDropManager mInstance;
    private Context mContext;

    private boolean isShow = false;
    private boolean animating = false;

    private View floatView;
    private WaterDropView waterDropView;

    private WindowManager.LayoutParams params;

    public static WaterDropManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new WaterDropManager(context);
        }
        return mInstance;
    }

    @SuppressLint("InflateParams")
    private WaterDropManager(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        floatView = LayoutInflater.from(mContext).inflate(R.layout.layout_water_drop, null);
        waterDropView = floatView.findViewById(R.id.water_drop);
        initWindowParams();
    }

    private void initWindowParams() {
        params = new WindowManager.LayoutParams();
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        params.x = 0;
        params.y = 0;
        //总是出现在应用程序窗口之上
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        //设置图片格式，效果为背景透明
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public void show() {
        if(animating){
            return;
        }
        try {
            mWindowManager.addView(floatView, params);
            isShow = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hide() {
        if(animating){
            return;
        }
        try {
            mWindowManager.removeView(floatView);
            isShow = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animateShow(){
        if(animating){
            return;
        }
        show();
        animating = true;
        waterDropView.setTranslationY(-125f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(waterDropView, "translationY", -125, 0);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(300);
        animator.addListener(new SimpleAnimatorListener(){
            @Override
            public void onAnimationEnd(Animator animation) {
                animating = false;
            }
        });
        animator.start();
    }

    public void animateHide(){
        if(animating){
            return;
        }
        animating = true;
        waterDropView.setTranslationY(0f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(waterDropView, "translationY", 0, -125);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(300);
        animator.addListener(new SimpleAnimatorListener(){
            @Override
            public void onAnimationEnd(Animator animation) {
                animating = false;
                hide();
            }
        });
        animator.start();
    }

    public boolean isShow(){
        return isShow;
    }

}
