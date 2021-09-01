package org.itzheng.ring.ring.list;

import android.content.Context;

import org.itzheng.ring.bean.RingItem;

import java.util.List;

/**
 * 铃声列表的接口
 * 铃声有系统默认的铃声
 * 也有手机自带的铃声
 * 也有App内置的铃声
 */
public interface IRingtone {
    /**
     * 获取铃声列表,获取铃声是一个耗时操作，建议缓存
     * @param context 上下文，一般获取铃音都需要用到上下文参数
     * @param type RingtoneManager.TYPE_ALL = TYPE_RINGTONE | TYPE_NOTIFICATION | TYPE_ALARM
     * @return
     */
    List<RingItem> getRingtone(Context context, int type);

}
