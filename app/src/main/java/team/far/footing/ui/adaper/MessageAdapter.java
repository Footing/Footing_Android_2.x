package team.far.footing.ui.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import team.far.footing.R;
import team.far.footing.model.bean.realmbean.Message;
import team.far.footing.presenter.MessagePresenter;
import team.far.footing.uitl.LogUtils;

/**
 * Created by luoyy on 2015/11/9 0009.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageItemView> {


    private List<Message> list;
    private Context mContext;
    private MessagePresenter presenter;

    public MessageAdapter(List<Message> list, Context context,MessagePresenter presenter) {
        this.list = list;
        this.mContext = context;
        this.presenter = presenter;

    }

    @Override
    public MessageItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_item_layout, parent, false);
        return new MessageItemView(view);
    }

    @Override
    public void onBindViewHolder(MessageItemView holder, final int position) {
        final Message msg = list.get(position);
        holder.tv_Name.setText(msg.getSendUser());
        holder.tv_Content.setText(msg.getContent());
        if (msg.getType() == Message.SYSTEM) {
            //系统消息
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MaterialDialog dialog = new MaterialDialog(mContext);
                    dialog.setTitle(R.string.Add_friend);
                    dialog.setMessage(msg.getSendUser() + "希望添加您为好友");
                    dialog.setNegativeButton(R.string.Add_refuse, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    dialog.setPositiveButton(R.string.Add_agree, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenter.addFriend(msg.getSendUser());
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
            });

        } else {
            //聊天消息


        }
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
        @Bind(R.id.layout)
        RelativeLayout layout;

        public MessageItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
