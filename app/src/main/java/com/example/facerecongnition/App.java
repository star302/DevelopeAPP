package com.example.facerecongnition;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

public class App extends Application {

    public static boolean openSkinMake = false;
    /**
     * 应用刚创建时，进行一些初始化
     */
    @Override
    public void onCreate() {
        super.onCreate();

        QMUISwipeBackActivityManager.init(this);
        // 初始化 OkGo
        initOkGo();
    }

    /**
     * 初始化 OkGo
     */
    void initOkGo() {
        OkGo.getInstance().init(this);
    }
}
