package team.far.footing.model;

import team.far.footing.model.bean.UserInfo;
import team.far.footing.model.callback.OnIsMyFriendListener;
import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.callback.OnUserListener;

/**
 * Created by Luoyy on 2015/8/9 0009.
 */
public interface IFriendModel {

    /**
     * @param nickname              ----------查询的用户的nickname
     * @param onQueryFriendListener -----------查询的监听器
     *                              <p/>
     *                              ## 按照nickname查找
     */
    void queryUserByName(String nickname, OnQueryFriendListener onQueryFriendListener);

    /**
     * @param id
     * @param onQueryFriendListener
     */

    void queryUserById(String id, OnQueryFriendListener onQueryFriendListener);


    /**
     * ##查找附近的人
     * ===还没写好先把接口放在这里
     */
    void queryUserByDistance(OnQueryFriendListener onQueryFriendListener);

    /**
     * ##查询所有注册用户
     *
     * @param onQueryFriendListener -----------查询的监听器
     */
    void queryAlluser(OnQueryFriendListener onQueryFriendListener);


    /**
     * ##查询当前用户已经添加了的好友 list
     *
     * @param onQueryFriendListener ------------查询的监听器
     */
    void getAllFriends(OnQueryFriendListener onQueryFriendListener);

    /**
     * 删除朋友
     *
     * @param userInfo
     * @param onUserListener
     */
    void deleteFriend(UserInfo userInfo, OnUserListener onUserListener);

    /**
     * 根据用户名判断
     *
     * @param username
     * @param onIsMyFriendListener
     */
    void isMyFriendByUsername(String username, OnIsMyFriendListener onIsMyFriendListener);

    /**
     * 根据昵称判断
     *
     * @param username
     * @param onIsMyFriendListener
     */
    void isMyFriendByNickname(String username, OnIsMyFriendListener onIsMyFriendListener);


    /**
     * @param userInfo
     * @param onUserListener 注意：这里是向对方发送请求加好友的消息
     */
    void addFriend(UserInfo userInfo, OnUserListener onUserListener);

    /**
     * @param userInfo
     * @param onUserListener 这里是用户同意对方加好友后的
     */

    void ensureAddFriend(UserInfo userInfo, OnUserListener onUserListener);

    /**
     * 拒绝加好友的请求  ---->>> 现在还是空方法
     */
    void refuseAddFriend();




}
