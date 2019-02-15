package com.ipc.code;


import com.cmprocess.ipc.VCore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IPayManager service = VCore.getCore().getLocalService(IPayManager.class);
        Log.e("TAG", "onCreate: " + (service == null));

        startActivity(new Intent(this,TestActivity.class));
    }

}
