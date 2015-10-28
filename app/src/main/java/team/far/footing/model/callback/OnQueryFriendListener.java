package team.far.footing.model.callback;

import java.util.List;

import team.far.footing.model.bean.UserInfo;

/**
 * Created by Luoyy on 2015/8/9 0009.
 */
public interface OnQueryFriendListener {
    /**
     * @param object ---------查询返回的一个关于BmobUser的list
     */
    void onSuccess(List<UserInfo> object);

    void onError(int code, String msg);
}
