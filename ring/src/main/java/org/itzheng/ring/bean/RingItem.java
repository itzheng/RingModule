package org.itzheng.ring.bean;

import android.net.Uri;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-5-7.
 */
public class RingItem {
    /**
     * 资源文件
     */
    public int resId;
    /**
     * 文件标题
     */
    public String title;
    /**
     * uri 位置
     */
    public Uri uri;
    /**
     * 路径，用于缓存
     * Uri.parse(path)
     */
    public String path;
    /**
     * 如果是Assets的资源文件不能简单使用URI解析
     */
    public boolean isAssets=false;
}
