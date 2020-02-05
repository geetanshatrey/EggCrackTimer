package com.spikedshark.eggcracktimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView result;
    SeekBar seek;
    Boolean timerOn=false;
    CountDownTimer countDownTimer;
    Button goButton;
    ImageView initialView ;
    ImageView finalView ;
    public void resetTimer(View view) {
        goButton.setVisibility(View.VISIBLE);
        if(timerOn==false)
            goButton.setText("GO!");
        result.setText("01:00");
        seek.setEnabled(true);
        initialView.animate().alpha(1);
        finalView.animate().alpha(0);
        seek.setProgress(60);
       if(timerOn)
           countDownTimer.cancel();
        goButton.setText("GO!");
        timerOn = false;
    }
    public void theTime(int i){
        int s=i%60;
        i=i/60;
        String h,m;
        if(i!=10) {
            h = Integer.toString(i);
            h="0"+h;
        }
        else
            h="10";
        m=Integer.toString(s);
        if(s<10){
            m="0"+m;
        }
        result.setText(h+":"+m);
    }
    public int getTheData()
    {
        String x= (String) result.getText();
        int m1,s1;
        m1=Integer.parseInt(x.substring(0,2));
        s1=Integer.parseInt(x.substring(3,5));
        s1=m1*60+s1;
        return s1;
    }
    public void startTimer(View view) {
        if(timerOn) {
            int ch;
            ch=getTheData();
            countDownTimer.cancel();
            goButton.setText("GO!");
            timerOn=false;
            seek.setEnabled(true);
            countDownTimer.cancel();
            seek.setProgress(ch);
        }
        else    {
            timerOn=true;
            seek.setEnabled(false);
            goButton.setText("PAUSE!");
            countDownTimer=new CountDownTimer(seek.getProgress()*1000 +100, 1000) {

                @Override
                public void onTick(long l) {
                    theTime(((int) l) / 1000);

                }

                public void onFinish() {
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();
                    seek.setEnabled(true);
                    goButton.setVisibility(View.INVISIBLE);
                    timerOn=false;
                    initialView.animate().alpha(0).setDuration(100);
                    finalView.animate().alpha(1).setDuration(100);
                }
            }.start();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result= findViewById(R.id.textView);
        seek= findViewById(R.id.seekBar);
        goButton=findViewById(R.id.button);
        initialView=findViewById(R.id.egg);
        finalView=findViewById(R.id.finalegg);
        seek.setMax(300);
        seek.setProgress(60);
        theTime(60);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                initialView.animate().alpha(1);
                finalView.animate().alpha(0);
                if(timerOn==false)
                    goButton.setText("GO!");
                theTime(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                initialView.animate().alpha(1);
                finalView.animate().alpha(0);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                initialView.animate().alpha(1);
                finalView.animate().alpha(0);
                goButton.setVisibility(View.VISIBLE);
                if(timerOn==false)
                    goButton.setText("GO!");
            }
        });

    }
}
