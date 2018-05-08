package org.itzheng.ring.ring.impl;

import android.content.Context;

import org.itzheng.ring.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Title:<br>
 * Description: <br>
 *
 * @email ItZheng@ZoHo.com
 * Created by itzheng on 2018-5-8.
 */
public class MyRawRing extends RawRing {
    public MyRawRing(Context context) {
        super(context);
    }

    private List<Integer> mResIds;

    @Override
    protected List<Integer> getResIds() {
        if (mResIds == null) {
            mResIds = new ArrayList<>();
            mResIds.add(R.raw.xiaomi);
        }
        return mResIds;
    }
}
