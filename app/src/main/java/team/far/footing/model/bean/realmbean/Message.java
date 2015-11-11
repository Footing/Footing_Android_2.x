package team.far.footing.model.bean.realmbean;

import io.realm.RealmObject;

/**
 * Created by luoyy on 2015/11/9 0009.
 */
public class Message extends RealmObject {

    public final static int UNREAD = 0x0000;
    public final static int READ = 0x0001;

    private String sendUser;
    private String getUser;
    private String content;
    private long date;
    //默认
    private int isRead = UNREAD;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getGetUser() {
        return getUser;
    }

    public void setGetUser(String getUser) {
        this.getUser = getUser;
    }

    public String getSendUser() {

        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
