package org.itzheng.ring.mix;

import android.content.Context;
import android.net.Uri;

import org.itzheng.ring.bean.RingItem;
import org.itzheng.ring.ring.IRingPlayer;
import org.itzheng.ring.ring.RingPlayer;
import org.itzheng.ring.ring.list.IRingtone;
import org.itzheng.ring.vibrate.IVibrate;
import org.itzheng.ring.vibrate.impl.MyVibrate;

/**
 * 震动铃音播放器
 */
public class VibrateRingPlayer implements IRingPlayer {
    IRingPlayer mRingPlayer;
    IVibrate mVibrate;

    public VibrateRingPlayer(Context context) {
        mRingPlayer = new RingPlayer(context);
        mVibrate = new MyVibrate(context);
    }

    @Override
    public void play(RingItem ringItem) {
        mRingPlayer.play(ringItem);
        if (mIsVibrate) {
            mVibrate.startVibrate();
        }
    }

    @Override
    public boolean isPlaying() {
        return mRingPlayer.isPlaying();
    }

    @Override
    public void play(Uri uri) {
        mRingPlayer.play(uri);
        if (mIsVibrate) {
            mVibrate.startVibrate();
        }
    }

    @Override
    public void playFromAssets(String assetsFile) {
        mRingPlayer.playFromAssets(assetsFile);
        mVibrate.startVibrate();
    }

    @Override
    public void stopRing() {
        mRingPlayer.stopRing();
        mVibrate.cancel();
    }

    @Override
    public void setRingTime(long millis) {
        mRingPlayer.setRingTime(millis);
    }

    @Override
    public void setMute(boolean isMute) {
        mRingPlayer.setMute(isMute);
    }

    @Override
    public void setLooping(boolean isLoop) {
        mRingPlayer.setLooping(isLoop);
    }

    @Override
    public void setOnStopListener(OnRingListener listener) {
        mRingPlayer.setOnStopListener(listener);
    }

    public void setModel(long[] pattern, int repeat) {
        mVibrate.setModel(pattern, repeat);
    }

    private boolean mIsVibrate = true;

    /**
     * 震动开关
     *
     * @param isVibrate
     */
    public void setVibrate(boolean isVibrate) {
        mIsVibrate = isVibrate;
        if (isVibrate) {
            if (mRingPlayer.isPlaying()) {
                mVibrate.startVibrate();
            }
        } else {
            mVibrate.cancel();
        }
    }

}
