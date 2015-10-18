package team.far.footing.model.callback;

/**
 * Created by luoyy on 2015/10/8 0008.
 */
public interface OnLoginListener {
    /**
     * @param event  ---SMSSDK.RESULT_COMPLETE  ----->>> 回调完成
     *               其它 ----->>> 验证失败
     * @param result ---SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE ----->>>提交验证码成功
     *               ---SMSSDK.EVENT_GET_VERIFICATION_CODE  ----->>>获取验证码成功
     *               ---SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES  ---->>>返回支持发送验证码的国家列表
     *               <p/>
     *               <p/>
     *               注意：此回调中的为子线程中的,操作ui 请用handler
     *               <p/>
     *               <p/>
     *               eg:
     *               if (result == SMSSDK.RESULT_COMPLETE) {
     *               //回调完成
     *               if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
     *               //提交验证码成功
     *               onLoginListener.success(event,SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE);
     *               } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
     *               //获取验证码成功
     *               onLoginListener.
     *               } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
     *               //返回支持发送验证码的国家列表
     *               }
     *               } else {
     *               ((Throwable) data).printStackTrace();
     *               }
     */
    void onSuccess(int event, int result);
    void onProgress(int progress, String status);
    void onError(int code, String message);
}
