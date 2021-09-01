package com.example.ringdemo;

import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import org.itzheng.ring.bean.RingItem;
import org.itzheng.ring.mix.VibrateRingPlayer;
import org.itzheng.ring.ring.IRingPlayer;
import org.itzheng.ring.ring.RingPlayer;
import org.itzheng.ring.ring.list.AssetRingtone;
import org.itzheng.ring.ring.list.IRingtone;
import org.itzheng.ring.ring.list.SystemRingtone;
import org.itzheng.ring.vibrate.impl.MyVibrate;

import java.util.List;

/**
 * 响铃模式，
 * 震动模式
 */
public class RingModeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RingModeActivity";
    ToggleButton btnMute;
    VibrateRingPlayer mPlayer;
    RingItem mRingItem;

    //    MyVibrate vibrate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringmode);
        IRingtone ringtone = new SystemRingtone();
        List<RingItem> list = ringtone.getRingtone(this, RingtoneManager.TYPE_RINGTONE);
        mRingItem = list.get(0);
        mPlayer = new VibrateRingPlayer(this);
        mPlayer.setLooping(true);
        btnMute = findViewById(R.id.btnMute);
        btnMute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mPlayer.setMute(isChecked);
            }
        });
        findViewById(R.id.btnPlay).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);
        findViewById(R.id.btnVibrateModel).setOnClickListener(this);
    }

    boolean isVibrate = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                mPlayer.play(mRingItem);
                break;
            case R.id.btnStop:
                mPlayer.stopRing();
                break;

            case R.id.btnVibrateModel:
                isVibrate = !isVibrate;
                mPlayer.setVibrate(isVibrate);
                break;

        }
    }
}
