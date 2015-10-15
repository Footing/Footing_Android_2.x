package team.far.footing2.model.callback;

/**
 * Created by moi on 15/10/2.
 */
public interface OnUserListener {

    void Success();

    void Failed(int i, String reason);
    void onProgress(int i, String s);

}
