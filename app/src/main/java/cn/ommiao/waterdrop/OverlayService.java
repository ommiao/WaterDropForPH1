package cn.ommiao.waterdrop;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Surface;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

public class OverlayService extends AccessibilityService {

    private ScreenRotationReceiver screenRotationReceiver;

    @Override
    public void onCreate() {
        screenRotationReceiver = new ScreenRotationReceiver();
        IntentFilter filterS = new IntentFilter();
        filterS.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
        registerReceiver(screenRotationReceiver, filterS);
    }

    @Override
    protected void onServiceConnected() {
        WaterDropManager.getInstance(OverlayService.this, true).animateShow();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(screenRotationReceiver);
    }

    private class ScreenRotationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int rotation = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
            if(rotation == Surface.ROTATION_0){
                WaterDropManager.getInstance(OverlayService.this, false).show();
            } else {
                WaterDropManager.getInstance(OverlayService.this, false).hide();
            }
        }
    }
}
