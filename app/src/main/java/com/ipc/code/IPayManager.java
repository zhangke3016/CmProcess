package com.ipc.code;

import com.cmprocess.ipc.server.IPCCallback;

/**
 * @author zk
 * @date 创建时间：2019/2/12
 * @Description：
 * @other 修改历史：
 */
public interface IPayManager {

    String pay(int count);
    void pay(int count,IPCCallback callBack);
}
