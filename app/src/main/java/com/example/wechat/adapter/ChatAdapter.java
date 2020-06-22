package com.example.wechat.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wechat.R;
import com.example.wechat.entity.Msg;

import java.util.List;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-30 20:12
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    List<Msg> msgList;
    Context context;

    public ChatAdapter(List<Msg> msgList) {
        this.msgList = msgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.msg_item, parent, false);
        ChatAdapter.ViewHolder viewHolder=new ChatAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Msg msg=msgList.get(position);
        //以下两句判断改用哪种聊天气泡，不用的气泡设为不可见
        if(msg.getType()==Msg.TYPE_RECEIVED){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
        }else if(msg.getType()==Msg.TYPE_SENT){
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftLayout =  itemView.findViewById(R.id.left_layout);
            rightLayout = itemView.findViewById(R.id.right_layout);
            leftMsg= itemView.findViewById(R.id.left_msg);
            rightMsg= itemView.findViewById(R.id.right_msg);
        }
    }

    // 添加数据
    public void addData(final Msg msg) {
        //   在list中添加数据，并通知条目加入一条
        msgList.add(msg);
        //添加动画
        notifyItemInserted(msgList.size()-1);
        notifyDataSetChanged();

    }


}
