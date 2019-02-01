package com.ipc.code;

import android.os.RemoteException;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */
public interface IAccountManager2 {

    User getUser() throws RemoteException;
}
