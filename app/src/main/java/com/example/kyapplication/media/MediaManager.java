package com.example.kyapplication.media;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.example.kyapplication.utils.F;

import java.io.IOException;


public class MediaManager {

    private MediaPlayer mediaPlayer;
    private Context mContext;
    private MediaManagerListener mListener;

    private MediaManager() {
    }

    public MediaManager(Context context) {
        mContext = context;
    }


    /**
     * play audio by res id
     *
     * @param raw res id of audio
     */
    public void play(final int raw) {
        try {
            mediaPlayer = MediaPlayer.create(mContext, raw);
            if (mediaPlayer == null) {
                F.d("mediaPlayer is null");
                return;
            }

            mediaPlayer.setOnErrorListener(null);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (mListener != null) {
                        mListener.onPrepare();
                    }
                }
            });
            mediaPlayer.start();
        } catch (Exception e) {
            F.d(e.getMessage());
        }
    }

    /**
     * play audio by file path
     *
     * @param filePath file path of audio
     */
    public void play(final String filePath) {
        try {
            mediaPlayer = MediaPlayer.create(mContext, Uri.parse(filePath));
            if (mediaPlayer == null) {
                F.d("mediaPlayer is null");
                return;
            }

            mediaPlayer.setOnErrorListener(null);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (mListener != null) {
                        mListener.onPrepare();
                    }
                }
            });
            mediaPlayer.start();
        } catch (Exception e) {
            F.d(e.getMessage());
        }
    }

    /**
     * play audio by file path from assets
     * @param assetsFileName 在assets文件夹中的文件名
     */
    public void playByAssetsFile(Context context,final String assetsFileName) {
        AssetManager assetManager = context.getAssets();
        AssetFileDescriptor assetFileDescriptor;
        try {
            assetFileDescriptor = assetManager.openFd(assetsFileName);
        } catch (IOException e) {

           F.d("file open failed");
            return;
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
        } catch (IOException e) {

            F.d("setDataSource failed");
            return;
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mediaPlayer!=null)
                    mediaPlayer.release();
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                F.d("onPrepared````````准备完成");
                if (mListener != null) {
                    mListener.onPrepare();
                }
                mp.start();
//                audioSessionId = mp.getAudioSessionId();
//                initVisualizer(audioSessionId);

            }
        });
        mediaPlayer.setOnErrorListener(null);
    }
    public int getMediaPlayerId() {
        return mediaPlayer.getAudioSessionId();
    }

    public void setMediaManagerListener(MediaManagerListener listener) {
        mListener = listener;
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
