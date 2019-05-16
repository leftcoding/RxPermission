package com.start.face.rxpermission;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.start.permission.constant.Permissions;
import com.start.permission.request.RequestCallback;
import com.start.permission.request.RequestExecutor;
import com.start.permission.andpermission.Runnable;
import com.start.permission.RxPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPermissions();
            }
        });
    }

    public void initPermissions() {
        RxPermission.with(MainActivity.this)
                .runtime()
                .checkPermission(Permissions.CAMERA)
                .rationale(new Runnable() {
                    @Override
                    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
                        new AlertDialog.Builder(MainActivity.this).setCancelable(false)
                                .setTitle("提示")
                                .setMessage(TextUtils.join("-", permissions))
                                .setPositiveButton("授权", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        executor.execute();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                                executor.cancel();
                                    }
                                })
                                .show();
                    }
                })
                .setCallback(new RequestCallback() {
                    @Override
                    public void onGranted(List<String> list) {

                    }

                    @Override
                    public void onDenied(List<String> list) {
                        if (RxPermission.hasAlwaysDeniedPermission(MainActivity.this, list)) {
                            showSettingDialog(MainActivity.this, list);
                            return;
                        }
                    }
                })
                .start();
    }

    /**
     * 打开系统权限设置界面
     */
    public void showSettingDialog(final Context context, final List<String> permissions) {
        String message = TextUtils.join("-", permissions);

        new AlertDialog.Builder(context).setCancelable(false)
                .setTitle("设置")
                .setMessage(message)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RxPermission.with(MainActivity.this).launcher().start(100);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Toast.makeText(MainActivity.this, "设置返回", Toast.LENGTH_SHORT).show();
        }
    }
}
