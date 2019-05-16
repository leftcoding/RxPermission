package com.start.permission.option;

import com.start.permission.launcher.Launcher;
import com.start.permission.request.Request;

public interface Option {
    Request runtime();

    Launcher launcher();
}
