package org.itzheng.ring.ring.list;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;

import org.itzheng.ring.bean.RingItem;

import java.util.ArrayList;
import java.util.List;

import static android.media.RingtoneManager.ID_COLUMN_INDEX;
import static android.media.RingtoneManager.URI_COLUMN_INDEX;

/**
 * 系统铃声列表
 */
public class SystemRingtone implements IRingtone {
    private RingtoneManager mRingtoneManager;

    /**
     * 获取铃声列表
     *
     * @param context 上下文，一般获取铃音都需要用到上下文参数
     * @param type    RingtoneManager.TYPE_ALL = TYPE_RINGTONE | TYPE_NOTIFICATION | TYPE_ALARM
     * @return
     */
    @Override
    public List<RingItem> getRingtone(Context context, int type) {
        if (mRingtoneManager == null) {
            mRingtoneManager = new RingtoneManager(context);
        }
        /* 指定获取类型为短信铃声 */
        mRingtoneManager.setType(type);
        /* 创建游标 */
        Cursor cursor = mRingtoneManager.getCursor();
        int count = cursor.getCount();
        List<RingItem> ringItems = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Ringtone ringtone = mRingtoneManager.getRingtone(i);
            RingItem ringItem = new RingItem();
            ringItem.resId = i;
            ringItem.title = ringtone.getTitle(context);
            ringItem.uri = mRingtoneManager.getRingtoneUri(i);
            ringItem.path=uriToPath(context,ringItem.uri);
            ringItems.add(ringItem);
        }
        //下面的方法没法正确获取到数据
        /* 游标移动到第一位，如果有下一项，则添加到ringlist中 */
//        if (cursor.moveToFirst()) {
//            do { // 游标获取RingtoneManager的列inde x
//                RingItem ringItem = new RingItem();
//                ringItem.title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
//                ringItem.uri = getUriFromCursor(cursor);
////                ringItems.add(ringItem);
//            } while (cursor.moveToNext());
//        }
        return ringItems;
    }

    private static Uri getUriFromCursor(Cursor cursor) {
        return ContentUris.withAppendedId(Uri.parse(cursor.getString(URI_COLUMN_INDEX)), cursor
                .getLong(ID_COLUMN_INDEX));
    }

    private String uriToPath(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver()
                .query(uri, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            try {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                return path;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return null;
    }
}
