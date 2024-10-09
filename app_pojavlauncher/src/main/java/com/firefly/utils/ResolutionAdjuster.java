package com.firefly.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.util.Log;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.SurfaceHolder;

import net.kdt.pojavlaunch.MinecraftGLSurface.mSurface;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import net.kdt.pojavlaunch.Tools;
import org.lwjgl.glfw.CallbackBridge;

public class ResolutionAdjuster {

    private float mScaleFactor = 1.0f; // 默认比例
    private final Context context;

    // 构造函数，传入Context
    public ResolutionAdjuster(Context context) {
        this.context = context;
    }

    // 显示滑动条弹窗
    public void showSeekBarDialog() {
        // 动态创建一个LinearLayout作为容器
        // 什么?为什么不用.xml来构建?
        // 因为麻烦
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 40);
        layout.setGravity(Gravity.CENTER);

        // 动态创建一个TextView,用于显示缩放因子
        final TextView scaleTextView = new TextView(context);
        scaleTextView.setText("Scale Factor: " + LauncherPreferences.PREF_SCALE_FACTOR / 100f);
        scaleTextView.setTextSize(18);
        layout.addView(scaleTextView);

        // 动态创建一个SeekBar,用于调整缩放因子
        final SeekBar scaleSeekBar = new SeekBar(context);
        scaleSeekBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // 设置滑动条的最大值和初始进度
        int maxScaleFactor = Math.max(LauncherPreferences.PREF_SCALE_FACTOR, 100);
        scaleSeekBar.setMax(maxScaleFactor - 25);
        scaleSeekBar.setProgress((int) (LauncherPreferences.PREF_SCALE_FACTOR - 25)); // 初始进度
        layout.addView(scaleSeekBar);

        // 设置滑动条监听器
        scaleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 更新缩放因子
                mScaleFactor = (progress + 25) / 100f;

                // 实时更新显示的缩放因子
                scaleTextView.setText("Scale Factor: " + mScaleFactor);

                // 新分辨率
                refreshSize();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Nothing to do here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Nothing to do here
            }
        });

        // 创建并显示弹窗
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Adjust Resolution Scale");
        builder.setView(layout);
        builder.setCancelable(false); // 不允许点击外部关闭弹窗,防止进程错误
        // 设置确认按钮，点击关闭弹窗
        builder.setPositiveButton("Close", (d, i) -> d.dismiss());
        builder.show();
    }

    // 刷新窗口
    private void refreshSize() {
        int windowWidth = Tools.getDisplayFriendlyRes(Tools.currentDisplayMetrics.widthPixels, mScaleFactor);
        int windowHeight = Tools.getDisplayFriendlyRes(Tools.currentDisplayMetrics.heightPixels, mScaleFactor);
        if (mSurface == null) {
            Log.w("MGLSurface", "Attempt to refresh size on null surface");
            return;
        }
        if (LauncherPreferences.PREF_USE_ALTERNATE_SURFACE) {
            SurfaceView view = (SurfaceView) mSurface;
            if (view.getHolder() != null) {
                view.getHolder().setFixedSize(windowWidth, windowHeight);
            }
        } else {
            TextureView view = (TextureView) mSurface;
            if (view.getSurfaceTexture() != null) {
                view.getSurfaceTexture().setDefaultBufferSize(windowWidth, windowHeight);
            }
        }

        CallbackBridge.sendUpdateWindowSize(windowWidth, windowHeight);

    }
}