package com.ipc.code;

import com.ipc.code.helper.ipcbus.IPCInvocationBridge;
import com.ipc.code.helper.ipcbus.ServerInterface;
import com.ipc.code.helper.ipcbus.TransformBinder;
import com.ipc.code.server.interfaces.IAccountManager;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Proxy;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public class TestActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.tv);
        textView.setText("test");

        Bundle bundle = getIntent().getBundleExtra("bundle");
        IBinder binder = BundleCompat.getBinder(bundle,"name");
        ServerInterface serverInterface = new ServerInterface(IAccountManager2.class);
        IAccountManager2 iAccountManager = (IAccountManager2) Proxy.newProxyInstance(IAccountManager2.class.getClassLoader(), new Class[]{IAccountManager2.class},
                new IPCInvocationBridge(serverInterface, binder));
        try {
            User name = iAccountManager.getUser();
            Log.e("TestActivity", "onCreate: "+name.toString() );
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
