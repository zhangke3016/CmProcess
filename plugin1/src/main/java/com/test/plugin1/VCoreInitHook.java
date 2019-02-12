package com.test.plugin1;

import com.ipc.code.VCore;
import com.test.common.user.IUserManager;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import android.util.Log;

/**
 * @author zk
 * @date 创建时间：2019/1/28
 * @Description：
 * @other 修改历史：
 */
@Aspect
public class VCoreInitHook {

    @After("execution(* com.ipc.code.VCore.init(..))")
    public void initAfter(JoinPoint joinPoint) {
        String s = joinPoint.getSignature().toString();
        Log.e("TAG", "initAfter: "+s );
        VCore.getCore().addService(IUserManager.class, UserManager.INSTANCE);
    }
}
