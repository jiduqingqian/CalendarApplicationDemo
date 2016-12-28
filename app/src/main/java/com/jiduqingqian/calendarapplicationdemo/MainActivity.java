package com.jiduqingqian.calendarapplicationdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<CalendarFragment> fragments = new ArrayList<>();
    private int middlePager = Short.MAX_VALUE / 2;
    private MyPagerAdapter myPagerAdapter;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private ArrayList<CalendarView> calendarViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initData();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        // myPagerAdapter = new MyPagerAdapter(calendarViews);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(middlePager);
    }

    private void initData() {
        fragments.add(new CalendarFragment());
        fragments.add(new CalendarFragment());
        fragments.add(new CalendarFragment());
        for (int i = 0; i < 4; i++) {
            CalendarView calendarView = new CalendarView(this);
            calendarViews.add(calendarView);
        }
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {


        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            CalendarFragment fragment = new CalendarFragment();
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.MONTH, position - middlePager);
            fragment.setCalendar(instance);
            return fragment;
        }

        @Override
        public int getCount() {
            return Short.MAX_VALUE;
        }
    }

    public class MyPagerAdapter extends PagerAdapter {
        private List<CalendarView> list;

        public MyPagerAdapter(List<CalendarView> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return Short.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            //获取需要加载视图的父控件
            CalendarView tv = calendarViews.get(position % list.size());
            ViewParent parent = tv.getParent();
            //判断父控件是否为空，若为空说明已被ViewPager加载，那么我们要移除改视图，这样就能保证一个视图只有一个父控件，
            if (parent != null) {
                viewPager.removeView(tv);
            }
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.MONTH, position - middlePager);
            tv.setCalendar(instance);
            container.addView(tv);
            return tv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
