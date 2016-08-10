package com.suggee.edustudent.modulers.grade;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.suggee.edustudent.R;
import com.suggee.edustudent.bean.Classes;
import com.suggee.edustudent.widgets.LabelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/2
 * Description:
 */
public class ClassesFragmentAdapter extends RecyclerView.Adapter<ClassesFragmentAdapter.ClassesHolder> {

    private List<Classes> mDatas;

    public void setDatas(List<Classes> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public List<Classes> getDatas() {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        return mDatas;
    }

    @Override
    public ClassesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClassesHolder(LayoutInflater.from(parent.getContext())
                                               .inflate(R.layout.frag_classes_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ClassesHolder holder, final int position) {
        final Classes data = getDatas().get(position);
        if (data != null) {
            Glide.with(holder.icon.getContext()).load(data.getLogo()).crossFade().into(holder.icon);
            holder.label.setText("未审核");
            holder.name.setText(data.getTitle());
            holder.num.setText(String.valueOf(data.getNum()));
            holder.team.setText(data.getTypeName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), data.getTitle(), Toast.LENGTH_SHORT).show();
                    ClassDetailActivity.launch(v.getContext(), data.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return getDatas().size();
    }

    static class ClassesHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.class_icon)
        ImageView icon;
        @BindView(R.id.class_label)
        LabelView label;
        @BindView(R.id.class_name)
        TextView name;
        @BindView(R.id.class_num)
        TextView num;
        @BindView(R.id.class_team)
        TextView team;

        public ClassesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
