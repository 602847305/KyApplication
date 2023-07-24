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

    private Button btnBar,btnCircle,musicWave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_a,container,false);
        btnBar = rootView.findViewById(R.id.bar_view);
        btnCircle = rootView.findViewById(R.id.circle_view);
        musicWave = rootView.findViewById(R.id.music_wave);
        btnCircle.setOnClickListener(this);
        btnBar.setOnClickListener(this);
        musicWave.setOnClickListener(this);
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
//            Navigation.findNavController(view).navigate(R.id.action_fragmentA_to_barFragment);

//            FragmentManager mFragmentManager;
//            FragmentTransaction fragmentTransaction;
//            mFragmentManager = getParentFragmentManager();
//            fragmentTransaction = mFragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.root,new BarFragment());
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
        }else if(view.getId() ==ResourceUtil.getId("music_wave"))
        {

            intent.putExtra("view",3);
        }

        startActivity(intent);

    }
}
