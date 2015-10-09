package team.far.footing.model.callback;

import team.far.footing.model.bean.UserInfo;

/**
 * Created by luoyy on 2015/10/9 0009.
 */
public interface OnUserInfoListener {

    void Success(UserInfo userInfo);

    void Failed(int i, String reason);

}
