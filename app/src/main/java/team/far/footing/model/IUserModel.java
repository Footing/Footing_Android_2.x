package team.far.footing.model;

import team.far.footing.model.callback.OnLoginListener;
import team.far.footing.model.callback.OnQueryFriendListener;
import team.far.footing.model.callback.OnUploadListener;
import team.far.footing.model.callback.OnUserListener;

/**
 * Created by moi on 15/10/2.
 */
public interface IUserModel {
    /**
     * 发起请求获取验证码
     *
     * @param phonenumber     -----手机号
     * @param onLoginListener -----获取与验证的监听器
     */
    void loginGetCode(String phonenumber, OnLoginListener onLoginListener);

    /**
     * 使用验证码验证
     *
     * @param phonenumber ----手机号
     * @param code        ----验证码
     */
    void loginVerifyCode(String phonenumber, String code);

    /**
     * 注册的监听器  --> 注册的gui包含在这里面，所以调用此方法就会弹出注册的gui
     *
     * @param onUserListener 注意：===============>>>>>>
     *                       在注册一个用户的时候，就给当前用户加了一个userInfo表和一个friends表
     */
    void register(OnUserListener onUserListener);

    /**
     * @param onUserListener 退出当前登录
     */
    void logout(OnUserListener onUserListener);

    /**
     * @param signatrue      ----------更新签名
     * @param onUserListener ---------更新用户数据的监听器
     */
    void updateUser_Signature(String signatrue, OnUserListener onUserListener);

    /**
     * @param level          ------------更新等级
     * @param onUserListener ------------更新用户数据的监听器
     */
    void updateUser_level(int level, OnUserListener onUserListener);

    /**
     * @param nickname       ------------昵称
     * @param onUserListener --------------更新用户数据的监听器
     */
    void update_NickName(String nickname, OnUserListener onUserListener);

    /**
     * @param PraiseCount    ----------点赞次数
     * @param onUserListener -----------更新用户数据的监听器
     */
    void update_PraiseCount(int PraiseCount, OnUserListener onUserListener);

    void update_distance(int today_distance, int all_distance, OnUserListener onUserListener);

    /**
     * @param today_distance ----------今日路程
     * @param onUserListener ---------用户更新的监听器
     */
    void update_today_distance(int today_distance, String date, OnUserListener onUserListener);

    /**
     * @param all_distance   --------总的距离
     * @param onUserListener --------用户更新的监听器
     */
    void update_all_distance(int all_distance, OnUserListener onUserListener);

    /**
     * @param update_is_finish_today ----------是否完成每日任务  0表示未完成  1表示完成
     * @param onUserListener         ------------------用户更新的监听器
     */
    void update_is_finish_today(int update_is_finish_today, OnUserListener onUserListener);

    /**
     * @param email          -----------用户注册时输入的邮箱
     * @param onUserListener ------------更新用户数据的监听器
     */
    void resetPasswordByEmail(String email, OnUserListener onUserListener);

    /**
     * @param nickname
     * @param signature
     * @param onUserListener
     */
    void updataUserInfo(String nickname, String signature, OnUserListener onUserListener);

    /**
     * @param filePath                     ------- 文件路径
     * @param onUploadHeadPortraitListener ----------文件上传监听
     */
    void uploadHeadPortrait(String filePath, OnUploadListener onUploadHeadPortraitListener);


}
