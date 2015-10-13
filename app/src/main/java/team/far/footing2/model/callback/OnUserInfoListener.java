package team.far.footing2.model.callback;

import android.accounts.NetworkErrorException;

import team.far.footing2.model.bean.UserInfo;

/**
 * Created by luoyy on 2015/10/9 0009.
 */
public interface OnUserInfoListener {

    void Success(UserInfo userInfo);

    void Failed(int i, String reason) throws NetworkErrorException;

}
