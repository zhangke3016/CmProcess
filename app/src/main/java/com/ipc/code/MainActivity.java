package com.ipc.code;

import com.ipc.code.client.ipc.VAccountManager;
import com.ipc.code.client.ipc.VPhoneManager;
import com.ipc.code.helper.ipcbus.ServerInterface;
import com.ipc.code.helper.ipcbus.TransformBinder;
import com.ipc.code.server.IServiceFetcher;
import com.ipc.code.server.accounts.VAccountManagerService;
import com.ipc.code.server.interfaces.IAccountManager;

import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements IAccountManager2{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String pwd = VAccountManager.get().getPassword("你好");
        String phone = VPhoneManager.get().getPhone();


        Log.d(TAG, "onCreate: "+phone);

//        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TransformBinder transformBinder = new TransformBinder(new ServerInterface(IAccountManager2.class),MainActivity.this);
//                Intent intent = new Intent(MainActivity.this,TestActivity.class);
//                Bundle bundle = new Bundle();
//                BundleCompat.putBinder(bundle,"name", transformBinder);
//                intent.putExtra("bundle", bundle);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public User getUser() throws RemoteException {
        User user = new User();
        user.setName("张三");
        return user;
    }
}
