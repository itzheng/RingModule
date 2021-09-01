package org.itzheng.ring.ring.list;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import org.itzheng.ring.R;
import org.itzheng.ring.bean.RingItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取Assets目录下的铃音
 */
public class AssetRingtone implements IRingtone {

    /**
     * 在Asset 目录下，新建对应的文件，如果不想根据文件夹去区分，type 输入 其他数值
     *
     * @param context 上下文，一般获取铃音都需要用到上下文参数
     * @param type    RingtoneManager.TYPE_ALL = TYPE_RINGTONE | TYPE_NOTIFICATION | TYPE_ALARM
     * @return
     */
    @Override
    public List<RingItem> getRingtone(Context context, int type) {
        List<RingItem> items = new ArrayList<>();
        boolean isTrue = false;
        if ((type & RingtoneManager.TYPE_RINGTONE) != 0) {
            isTrue = true;
            getRingtonesByDir(context, items, "ringtones");
        }
        if ((type & RingtoneManager.TYPE_NOTIFICATION) != 0) {
            isTrue = true;
            getRingtonesByDir(context, items, "notifications");
        }
        if ((type & RingtoneManager.TYPE_ALARM) != 0) {
            isTrue = true;
            getRingtonesByDir(context, items, "alarms");
        }
        if (!isTrue) {
            //都没有的话，那么选择所有目录
            getRingtonesByDir(context, items, "");
        }
        return items;
    }

    /**
     * 获取文件夹下的文件
     *
     * @param context
     * @param items
     * @param dir
     */
    private void getRingtonesByDir(Context context, List<RingItem> items, String dir) {
        try {
            //默认子文件夹 images webkit
            AssetManager am = context.getAssets();
            String[] files = am.list(dir);
            if (files.length == 0) {
                return;
            }
            for (int i = 0; i < files.length; i++) {
                String filepath = dir + File.separator + files[i];
                try {
                    AssetFileDescriptor afd = am.openFd(filepath);
                    //能打开就是文件,这里没有夹是否是音频文件判断
                    RingItem item = new RingItem();
                    item.path = filepath;
                    item.isAssets = true;
                    item.title = files[i];
                    items.add(item);
                    afd.close();
                } catch (FileNotFoundException e) {
                    //文件没找到，说明是文件夹,递归下去
                    getRingtonesByDir(context, items, filepath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
