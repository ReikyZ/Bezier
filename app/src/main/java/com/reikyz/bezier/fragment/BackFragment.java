package com.reikyz.bezier.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.reikyz.bezier.EventConfig;
import com.reikyz.bezier.R;
import com.reikyz.bezier.adapter.SimpleAdapter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reikyZ on 2016/10/15.
 */

public class BackFragment extends BaseFragment {

    private ListView lv;

    public static BaseFragment newInstance() {
        BaseFragment baseFragment = new BackFragment();
        return baseFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_back, container, false);
        EventBus.getDefault().register(this);
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
    }

}
