package com.cmprocess.ipc.helper.ipcbus;

import android.os.IBinder;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public interface IServerCache {
    void join(String serverName, IBinder binder);
    void joinLocal(String serverName, Object object);
    IBinder removeService(String serverName);
    Object removeLocalService(String serverName);
    IBinder query(String serverName);
    Object queryLocal(String serverName);
}
