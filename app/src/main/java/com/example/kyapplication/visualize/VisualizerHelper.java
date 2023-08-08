package com.example.kyapplication.visualize;

import android.media.audiofx.Visualizer;

import com.example.kyapplication.utils.F;

public class VisualizerHelper {

    private Visualizer mVisualizer;
    private VisualizeCallback mCallback;
    private int mCount;
    public void setVisualCount(int count) {
        mCount = count;
    }

    public void setVisualizeCallback(VisualizeCallback callback) {
        mCallback = callback;
    }

    /**
     * Sets the audio session id for the currently playing audio
     *
     * @param audioSessionId of the media to be visualised
     */
    public void setAudioSessionId(int audioSessionId) {
        if (mVisualizer != null)
            release();

        mVisualizer = new Visualizer(audioSessionId);
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
                                              int samplingRate) {
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft,
                                         int samplingRate) {

                float[] model = new float[fft.length / 2 + 1];
                model[0] = (float) Math.abs(fft[1]);
                int j = 1;

                //取长度需要的部分
//                for (int i = 2; i < mCount* 2;) {
//
//                    model[j] = (float) Math.hypot(fft[i], fft[i + 1]);
//                    i += 2;
//                    j++;
//                    model[j] = Math.abs(model[j]);
//                }



//                根据长度去平均值
//                for (int i = 2; i < fft.length / 2;) {
//
//                    model[j] = (float) Math.hypot(fft[i], fft[i + 1]);
//                    i += 2;
//                    j++;
//                    model[j] = Math.abs(model[j]);
//                }
//                j = 0;
//                int zheng = (fft.length / 2)/mCount;
//                float[] data = new float[mCount];
//                for (int i = 0; i < mCount *2;) {
//                    float fen4 = 0f;
//                    for(int l=0;l<zheng;l++)
//                    {
//                        fen4+=model[i];
////                        j++;
//                        i++;
//                    }
//                    data[j]=fen4/zheng;
//                    j++;
//                }

                    for (int i = 2; i < fft.length/2;) {
                    model[j] = (float) Math.hypot(fft[i], fft[i + 1]);
                    i += 2;
                    j++;
                    model[j] = Math.abs(model[j]);
                }



                if (mCallback != null) {
                    mCallback.onFftDataCapture(model);
                }
                F.d("model.length:"+model.length);
            }
        }, Visualizer.getMaxCaptureRate() / 3, false, true);

        mVisualizer.setEnabled(true);
    }

    /**
     * Releases the visualizer
     */
    public void release() {
        if (mVisualizer != null)
            mVisualizer.release();
    }
}
