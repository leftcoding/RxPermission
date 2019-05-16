package com.start.permission.andpermission;

import android.content.Context;

import com.start.permission.request.RequestExecutor;

import java.util.List;

public interface Runnable {
    void showRationale(Context context, List<String> permissions, final RequestExecutor executor);
}
