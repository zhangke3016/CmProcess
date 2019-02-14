package com.ipc.code;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        VCore.getCore().unregisterService(IPayManager.class);

        IPayManager service = VCore.getCore().getService(IPayManager.class);
        TextView textview = findViewById(R.id.tv);
        textview.setText(service.pay(5000));
    }


}
