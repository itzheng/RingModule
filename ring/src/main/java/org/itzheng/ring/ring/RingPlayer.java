package org.itzheng.ring.ring;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;

import org.itzheng.ring.bean.RingItem;

import java.io.IOException;

/**
 * 铃音播放器
 */
public class RingPlayer implements IRingPlayer {
    private Context mContext;

    /**
     * 构造函数
     *
     * @param context
     */
    public RingPlayer(Context context) {
        mContext = context.getApplicationContext();
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

    public void play(RingItem ringItem) {
        if (ringItem == null) {
            throw new NullPointerException("ringItem not null");
        }
        if (ringItem.isAssets) {
            playFromAssets(ringItem.path);
        } else {
            play(ringItem.uri);
        }
    }

    private boolean mPlaying = false;

    @Override
    public boolean isPlaying() {
        return mPlaying;
    }

    /**
     * 播放音乐
     *
     * @param uri
     */
    public void play(Uri uri) {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        mMediaPlayer = MediaPlayer.create(mContext, uri);
        initMediaPlayer();
        mMediaPlayer.start();
        mPlaying = true;
    }

    /**
     * 播放asset 下的音频文件
     *
     * @param assetsFile
     */
    public void playFromAssets(String assetsFile) {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        mMediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor assetFileDescriptor = mContext.getAssets().openFd(assetsFile);
            //设置媒体播放器的数据资源
            mMediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initMediaPlayer();
        mMediaPlayer.start();
        mPlaying = true;
    }

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

    public void stopRing() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mPlaying = false;
        onStopListener();
    }

    /**
     * 停止播放的监听，可以在此移除计时器
     */
    private void onStopListener() {
        handler.removeCallbacks(stopRing);
        if (mOnRingListener != null) {
            mOnRingListener.onStop();
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

    /**
     * 响铃时间，时间一到，马上停止
     *
     * @param millis
     */
    public void setRingTime(long millis) {
        mStopMillis = millis;
        handler.removeCallbacks(stopRing);
        if (mStopMillis > 0) {
            handler.postDelayed(stopRing, mStopMillis);
        }

    }


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

    /**
     * 循环播放
     *
     * @param isLoop
     */
    public void setLooping(boolean isLoop) {
        mIsLoop = isLoop;
        if (mMediaPlayer != null) {
            mMediaPlayer.setLooping(mIsLoop);
        }
    }

    private OnRingListener mOnRingListener;

    /**
     * 设置停止监听
     *
     * @param listener
     */
    public void setOnStopListener(OnRingListener listener) {
        mOnRingListener = listener;
    }

    /**
     * 设置播放完成监听
     */
    void setOnCompletionListener() {
        if (mMediaPlayer != null) {
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    onStopListener();
                }
            });
        }
    }
}
