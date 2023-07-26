//// 导入所需的库
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.media.MediaPlayer;
//import android.media.audiofx.Visualizer;
//import android.os.Bundle;
//import android.util.AttributeSet;
//import android.view.View;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.kyapplication.R;
//
//// 自定义View类
//public class WaveformView extends View {
//
//    private static final int MAX_AMPLITUDE = 32767; // 最大振幅
//    private static final int WAVEFORM_COLOR = Color.GREEN; // 波形颜色
//
//    private Paint paint; // 画笔
//    private byte[] waveformData; // 波形数据
//
//    public WaveformView(Context context) {
//        super(context);
//        init();
//    }
//
//    public WaveformView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    private void init() {
//        paint = new Paint();
//        paint.setColor(WAVEFORM_COLOR);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (waveformData != null) {
//            int width = getWidth();
//            int height = getHeight();
//            int centerY = height / 2;
//
//            for (int i = 0; i < waveformData.length - 1; i++) {
//                float x1 = width * i / (waveformData.length - 1);
//                float y1 = centerY + waveformData[i] * height / (2 * MAX_AMPLITUDE);
//                float x2 = width * (i + 1) / (waveformData.length - 1);
//                float y2 = centerY + waveformData[i + 1] * height / (2 * MAX_AMPLITUDE);
//                canvas.drawLine(x1, y1, x2, y2, paint);
//            }
//        }
//    }
//
//    // 设置波形数据
//    public void setWaveformData(byte[] waveformData) {
//        this.waveformData = waveformData;
//        invalidate(); // 更新视图
//    }
//}
//
//// 在Activity中使用WaveformView
//public class MainActivity extends AppCompatActivity {
//
//    private WaveformView waveformView;
//    private MediaPlayer mediaPlayer;
//    private Visualizer visualizer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        waveformView = findViewById(R.id.waveformView);
//
//        // 初始化MediaPlayer
//        mediaPlayer = MediaPlayer.create(this, R.raw.test_audio);
//
//        // 初始化Visualizer
//        int audioSessionId = mediaPlayer.getAudioSessionId();
//        visualizer = new Visualizer(audioSessionId);
////        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]); // 设置捕获大小为最大值
//        visualizer.setCaptureSize(5);
//        waveformView.setWaveformData(new byte[0]);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        visualizer.setEnabled(true); // 启用Visualizer
//
//        // 设置OnDataCaptureListener监听器，用于获取音频数据
//        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
//            @Override
//            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
//                waveformView.setWaveformData(waveform); // 将波形数据设置给WaveformView
//            }
//
//            @Override
//            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
//                // 不需要实现
//            }
//        }, Visualizer.getMaxCaptureRate() / 2, true, false);
//
//        mediaPlayer.start(); // 播放音频
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mediaPlayer.pause(); // 暂停音频
//        visualizer.setEnabled(false); // 禁用Visualizer
//    }
//}