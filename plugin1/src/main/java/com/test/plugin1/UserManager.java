package com.test.plugin1;

import com.test.common.user.IUserManager;

/**
 * @author zk
 * @date 创建时间：2019/2/12
 * @Description：
 * @other 修改历史：
 */
public class UserManager implements IUserManager {

    private UserManager(){}

    public static final UserManager INSTANCE = new UserManager();

    @Override
    public String getAccount() {
        return "123456789";
    }
}
