package com.start.face.rxpermission;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.start.permission.Permissions;
import com.start.permission.RequestCallback;
import com.start.permission.RequestExecutor;
import com.start.permission.Runnable;
import com.start.permission.RxPermission;
import com.start.permission.andpermission.RxAndPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.permissions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermission.getInstance()
                        .runtime(MainActivity.this)
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

                                RxPermission.getInstance()
                                        .runtime(MainActivity.this)
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
//                                                                executor.cancel();
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
                                                showSettingDialog(MainActivity.this, list);
                                            }
                                        })
                                        .start();
                            }
                        })
                        .start();
            }
        });
    }

    /**
     * Display setting dialog.
     */
    public void showSettingDialog(Context context, final List<String> permissions) {
        String message = TextUtils.join("-", permissions);

        new AlertDialog.Builder(context).setCancelable(false)
                .setTitle("设置")
                .setMessage(message)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
