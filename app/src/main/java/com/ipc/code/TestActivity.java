package com.ipc.code;

import com.cmprocess.ipc.VCore;
import com.cmprocess.ipc.callback.BaseCallback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * @author zk
 * @date 创建时间：2019/2/15
 * @Description：
 * @other 修改历史：
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IPayManager service = VCore.getCore().getService(IPayManager.class);
        final TextView textview = findViewById(R.id.tv);
        if (service != null){
            service.pay(5000, new BaseCallback() {

                @Override
                public void onSucceed(Bundle result) {
                    textview.setText(result.getString("pay"));
                }

                @Override
                public void onFailed(String reason) {

                }
            });
        }
    }
}
