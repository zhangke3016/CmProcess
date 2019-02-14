package com.ipc.code;


import android.app.Application;
import android.content.Context;
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
        Log.d("App", "attachBaseContext: ");
        VCore.init(base);
        VCore.getCore().registerService(IPayManager.class, this);
    }

    @Override
    public String pay(int count) {
        return count + 100 + "";
    }
}
