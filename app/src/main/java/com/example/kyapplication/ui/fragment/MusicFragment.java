package com.example.kyapplication.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kyapplication.R;
import com.example.kyapplication.utils.F;
import com.example.kyapplication.widget.MusicalWave2;

public class MusicFragment extends Fragment {

    private View musicRoundView;
    private MusicalWave2 mMusicalWave2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        F.d("```````````MusicFragment");
        return inflater.inflate(R.layout.fragment_music_a, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        musicRoundView = view.findViewById(R.id.music_image_view);
        if (musicRoundView !=null)
        {
            F.d("``````````onViewCreated");
            rotate();
        }

        mMusicalWave2 = new MusicalWave2(getContext());
        if(getContext()!=null) {
            mMusicalWave2.init(getContext(), "music1.mp3", 100);
        }
    }

    private void rotate()
    {
        F.d("```````````rotate");
        RotateAnimation rotateAnimation = new RotateAnimation(
                0,360, Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000*30);//动画执行一次所需要的时间
        rotateAnimation.setRepeatCount(Animation.INFINITE);//重复次数 - 无次数限制
        rotateAnimation.setRepeatMode(Animation.RESTART);//设置动画的重复模式
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            //重复动画
            }
        });
        musicRoundView.startAnimation(rotateAnimation);

    }
}
