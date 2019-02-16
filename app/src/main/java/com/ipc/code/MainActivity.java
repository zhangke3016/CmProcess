package com.ipc.code;


import com.cmprocess.ipc.VCore;
import com.cmprocess.ipc.event.EventCallback;
import com.cmprocess.ipc.server.IPCCallback;

import android.content.Intent;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements IPayManager{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VCore.getCore().registerService(IPayManager.class, this);
        IPayManager service = VCore.getCore().getLocalService(IPayManager.class);
        Log.e("TAG", "onCreate: " + (service == null));

        VCore.getCore().subscribe("key", new EventCallback() {
            @Override
            public void onEventCallBack(Bundle event) {
            Log.e("TAG", "onEventCallBack: "+(Looper.myLooper() == Looper.getMainLooper()) );
            }
        });
        startActivity(new Intent(this,TestActivity.class));
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
