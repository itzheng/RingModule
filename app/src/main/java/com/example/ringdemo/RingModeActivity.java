package com.example.ringdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import org.itzheng.ring.ring.RingPlayer;
import org.itzheng.ring.vibrate.impl.MyVibrate;

/**
 * 响铃模式，
 * 震动模式
 */
public class RingModeActivity extends AppCompatActivity {
    private static final String TAG = "RingModeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringmode);
        ToggleButton btnMute = findViewById(R.id.btnMute);
        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnStop = findViewById(R.id.btnStop);
        Button btnVibrateModel = findViewById(R.id.btnVibrateModel);
        final RingPlayer systemRing = new RingPlayer(this);
        final MyVibrate vibrate = new MyVibrate(this);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                systemRing.playRing(0);
                vibrate.startVibrate();
            }
        });
        //手动停止响铃
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                systemRing.stopRing();
            }
        });
        //静音测试
        btnMute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                systemRing.setMute(isChecked);
            }
        });
        //震动模式测试
        btnVibrateModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long[] longs = {1000, 1000, 1000, 1000};
                vibrate.setModel(longs, 0);
            }
        });
        //响铃时间测试
        systemRing.setRingTime(10000);
        //停止监听测试
        systemRing.setOnStopListener(new RingPlayer.OnRingListener() {
            @Override
            public void onStop() {
//                Log.w(TAG, "onStop: ");
                vibrate.cancel();
            }
        });

    }
}
