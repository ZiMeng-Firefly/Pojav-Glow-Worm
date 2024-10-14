package com.firefly.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.kdt.pojavlaunch.MinecraftGLSurface;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import net.kdt.pojavlaunch.R;

public class ResolutionAdjuster {

    private float mScaleFactor;
    private final Context context;
    private final OnResolutionChangeListener listener;
    private MinecraftGLSurface glSurface;

    public ResolutionAdjuster(Context context, MinecraftGLSurface glSurface, OnResolutionChangeListener listener) {
        this.context = context;
        this.glSurface = glSurface;
        this.listener = listener;
    }

    // 显示滑动条弹窗
    public void showSeekBarDialog() {
        if (glSurface == null) {
            glSurface = new MinecraftGLSurface(context);
        }
        mScaleFactor = glSurface.mScaleFactor;
        int percentage = Math.round(mScaleFactor * 100);

        // 动态创建一个LinearLayout
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);  // 设置水平排列
        layout.setPadding(50, 40, 50, 40);
        layout.setGravity(Gravity.CENTER);

        // 动态创建一个 SeekBar ,用于调整缩放因子
        final SeekBar scaleSeekBar = new SeekBar(context);
        scaleSeekBar.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)); // 设置权重1, 充满剩余空间
        // 获取当前设置的最大缩放因子,并设置为滑动条的最大值
        int maxScaleFactor = Math.max(LauncherPreferences.PREF_SCALE_FACTOR, 100);
        scaleSeekBar.setMax(maxScaleFactor - 25);
        // 根据当前获取的缩放因子,设置滑动条初始值
        scaleSeekBar.setProgress((int) (mScaleFactor * 100) - 25);
        layout.addView(scaleSeekBar);

        // 动态创建一个TextView,用于显示当前分辨率
        final TextView resolutionTextView = new TextView(context);
        resolutionTextView.setText("16x9");  // 获取当前分辨率
        resolutionTextView.setTextSize(14);
        resolutionTextView.setPadding(10, 0, 0, 0);  // 添加一些左侧间距
        layout.addView(resolutionTextView);

        // 动态创建一个TextView,用于显示缩放百分数
        final TextView scaleTextView = new TextView(context);
        scaleTextView.setText(percentage + "%");
        scaleTextView.setTextSize(14);
        scaleTextView.setPadding(10, 0, 0, 0);  // 添加一些左侧间距
        layout.addView(scaleTextView);

        // 设置滑动条监听器
        scaleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 更新缩放因子
                mScaleFactor = (progress + 25) / 100f;
                listener.onChange(mScaleFactor);
                // 将缩放因子转换为整数
                int scaleFactor = Math.round(mScaleFactor * 100);
                // 动态更新显示的缩放百分数
                scaleTextView.setText(scaleFactor + "%");

                // 动态更新分辨率TextView,根据缩放因子调整分辨率显示
                // resolutionTextView.setText("动态分辨率值");
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
        builder.setTitle(context.getString(R.string.mcl_setting_title_resolution_scaler));
        builder.setView(layout);
        builder.setCancelable(false); // 不允许点击外部关闭弹窗,防止进程错误
        // 设置确认按钮, 点击关闭弹窗
        builder.setPositiveButton(android.R.string.ok, (d, i) -> d.dismiss());
        builder.show();
    }

    public interface OnResolutionChangeListener {
        void onChange(float value);
    }
}