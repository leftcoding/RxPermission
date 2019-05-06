package com.start.permission.source;

import android.content.Context;
import android.content.Intent;

import com.start.permission.Source;

public class ContextSource extends Source {
    private Context context;

    public ContextSource(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void startActivity(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        throw new UnsupportedOperationException("Unsupported operation.");
    }
}
