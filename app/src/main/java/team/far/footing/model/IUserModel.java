package team.far.footing.model;

import team.far.footing.model.callback.OnLoginListener;
import team.far.footing.model.callback.OnUserListener;

/**
 * Created by moi on 15/10/2.
 */
public interface IUserModel {
    void loginGetCode(String phonenumber,OnLoginListener onLoginListener);

    void loginVerifyCode(String phonenumber,String code);

    void register(OnUserListener onUserListener);

    void logout(OnUserListener onUserListener);


}
