package team.far.footing2.model.callback;

public interface OnIsMyFriendListener {
    /**
     * @param b 查询成功  有返回true  没有就返回false
     */
    void onSuccess(boolean b);

    /**
     * 查询失败
     *
     * @param code
     * @param msg
     */
    void onError(int code, String msg);
}