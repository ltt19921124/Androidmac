package com.u91porn.ui.user;

/**
 * @author flymegoc
 * @date 2017/12/10
 */

public interface IUser extends IBaseUser {
    void register(String username, String password1, String password2, String email, String captchaInput);
}
