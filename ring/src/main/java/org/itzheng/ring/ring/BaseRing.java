package org.itzheng.ring.ring;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-5-8.
 */
public abstract class BaseRing implements IRing {
    private static final String TAG = "BaseRing";
    /**
     * 播放器实例
     */
    protected MediaPlayer mMediaPlayer;
    /**
     * 静音默认不开启
     */
    protected boolean mIsMute = false;
    /**
     * 循环播放默认开启
     */
    protected boolean mIsLoop = true;

    @Override
    public void stopRing() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        onStopListener();
    }

    /**
     * 停止播放的监听，可以在此移除计时器
     */
    private void onStopListener() {
        handler.removeCallbacks(stopRing);
        if (mOnStopListener != null) {
            mOnStopListener.onStop();
        }
    }

    /**
     * 停止时间
     */
    protected long mStopMillis = 0;
    Handler handler = new Handler();
    Runnable stopRing = new Runnable() {
        @Override
        public void run() {
            //时间到就停止
            stopRing();
        }
    };

    @Override
    public void setRingTime(long millis) {
        mStopMillis = millis;
        handler.removeCallbacks(stopRing);
        if (mStopMillis > 0) {
            handler.postDelayed(stopRing, mStopMillis);
        }

    }

    /**
     * 释放引用，避免多次调用stop
     */
    protected void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }


    @Override
    public void setMute(boolean isMute) {
        mIsMute = isMute;
        if (mMediaPlayer != null) {
            if (mIsMute) {
                mMediaPlayer.setVolume(0, 0);
            } else {
                mMediaPlayer.setVolume(1, 1);
            }
        }
    }

    @Override
    public void setLooping(boolean isLoop) {
        mIsLoop = isLoop;
        if (mMediaPlayer != null) {
            mMediaPlayer.setLooping(mIsLoop);
        }
    }

    private OnStopListener mOnStopListener;

    @Override
    public void setOnStopListener(OnStopListener listener) {
        mOnStopListener = listener;
    }

    /**
     * 设置播放完成监听
     */
    void setOnCompletionListener() {
        if (mMediaPlayer != null) {
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.w(TAG, "onCompletion: ");
                    onStopListener();
                }
            });
        }
    }

    /**
     * 设置播放器初始参数
     */
    protected void initMediaPlayer() {
        setOnCompletionListener();
        setMute(mIsMute);
        setLooping(mIsLoop);
        setRingTime(mStopMillis);
    }
}
