package team.far.footing2.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Luoyy on 2015/8/9 0009.
 */
public class Friends extends BmobObject {

    private BmobRelation friends;
    private UserInfo userInfo;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserInfo getUserbean() {
        return userInfo;
    }

    public void setUserbean(UserInfo userbean) {
        this.userInfo = userbean;
    }

    public BmobRelation getFriends() {
        return friends;
    }

    public void setFriends(BmobRelation friends) {
        this.friends = friends;
    }


}
