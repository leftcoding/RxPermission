package com.start.permission.request;

import android.content.Context;
import android.support.annotation.NonNull;

import com.start.permission.andpermission.Runnable;
import com.start.permission.andpermission.RuntimeRationale;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

public class AndRequest implements Request {
    private final Context context;
    private String[] permissions;
    private RequestCallback callback;
    private Runnable runnable;

    public AndRequest(Context context) {
        this.context = context;
    }

    @Override
    public Request checkPermission(String... permissions) {
        this.permissions = permissions;
        return this;
    }

    @Override
    public Request setCallback(RequestCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public Request rationale(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    @Override
    public void start() {
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale(runnable))
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (callback != null) {
                            callback.onGranted(permissions);
                        }
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (callback != null) {
                            callback.onDenied(permissions);
                        }
                    }
                })
                .start();
    }
}
