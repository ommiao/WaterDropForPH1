package cn.ommiao.waterdrop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;

import cn.ommiao.waterdrop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements CustomDialogFragment.OnClickActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ImmersionBar.with(this).init();

        checkFloatingPermission();

        mBinding.btnShow.setOnClickListener(v -> WaterDropManager.getInstance(this).show());
        mBinding.btnHide.setOnClickListener(v -> WaterDropManager.getInstance(this).hide());

        mBinding.btnBigSpace.setOnClickListener(v -> {
            if(WaterDropManager.getInstance(this).isShow()){
                WaterDropManager.getInstance(this).bigMiddleSpace();
                mBinding.tvSpace.setText(String.valueOf(WaterDropManager.getInstance(this).getSpace()));
            }
        });

        mBinding.btnSmallSpace.setOnClickListener(v -> {
            if(WaterDropManager.getInstance(this).isShow()){
                WaterDropManager.getInstance(this).smallMiddleSpace();
                mBinding.tvSpace.setText(String.valueOf(WaterDropManager.getInstance(this).getSpace()));
            }
        });
    }

    private void checkFloatingPermission() {
        if(!Settings.canDrawOverlays(this)){
            CustomDialogFragment customDialogFragment = new CustomDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("content", getString(R.string.permission_tips));
            customDialogFragment.setArguments(bundle);
            customDialogFragment.setOnClickActionListener(this);
            customDialogFragment.show(getSupportFragmentManager(), CustomDialogFragment.class.getSimpleName());
        }
    }

    @Override
    public void onLeftClick() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        startActivityForResult(intent, 269);
    }

    @Override
    public void onRightClick() {
        noPermissionTipsAndQuit();
    }

    private void noPermissionTipsAndQuit() {
        CustomDialogFragment customDialogFragment = new CustomDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("content", getString(R.string.no_permission_tips));
        bundle.putBoolean("justConfirm", true);
        customDialogFragment.setArguments(bundle);
        customDialogFragment.setOnClickActionListener(new CustomDialogFragment.OnClickActionListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                finish();
            }
        });
        customDialogFragment.show(getSupportFragmentManager(), CustomDialogFragment.class.getSimpleName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(!Settings.canDrawOverlays(this)){
            noPermissionTipsAndQuit();
        }
    }


}
