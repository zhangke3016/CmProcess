package com.ipc.code;

import com.cmprocess.ipc.VCore;
import com.cmprocess.ipc.callback.BaseCallback;
import com.cmprocess.ipc.event.EventCallback;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * @author zk
 * @date 创建时间：2019/2/15
 * @Description：
 * @other 修改历史：
 */
public class TestActivity extends AppCompatActivity implements EventCallback {

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
                    final Bundle bundle = new Bundle();
                    bundle.putString("name", "DoDo");
                    VCore.getCore().post("key", bundle);
                }

                @Override
                public void onFailed(String reason) {

                }
            });
        }

        VCore.getCore().subscribe("key", this);
    }

    @Override
    public void onEventCallBack(Bundle event) {
        String name = event.getString("name");
        Log.e("TAG", "onEventCallBack: " + name + " " + (Looper.myLooper() == Looper.getMainLooper()));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        VCore.getCore().unsubscribe(this);
    }
}
