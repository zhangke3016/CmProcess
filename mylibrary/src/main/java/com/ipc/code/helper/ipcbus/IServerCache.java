package com.ipc.code.helper.ipcbus;

import android.os.IBinder;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public interface IServerCache {
    void join(String serverName, IBinder binder);
    IBinder query(String serverName);
}
