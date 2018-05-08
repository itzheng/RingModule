package org.itzheng.ring.ring.impl;

import android.content.Context;
import android.media.MediaPlayer;

import org.itzheng.ring.R;
import org.itzheng.ring.ring.BaseRing;
import org.itzheng.ring.bean.RingItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-5-8.
 */
public class RawRing extends BaseRing {
    private Context mContext;

    public RawRing(Context context) {
        mContext = context;
    }

    @Override
    public List<RingItem> getRingList() {
        List<RingItem> items = new ArrayList<>();
        List<Integer> resIds = getResIds();
        List<String> titles = getTitles();
        for (int i = 0; i < resIds.size(); i++) {
            RingItem item = new RingItem();
            item.resId = resIds.get(i);
            //避免获取不到对应的标题
            item.title = (titles != null && titles.size() > i) ? titles.get(i) : "";
            items.add(item);
        }
        return items;
    }

    private List<Integer> mResId;

    protected List<Integer> getResIds() {
        if (mResId == null) {
            mResId = new ArrayList<>();
            mResId.add(R.raw.jinggao);
        }
        return mResId;
    }

    /**
     * 获取资源文件对应的标题，这个需要手动设置
     * 可重写，根据当前语言获取对应的文字
     *
     * @return
     */
    protected List<String> getTitles() {
        List<String> titles = new ArrayList<>();

        return titles;
    }

    @Override
    public void playRing(int index) {
        release();
        mMediaPlayer = MediaPlayer.create(mContext, getResIds().get(index));
        initMediaPlayer();
        mMediaPlayer.start();
    }
}
