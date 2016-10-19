package com.reikyz.bezier.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.reikyz.bezier.EventConfig;
import com.reikyz.bezier.R;
import com.reikyz.bezier.Utils.DentistyConvert;
import com.reikyz.bezier.activity.BaseActivity;
import com.reikyz.bezier.fragment.BackFragment;
import com.reikyz.bezier.fragment.FrontFragment;
import com.reikyz.bezier.view.BezierActionBar;
import com.reikyz.bezier.view.VerticalViewPager;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    final static String TAG = "==MainActivity==";

    private LinearLayout llContainer;
    private VerticalViewPager verticalViewPager;
    private FragmentStatePagerAdapter adapter;

    boolean frontOpen = false;
    float lastOffset = 0.0f;
    float scrollIntent = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verticalViewPager = (VerticalViewPager) findViewById(R.id.vvp);
        verticalViewPager.addOnPageChangeListener(new GuidePagerChangeListener());

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return BackFragment.newInstance();
                } else {
                    return FrontFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

        verticalViewPager.setAdapter(adapter);

        llContainer = (LinearLayout) findViewById(R.id.container);
        llContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return verticalViewPager.dispatchTouchEvent(event);
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    class GuidePagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            scrollIntent = positionOffset - lastOffset;
            if (position == 0) {
                verticalViewPager.getChildAt(position).layout(0, 0, verticalViewPager.getWidth(), verticalViewPager.getHeight());
            }

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) verticalViewPager.getLayoutParams();
            params.setMargins(0, 0, 0, (int) (DentistyConvert.dp2px(70) * (1 - positionOffset)));
            verticalViewPager.setLayoutParams(params);
            if (position == 1) {
                frontOpen = true;
                params.setMargins(0, 0, 0, 0);
                verticalViewPager.setLayoutParams(params);
            } else {
                frontOpen = false;
            }
            lastOffset = positionOffset;
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (scrollIntent != 0.0f)
                    EventBus.getDefault().post(frontOpen, EventConfig.NAVI_STATUS);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = EventConfig.SWITCH_FRAGMENT)
    void switchFragment(int i) {
        Log.e(TAG, "main receive");
        if (frontOpen) {
            verticalViewPager.setCurrentItem(0);
        } else {
            verticalViewPager.setCurrentItem(1);
        }
        frontOpen = !frontOpen;
    }

}
