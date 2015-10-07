package team.far.footing.model;

import team.far.footing.model.callback.OnUserListener;

/**
 * Created by moi on 15/10/2.
 */
public interface IUserModel {
    void login(OnUserListener onUserListener);

    void register(OnUserListener onUserListener);

    void logout(OnUserListener onUserListener);


}
