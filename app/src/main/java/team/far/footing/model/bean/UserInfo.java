package team.far.footing.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by luoyy on 2015/10/7 0007.
 */
public class UserInfo extends BmobObject {

    private Userbean userbean;

    public Userbean getUserbean() {
        return userbean;
    }

    public void setUserbean(Userbean userbean) {
        this.userbean = userbean;
    }
}
