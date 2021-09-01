package com.example.ringdemo;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.itzheng.ring.bean.RingItem;
import org.itzheng.ring.ring.RingPlayer;
import org.itzheng.ring.ring.list.AssetRingtone;
import org.itzheng.ring.ring.list.IRingtone;
import org.itzheng.ring.ring.list.ResRawRingtone;
import org.itzheng.ring.ring.list.SystemRingtone;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnRingtone).setOnClickListener(this);
        findViewById(R.id.btnVibrate).setOnClickListener(this);
        findViewById(R.id.btnRingMode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRingtone:
                startActivity(RingtoneActivity.class);
                break;
            case R.id.btnVibrate:
                IRingtone ringtone=new AssetRingtone();
                List<RingItem> list = ringtone.getRingtone(this, RingtoneManager.TYPE_ALL);
                RingPlayer player=new RingPlayer(this);
                player.play(list.get(0));
                player.setRingTime(10*1000);
                break;
            case R.id.btnRingMode:
                startActivity(RingModeActivity.class);
                break;
        }
    }

    /**
     * 跳转
     * @param cls
     */
    private void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }
}
