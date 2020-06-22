package com.example.wechat.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wechat.R;
import com.example.wechat.entity.Msg;
import com.example.wechat.entity.UserEntity;

import java.util.ArrayList;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-29 20:34
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ArrayList<UserEntity> list;
    Context context;
    private OnItemClickListener listener;

    public UserAdapter(ArrayList<UserEntity> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final UserEntity userEntity=list.get(position);
        holder.name.setText(userEntity.getName());
        holder.info_coming.setVisibility(userEntity.isComing()?View.VISIBLE:View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.touch(userEntity);
                userEntity.setComing(false);
                holder.info_coming.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView info_coming;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            info_coming=itemView.findViewById(R.id.info_coming);

        }
    }

    public interface OnItemClickListener{
        void touch(UserEntity userEntity);
    }
    public void setListener(OnItemClickListener listener) {

        this.listener = listener;
    }

    //添加用户
    public void addData(UserEntity user) {
        //   在list中添加数据，并通知条目加入一条
        list.add(user);
        //添加动画
        notifyItemInserted(list.size()-1);
        notifyDataSetChanged();
    }

    // 删除数据
    public void removeData(String port) {
        UserEntity userEntity=null;
        int i=0;
        for(;i<list.size();i++){
            userEntity=list.get(i);
            if(userEntity.getPort().equals(port)){
                list.remove(i);
                break;
            }
        }
        //删除动画
        notifyItemRemoved(i);
        notifyDataSetChanged();
    }

    public void infoComing(String from){
        UserEntity userEntity;
        for (int i=0;i<list.size();i++){
            userEntity=list.get(i);
            if(userEntity.getPort().equals(from)){
                userEntity.setComing(true);
                break;
            }
        }
        notifyDataSetChanged();

    }
}
