package org.itzheng.ring.ring.list;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import org.itzheng.ring.R;
import org.itzheng.ring.bean.RingItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Raw 只对于当前的model有用，灵活度不高
 */
public class ResRawRingtone implements IRingtone {
    @Override
    public List<RingItem> getRingtone(Context context, int type) {
        List<RingItem> items = new ArrayList<>();
        Field[] fields = R.raw.class.getFields();
        for (Field f : fields)
            try {
                RingItem item = new RingItem();
                item.resId = f.getInt(null);
                item.title = f.getName();
                item.path = "android.resource://" + context.getPackageName() + "/" + item.resId;
                item.uri = Uri.parse(item.path);
                items.add(item);
            } catch (IllegalArgumentException e) {

            } catch (IllegalAccessException e) {

            }
        return items;
    }
}
