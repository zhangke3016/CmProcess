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
public class App extends Application implements IPayManager{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        VCore.init(base);
        VCore.getCore().registerService(IPayManager.class, this);
    }

    @Override
    public String pay(int count) {
        return count + 100 + "";
    }

    @Override
    public void pay(final int count, final IPCCallback callBack) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                Bundle bundle = new Bundle();
                bundle.putString("pay", count + 100 + "");
                try {
                    callBack.onSuccess(bundle);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
