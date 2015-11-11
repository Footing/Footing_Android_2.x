package team.far.footing.ui.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import team.far.footing.R;
import team.far.footing.model.bean.realmbean.Message;
import team.far.footing.uitl.LogUtils;

/**
 * Created by luoyy on 2015/11/9 0009.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageItemView> {


    private List<Message> list;
    private Context mContext;

    public MessageAdapter(List<Message> list, Context context) {
        this.list = list;
        this.mContext = context;
        LogUtils.e(list.size() + "");
    }

    @Override
    public MessageItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_item_layout, parent, false);
        return new MessageItemView(view);
    }

    @Override
    public void onBindViewHolder(MessageItemView holder, int position) {
        //   holder.mImageView.setImageResource();
        holder.tv_Name.setText(list.get(position).getSendUser());
        holder.tv_Content.setText(list.get(position).getContent());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MessageItemView extends RecyclerView.ViewHolder {
        @Bind(R.id.message_img)
        ImageView mImageView;
        @Bind(R.id.message_tv_name)
        TextView tv_Name;
        @Bind(R.id.message_tv_content)
        TextView tv_Content;

        public MessageItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
