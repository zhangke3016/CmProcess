package com.ipc.code;


import com.cmprocess.ipc.VCore;
import com.cmprocess.ipc.callback.BaseCallback;
import com.cmprocess.ipc.server.IPCCallback;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;



/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public class App extends Application {

    static App mApp;

    public static App getApp() {
        return mApp;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mApp = this;
        VCore.init(base);

    }


}
