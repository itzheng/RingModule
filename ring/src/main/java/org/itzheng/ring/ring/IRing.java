package org.itzheng.ring.ring;

import org.itzheng.ring.bean.RingItem;

import java.util.List;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-5-7.
 */
public interface IRing {
    /**
     * 获取铃声列表
     *
     * @return
     */
    List<RingItem> getRingList();

    /**
     * 播放指定位置的铃声，如果之前有铃声播放需要先停止
     *
     * @param index
     */
    void playRing(int index);

    /**
     * 设置响铃时长（最长时间，时间到，如果继续播放则停止，如果时间未到已经停止，则无效）
     * 时间小于或等于0则不生效
     * 1，looping模式下，时间到，则停止
     * 2，非looping模式，时间到，停止，如果时间还未到，铃声就停止，则不继续播放
     * 3，如果用户手动停止，则不继续
     *
     * @param millis
     */
    void setRingTime(long millis);

    /**
     * 停止响铃
     */
    void stopRing();

    /**
     * 设置静音
     *
     * @param isMute true 静音，false 响铃
     */
    void setMute(boolean isMute);

    /**
     * 是否循环
     *
     * @param isLoop
     */
    void setLooping(boolean isLoop);

    /**
     * 设置播放完成的监听
     *
     * @param listener
     */
//    void setOnCompletionListener(MediaPlayer.OnCompletionListener listener);
//    设置响铃时长，如果时间到，没有关掉的话，则自己关闭响铃，这个得外面自己写个监听，本工具不提供方法

    /**
     * 设置停止监听，停止是指播放停止了，有这么几种情况
     * 1，用户点击停止（调用stopRing方法）
     * 2，looping模式下，设置响铃时间到
     * 3，非looping模式，播放结束
     *
     * @param listener
     */
    void setOnStopListener(OnStopListener listener);

    interface OnStopListener {
        /**
         * 铃声停止
         */
        void onStop();
    }

}
