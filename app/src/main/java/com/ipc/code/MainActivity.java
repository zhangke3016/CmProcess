package com.ipc.code;


import com.cmprocess.ipc.VCore;
import com.cmprocess.ipc.event.EventCallback;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements EventCallback {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VCore.getCore().registerService(IPayManager.class, new IPCPayManagerTest());
        IPayManager service = VCore.getCore().getLocalService(IPayManager.class);
        Log.e("TAG", "onCreate: " + (service == null));

        VCore.getCore().subscribe("key", this);
        startActivity(new Intent(this,TestActivity.class));
    }

    @Override
    public void onEventCallBack(Bundle event) {
        Log.e("TAG", "onEventCallBack: "+(Looper.myLooper() == Looper.getMainLooper()) );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VCore.getCore().unsubscribe(this);
    }
}
