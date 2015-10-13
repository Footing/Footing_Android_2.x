package team.far.footing2.model.callback;

/**
 * Created by luoyy on 2015/10/7 0007.
 */
public interface OnSMSListener {
    void success( String country, String phone);

    void failed();
}
