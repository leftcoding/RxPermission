package com.start.permission.launcher;

import com.start.permission.Source;

public class AllLauncher implements Launcher {
    private final Source source;

    public AllLauncher(Source source) {
        this.source = source;
    }

    @Override
    public void start(int code) {
        PlatformLauncher platformLauncher = new PlatformLauncher(source);
    }
}
