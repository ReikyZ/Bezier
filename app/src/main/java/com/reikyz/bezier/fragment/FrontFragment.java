package com.reikyz.bezier.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.reikyz.bezier.EventConfig;
import com.reikyz.bezier.R;
import com.reikyz.bezier.adapter.SimpleAdapter;
import com.reikyz.bezier.view.BezierActionBar;
import com.reikyz.bezier.view.NavigationView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reikyZ on 2016/10/15.
 */

public class FrontFragment extends BaseFragment implements View.OnClickListener {

    final static String TAG = "==FrontFragment==";

    BezierActionBar bzrBar;
    NavigationView ivNavi;
    ListView lv;

    public static FrontFragment newInstance() {
        FrontFragment frontFragment = new FrontFragment();
        return frontFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_front, container, false);
        EventBus.getDefault().register(this);

        bzrBar = (BezierActionBar) view.findViewById(R.id.bzr);

        ivNavi = (NavigationView) view.findViewById(R.id.iv_navi);
        ivNavi.setOnClickListener(this);

        lv = (ListView) view.findViewById(R.id.lv);
        initList();
        return view;
    }

    private void initList() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("data-" + i);
        }
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), datas);
        lv.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.e(TAG, "onDestroy");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_navi:
                Log.e(TAG, "front post");
                EventBus.getDefault().post(0, EventConfig.SWITCH_FRAGMENT);
                break;
        }
    }

    @Subscriber(tag = EventConfig.NAVI_STATUS)
    void setStatus(boolean open) {
        Log.e(TAG, "setStatus receive");
        ivNavi.changeStatus(open);
        bzrBar.startAnimate();
    }

}
