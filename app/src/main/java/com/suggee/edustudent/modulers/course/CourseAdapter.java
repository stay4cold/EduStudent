package com.suggee.edustudent.modulers.course;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.suggee.edustudent.R;
import com.suggee.edustudent.bean.Course;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/3
 * Description:
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {

    private List<Course> mDatas;

    public void setDatas(List<Course> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public List<Course> getDatas() {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        return mDatas;
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CourseHolder(LayoutInflater.from(parent.getContext())
                                              .inflate(R.layout.frag_course_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, final int position) {
        final Course data = getDatas().get(position);
        if (data != null) {
            Glide.with(holder.itemView.getContext())
                 .load(data.getImage())
                 .crossFade()
                 .into(holder.courseIcon);
            holder.courseTitle.setText(data.getTitle());
            holder.courseName.setText(data.getUname());
            holder.courseSchool.setText(data.getSchool());
            holder.viewNum.setText(String.valueOf(data.getViewNum()));
            holder.spot.setText(String.valueOf(data.getUp()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), data.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return getDatas().size();
    }

    static class CourseHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.course_icon)
        ImageView courseIcon;
        @BindView(R.id.course_title)
        TextView courseTitle;
        @BindView(R.id.course_name)
        TextView courseName;
        @BindView(R.id.course_school)
        TextView courseSchool;
        @BindView(R.id.view_num)
        TextView viewNum;
        @BindView(R.id.spot)
        TextView spot;

        public CourseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
