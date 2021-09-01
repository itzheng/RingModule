package org.itzheng.ring.ring;

import android.content.Context;
import android.net.Uri;

import org.itzheng.ring.bean.RingItem;

import java.util.List;

/**
 * Title:铃音播放器<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-5-7.
 */
public interface IRingPlayer {
//    /**
//     * 播放指定位置的声音
//     *
//     * @param index
//     */
//    public void playRing(int index);

    /**
     * 播放指定铃音
     *
     * @param ringItem
     */
    public void play(RingItem ringItem);

    /**
     * 是否正在播放
     *
     * @return
     */
    public boolean isPlaying();

    /**
     * 播放音乐
     *
     * @param uri
     */
    public void play(Uri uri);

    /**
     * 播放asset 下的音频文件
     *
     * @param assetsFile
     */
    public void playFromAssets(String assetsFile);

    /**
     * 停止播放
     */
    public void stopRing();


    /**
     * 响铃时间，时间一到，马上停止
     *
     * @param millis
     */
    public void setRingTime(long millis);

    /**
     * 静音
     *
     * @param isMute
     */
    public void setMute(boolean isMute);

    /**
     * 循环播放
     *
     * @param isLoop
     */
    public void setLooping(boolean isLoop);

    /**
     * 设置停止监听
     *
     * @param listener
     */
    public void setOnStopListener(OnRingListener listener);

    /**
     * 铃声监听
     */
    public interface OnRingListener {
        /**
         * 铃声停止
         */
        void onStop();
    }
}
