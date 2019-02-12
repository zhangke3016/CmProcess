package com.ipc.code;


import com.test.common.pay.IPayManager;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        IPayManager service = VCore.getCore().getService(IPayManager.class);

        if (service != null){
            Log.d(TAG, "onCreate: "+service.pay(5000));
        }
    }


}
