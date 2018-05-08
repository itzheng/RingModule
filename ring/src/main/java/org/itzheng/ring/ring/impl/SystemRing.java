package org.itzheng.ring.ring.impl;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;

import org.itzheng.ring.ring.BaseRing;
import org.itzheng.ring.bean.RingItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-5-7.
 */
public class SystemRing extends BaseRing {
    private Context mContext;
    private static final String TAG = "SystemRing";
    private RingtoneManager mRingtoneManager;
    private ArrayList ringItems;

    public SystemRing(Context context) {
        mContext = context;
        getRingList();
    }

    @Override
    public List<RingItem> getRingList() {
        initRingtoneManager();
        initItems();
        return ringItems;
    }

    private void initItems() {
        if (ringItems == null) {
            ringItems = new ArrayList<>();
        /* 创建游标 */
            Cursor cursor = mRingtoneManager.getCursor();
        /* 游标移动到第一位，如果有下一项，则添加到ringlist中 */
            if (cursor.moveToFirst()) {
                do { // 游标获取RingtoneManager的列inde x
                    RingItem ringItem = new RingItem();
                    ringItem.title = cursor
                            .getString(RingtoneManager.TITLE_COLUMN_INDEX);
                    ringItems.add(ringItem);
                } while (cursor.moveToNext());
            }
        }
    }

    protected void initRingtoneManager() {
        if (mRingtoneManager == null) {
            mRingtoneManager = new RingtoneManager(mContext);
        /* 指定获取类型为短信铃声 */
            mRingtoneManager.setType(RingtoneManager.TYPE_NOTIFICATION);
        }
    }

    @Override
    public void playRing(int index) {
        release();
        mMediaPlayer = MediaPlayer.create(mContext, mRingtoneManager.getRingtoneUri(index));
        initMediaPlayer();
        mMediaPlayer.start();
    }
}
