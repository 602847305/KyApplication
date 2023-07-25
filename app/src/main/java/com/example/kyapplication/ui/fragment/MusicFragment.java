package com.example.kyapplication.ui.fragment;

import android.Manifest;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
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

import java.io.IOException;

import pub.devrel.easypermissions.EasyPermissions;

public class MusicFragment extends Fragment {

    private View musicRoundView;
    private final String[] permission ={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO};
    private MusicalWave2 mMusicalWave2;
    private int audioSessionId = 0;
    private MediaPlayer mediaPlayer;
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
            mMusicalWave2.init(getContext());
        }
        if(!EasyPermissions.hasPermissions(getContext(),permission))
        {
            return;
        }
        initMediaPlayer();
    }

    private void initMediaPlayer()
    {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                audioSessionId = mediaPlayer.getAudioSessionId();
                initVisualizer(audioSessionId);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mediaPlayer!=null)
                    mediaPlayer.release();
            }
        });
        AssetManager assetManager = getContext().getAssets();
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("music1.mp3");
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
            mediaPlayer.prepareAsync();//异步准备
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mediaPlayer.setOnErrorListener(null);


    }
    float max_int = 0;
    private void initVisualizer(int audioSessionId)
    {
        F.d("audioSessionId..."+audioSessionId);
        Visualizer visualizer = new Visualizer(audioSessionId);
        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {

            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
                float[] model = new float[fft.length / 2 + 1];
                model[0] = (byte) Math.abs(fft[1]);
                int j = 1;

                for (int i = 2; i < 50 *2;) {
                    model[j] = (float) Math.hypot(fft[i], fft[i + 1]);
                    i += 2;
                    j++;
                    model[j] = (float) Math.abs(fft[j]);

                    if(max_int<model[j])
                    {
                        max_int = (int) model[j];
                        F.d("maxint.."+max_int);
                    }

                }
                //model即为最终用于绘制的数据
            }
        }, Visualizer.getMaxCaptureRate() / 2, false, true);

        visualizer.setEnabled(true);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer !=null)
            mediaPlayer.release();
    }
}
