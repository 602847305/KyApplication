package com.example.kyapplication.ui.fragment;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kyapplication.R;
import com.example.kyapplication.utils.F;
import com.example.kyapplication.widget.AudioAndCircle;
import com.example.kyapplication.widget.AudioAndCircle2;
import com.example.kyapplication.widget.BaseAudioVisualizeView;
import com.example.kyapplication.widget.BaseAudioVisualizeView2;
import com.example.kyapplication.widget.RoundImageView;


import pub.devrel.easypermissions.EasyPermissions;

public class MusicFragment extends Fragment {

    private RoundImageView musicRoundView;
    private final String[] permission ={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO};
    private AudioAndCircle mAudioAndCircle;
    private int audioSessionId = 0;
    public static float[] mWaveData;
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
            rotate();
        }

        mAudioAndCircle = view.findViewById(R.id.music_audio_circle);
        if(!EasyPermissions.hasPermissions(getContext(),permission))
        {
            return;
        }
        mAudioAndCircle.setNumRays(80);


        mAudioAndCircle.play("music1.mp3");

    }

    /**
     * 旋转图片
     */
    private void rotate()
    {
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
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAudioAndCircle!=null)
            mAudioAndCircle.release();
    }
}
