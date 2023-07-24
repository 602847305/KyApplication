package com.example.kyapplication.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kyapplication.R;
import com.example.kyapplication.bean.BarChartsData;
import com.example.kyapplication.widget.BarCharts;

import java.util.ArrayList;

public class BarFragment extends Fragment {
    private BarCharts mBarCharts ;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.bar_fragment,container,false);
        mBarCharts = rootView.findViewById(R.id.bar_charts);


        ArrayList<BarChartsData> list = new ArrayList<>();
        BarChartsData barCharts = new BarChartsData("西瓜1",9000);
        list.add(barCharts);
        barCharts = new BarChartsData("西瓜2",3000);
        list.add(barCharts);
        barCharts = new BarChartsData("西瓜3",1000);
        list.add(barCharts);
        barCharts = new BarChartsData("西瓜4",2625);
        list.add(barCharts);
        barCharts = new BarChartsData("西瓜5",3925);
        list.add(barCharts);
        barCharts = new BarChartsData("西瓜14",2625);
        list.add(barCharts);
        barCharts = new BarChartsData("西瓜35",3925);
        list.add(barCharts);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBarCharts.setData(list,6000,6);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
