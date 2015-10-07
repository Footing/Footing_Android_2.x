package team.far.footing.model.callback;

import team.far.footing.model.bean.Userbean;

/**
 * Created by moi on 15/10/2.
 */
public interface OnUserListener {

    void Success(Userbean userbean);

    void Failed(int i, String reason);

}
