package com.example.kyapplication.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kyapplication.R;
import com.example.kyapplication.widget.CircleView;
import com.example.kyapplication.widget.ColorBar;

public class CircleFragment extends Fragment {
    private View rootView;
    final int START = 0;
    final int SPORT = 1;
    final int REST = 2;

    private CircleView circleView;
    private Button btnStart;
    final int sportTime = 10*1000;
    final int restTime = 5*1000;
    final int num = 5;
    final int allTime = sportTime*num +restTime*(num-1);

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            switch (msg.what)
            {
                case START:
                    circleView.setOutTime(allTime,360,0);
                    circleView.setInnerTime(restTime,360,0);
                    break;

                case SPORT:
                    circleView.setInnerAnnulusColor(R.color.colorPrimary);
                    circleView.setInnerTime(sportTime,360,0);
                    break;

                case REST:
                    circleView.setInnerAnnulusColor(R.color.colorAccent);
                    circleView.setInnerTime(restTime,360,0);
                    break;

            }


            return false;
        }
    });
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_c,container,false);

        ColorBar outerColorBar = rootView.findViewById(R.id.out_color_bar);
        ColorBar innerColorBar = rootView.findViewById(R.id.inner_color_bar);
        btnStart  = rootView.findViewById(R.id.btn_start);


        circleView =rootView.findViewById(R.id.circle_view);

        innerColorBar.setOnColorChangedListener(new ColorBar.OnColorChangedListener() {
            @Override
            public void onChanged(int color) {
                if(getContext()==null)
                    return;
                circleView.setInnerAnnulusColorInt(color);
            }
        });

        outerColorBar.setOnColorChangedListener(new ColorBar.OnColorChangedListener() {
            @Override
            public void onChanged(int color) {
                if(getContext()==null)
                    return;
                circleView.setOutAnnulusColorInt(color);
            }
        });



        Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                circleView.setOutTime(allTime,360,0);
//                circleView.setInnerTime(restTime,360,0);
                boolean isSport = false;
                Message message = Message.obtain();
                message.what = START;
                handler.sendMessage(message);

                for(int i=num*2-1;i>0;i--)
                {
                    if(!isSport)
                    {
                        isSport = true;
                        message = Message.obtain();
                        message.what = SPORT;
                        handler.sendMessage(message);
                        try {
                            Thread.sleep(sportTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else
                    {
                        isSport = false;
                        message = Message.obtain();
                        message.what = REST;
                        handler.sendMessage(message);
                        try {
                            Thread.sleep(restTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });


        return rootView;
    }
}
