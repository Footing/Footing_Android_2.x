package team.far.footing.ui.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import team.far.footing.model.bean.realmbean.Message;

/**
 * Created by luoyy on 2015/11/9 0009.
 */
public class MessageAdapter extends RecyclerView.Adapter {

    private List<Message> list;
    private Context mContext;

    public MessageAdapter(List<Message> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
