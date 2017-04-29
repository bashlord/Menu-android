package com.jjkbashlord.menu;

import android.app.Application;

/**
 * Created by JJK on 4/20/17.
 */

public class AppDelegate  extends Application {

    private String mGlobalVarValue;

    public String getGlobalVarValue() {
        return mGlobalVarValue;
    }

    public void setGlobalVarValue(String str) {
        mGlobalVarValue = str;
    }
}
