package com.example.asus.headlinestoday.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.asus.headlinestoday.R;
import com.example.asus.headlinestoday.bean.Bean_tuijain;
import com.example.asus.headlinestoday.bean.Tuijian_Bean;
import com.example.asus.headlinestoday.wedview.WebviewActiviti;
import com.example.asus.headlinestoday.xlistview.XListView;
import com.example.asus.headlinestoday.adapter.Myadapter;
import com.example.asus.headlinestoday.httputils.Httputies;
import com.example.asus.headlinestoday.httputils.RequestBean;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 2017/9/5.
 */


public class Tuijian_fragment extends Fragment implements XListView.IXListViewListener {
    private List<Tuijian_Bean.DataBean> list = new ArrayList<>();
    private List<Tuijian_Bean.DataBean> mData;
    private XListView mLv;
    private Myadapter mMyadapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list, null);
        mLv = (XListView) view.findViewById(R.id.lv_list);
        mLv.setPullRefreshEnable(true);
        mLv.setPullLoadEnable(true);
        mLv.setXListViewListener(this);
        mMyadapter = new Myadapter(getActivity(), list);
        mLv.setAdapter(mMyadapter);
        getData();

        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mData != null) {
                    i--;
                    Intent intent = new Intent(getActivity(), WebviewActiviti.class);
                    intent.putExtra("url", mData.get(i).getUrl());
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
       mMyadapter.upData(mData);
        onLoad();

    }

    @Override
    public void onLoadMore() {
       mMyadapter.addData(mData);

        onLoad();
    }

    private void onLoad() {
        mLv.stopRefresh();
        mLv.stopLoadMore();
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        //格式化
        SimpleDateFormat formatter = new SimpleDateFormat();
        String time = formatter.format(curDate);
        mLv.setRefreshTime(time);


    }


    public void getData() {
        String url = null;
        try {
            url = "http://ic.snssdk.com/2/article/v25/stream/?count=20&min_behot_time=1455521444&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82";
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBean requestBean = new RequestBean(url);
        new Httputies().getDataFromServer(getActivity(), requestBean, new Httputies.DataCallBack() {




            @Override
            public void prosseData(String json) {
                if (json != null && !json.equals("")) {
                    Gson gson = new Gson();
                    Tuijian_Bean fromJson = gson.fromJson(json, Tuijian_Bean.class);
                    if (fromJson != null) {
                        mData = fromJson.getData();
                    }
                    if (mData != null) {
                        list.addAll(mData);
                        mMyadapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }


}
