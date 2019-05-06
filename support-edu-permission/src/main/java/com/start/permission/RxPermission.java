package com.start.permission;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.Fragment;

import com.start.permission.source.ActivitySource;
import com.start.permission.source.ContextSource;
import com.start.permission.source.FragmentSource;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

public class RxPermission {
    public static final int RESULT_CODE = 100;

    private RxPermission() {

    }

    public Option with(Context context) {
        return new BootOption(getSource(context));
    }

    public Option with(Activity activity) {
        return new BootOption(new ActivitySource(activity));
    }

    public Option with(Fragment fragment) {
        return new BootOption(new FragmentSource(fragment));
    }

    /**
     * 判断权限是否被永久禁止
     *
     * @param context     上下文
     * @param permissions 权限
     * @return true 被永久禁止，false 相反
     */
    public static boolean hasAlwaysDeniedPermission(Context context, List<String> permissions) {
        return AndPermission.hasAlwaysDeniedPermission(context, permissions);
    }

    /**
     * 启动设置页
     */
    public static void startSetting(Fragment fragment) {
        AndPermission.with(fragment).runtime().setting().start(RESULT_CODE);
    }

    /**
     * 启动设置页
     */
    public static void startSetting(Activity activity) {
        AndPermission.with(activity).runtime().setting().start(RESULT_CODE);
    }

    public Source getSource(Context context) {
        if (context instanceof Activity) {
            return new ActivitySource((Activity) context);
        } else if (context instanceof ContextWrapper) {
            return getSource(((ContextWrapper) context).getBaseContext());
        }
        return new ContextSource(context);
    }
}
