package org.itzheng.ring.vibrate;

import android.annotation.SuppressLint;
import android.support.annotation.RequiresPermission;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-5-8.
 */
public interface IVibrate {
    /**
     * 开启震动
     */
    @RequiresPermission(android.Manifest.permission.VIBRATE)
    void startVibrate();

    /**
     * 设置震动模式
     *
     * @param pattern 等待几秒，震动几秒，等待几秒，震动几秒....
     * @param repeat  -1表示不重复, 如果不是-1, 比如改成1, 表示从前面这个long数组的下标为1的元素开始重复.
     */
    @RequiresPermission(android.Manifest.permission.VIBRATE)
    void setModel(long[] pattern, int repeat);

    /**
     * 停止震动
     */
    void cancel();
}
