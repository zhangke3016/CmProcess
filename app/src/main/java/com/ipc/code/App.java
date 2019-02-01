package com.ipc.code;

import com.ipc.code.client.core.VirtualCore;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public class App extends Application{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d("App", "attachBaseContext: ");
        try {
            VirtualCore.get().startup(base);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
