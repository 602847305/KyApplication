package com.example.kyapplication.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kyapplication.R;
import com.example.kyapplication.ui.activity.ShowViewActivity;
import com.example.kyapplication.utils.F;
import com.example.kyapplication.utils.ResourceUtil;

public class FragmentA extends Fragment implements View.OnClickListener{
    private View rootView;

    private Button btnBar,btnCircle,musicWave,musicWave2,musicWave3,musicWave4,musicWave5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_a,container,false);
        btnBar = rootView.findViewById(R.id.bar_view);
        btnCircle = rootView.findViewById(R.id.circle_view);
        musicWave = rootView.findViewById(R.id.music_wave);
        musicWave2 = rootView.findViewById(R.id.music_wave2);
        musicWave3 = rootView.findViewById(R.id.music_wave3);
        musicWave4 = rootView.findViewById(R.id.music_wave4);
        musicWave5 = rootView.findViewById(R.id.music_wave5);
        btnCircle.setOnClickListener(this);
        btnBar.setOnClickListener(this);
        musicWave.setOnClickListener(this);
        musicWave2.setOnClickListener(this);
        musicWave3.setOnClickListener(this);
        musicWave4.setOnClickListener(this);
        musicWave5.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        F.d(view.getId());
        Intent intent = new Intent(getActivity(), ShowViewActivity.class);
        F.d("``````onClick````````");

        if(view.getId() ==ResourceUtil.getId("circle_view"))
        {
            intent.putExtra("view",1);
//            Navigation.findNavController(view).navigate(R.id.action_fragmentA_to_circleFragment);

//            FragmentManager mFragmentManager;
//            FragmentTransaction fragmentTransaction;
//            mFragmentManager = getParentFragmentManager();
//            fragmentTransaction = mFragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.root,new CircleFragment());
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
        }else if(view.getId() ==ResourceUtil.getId("bar_view"))
        {
            intent.putExtra("view",2);
        }else if(view.getId() ==ResourceUtil.getId("music_wave"))
        {
            intent.putExtra("view",3);
        }else if(view.getId() ==ResourceUtil.getId("music_wave2"))
        {
            intent.putExtra("view",4);
        }else if(view.getId() ==ResourceUtil.getId("music_wave3"))
        {
            intent.putExtra("view",5);
        }else if(view.getId() ==ResourceUtil.getId("music_wave4"))
        {
            intent.putExtra("view",6);
        }else if(view.getId() ==ResourceUtil.getId("music_wave5"))
        {
            intent.putExtra("view",7);
        }

        startActivity(intent);

    }
}
