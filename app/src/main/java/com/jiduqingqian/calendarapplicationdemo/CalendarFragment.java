package com.jiduqingqian.calendarapplicationdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by qianhao on 2016/12/27.
 */

public class CalendarFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Calendar calendar;
    private ArrayList<String> data = new ArrayList<>();
    private CalendarAdapter calendarAdapter;
    private TextView year_month;
    private int year, month;

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        if (year_month != null) {
            initData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        year_month = (TextView) view.findViewById(R.id.year_month);
        //设置布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 7);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        gridLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        calendarAdapter = new CalendarAdapter();
        mRecyclerView.setAdapter(calendarAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initData();
        return view;
    }

    private void initData() {
        data.clear();
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        year_month.setText(year + "/" + (month + 1));
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
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_calendar, null);
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
