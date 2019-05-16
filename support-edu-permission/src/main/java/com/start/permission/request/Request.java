package com.start.permission.request;

import com.start.permission.andpermission.Runnable;

public interface Request {
    Request checkPermission(String... permission);

    Request setCallback(RequestCallback callback);

    Request rationale(Runnable runnable);

    void start();
}
