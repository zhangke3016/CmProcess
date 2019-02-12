package com.test.plugin2;

import com.test.common.pay.IPayManager;

/**
 * @author zk
 * @date 创建时间：2019/2/12
 * @Description：
 * @other 修改历史：
 */
public class PayManager implements IPayManager {

    @Override
    public String pay(int count) {
        return count+2+" 00000";
    }
}
