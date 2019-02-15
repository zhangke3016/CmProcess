package com.cmprocess.ipc;


import com.cmprocess.ipc.helper.ipcbus.IPCBus;
import com.cmprocess.ipc.helper.ipcbus.IPCSingleton;

import android.support.v4.util.ArrayMap;


/**
 * @author zk
 * @date 创建时间：2019/2/11
 * @Description：
 * @other 修改历史：
 */
final class VManager {

    private static final VManager sVM = new VManager();

    private ArrayMap<Class,IPCSingleton> mIPCSingletonArrayMap = new ArrayMap<>();

    public static  VManager get() {
        return sVM;
    }

    public <T> T getService(Class<T> ipcClass) {
        T t = IPCBus.get(ipcClass);
        if (t != null){
            return t;
        }
        IPCSingleton<T> tipcSingleton = mIPCSingletonArrayMap.get(ipcClass);
        if (tipcSingleton == null){
            tipcSingleton = new IPCSingleton<>(ipcClass);
            mIPCSingletonArrayMap.put(ipcClass,tipcSingleton);
        }
        return tipcSingleton.get();
    }


}
