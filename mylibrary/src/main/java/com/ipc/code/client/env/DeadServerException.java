package com.ipc.code.client.env;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * @author zk
 * @date 创建时间：2019/2/1
 * @Description：
 * @other 修改历史：
 */

public class DeadServerException extends RuntimeException {

    public DeadServerException() {
    }

    public DeadServerException(String message) {
        super(message);
    }

    public DeadServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeadServerException(Throwable cause) {
        super(cause);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public DeadServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
