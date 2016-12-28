package com.jiduqingqian.calendarapplicationdemo;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by qianhao on 2016/12/28.
 */

public class CalendarView extends LinearLayout {

    public CalendarView(Context context) {
        super(context);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_calendar, null);
        initView(view);
        initData();
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_calendar, null);
        initView(view);
        initData();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private RecyclerView mRecyclerView;
    private Calendar calendar;
    private ArrayList<String> data = new ArrayList<>();
    private CalendarAdapter calendarAdapter;
    private TextView year_month;
    private Context context;

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        initData();
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        year_month = (TextView) view.findViewById(R.id.year_month);
        //设置布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        gridLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        calendarAdapter = new CalendarAdapter();
        mRecyclerView.setAdapter(calendarAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    private void initData() {
        data.clear();
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        year_month.setText(calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int DAY_OF_WEEK = calendar.get(Calendar.DAY_OF_WEEK);//获取每月1号周几


        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);//获取当前月一共多少天

        calendar.add(Calendar.DAY_OF_MONTH, 2 - DAY_OF_WEEK - DAY_OF_MONTH);

        int totalDays = DAY_OF_WEEK + DAY_OF_MONTH - 1;
        if (totalDays % 7 > 0) {
            totalDays += (7 - totalDays % 7);
        }
        for (int i = 0; i < totalDays; i++) {
            data.add(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        calendarAdapter.notifyDataSetChanged();
    }

    private class CalendarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_calendar, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolder) {
                ((MyViewHolder) holder).textView.setText(data.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            //在布局中找到所含有的UI组件
            public MyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.time);
            }
        }

    }
}
