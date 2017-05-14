package com.hexleo.mengine.db;

import android.text.TextUtils;

import com.hexleo.mengine.db.entity.KeyValueDB;

import java.util.List;

/**
 * Created by hexleo on 2017/4/6.
 */

public class DBManager {
    private static DBManager sInstance;

    private DBManager() {

    }

    public static DBManager getInstance() {
        if (sInstance == null) {
            synchronized (DBManager.class) {
                if (sInstance == null) {
                    sInstance = new DBManager();
                }
            }
        }
        return sInstance;
    }

    public void put(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        List<KeyValueDB> kv = KeyValueDB.find(KeyValueDB.class, "key=?", key);
        if (kv != null && kv.size() > 0) {
            kv.get(0).value = value;
            kv.get(0).save();
        } else {
            KeyValueDB kvEntity = new KeyValueDB(key, value);
            kvEntity.save();
        }
    }

    public String get(String key) {
        List<KeyValueDB> kv = KeyValueDB.find(KeyValueDB.class, "key=?", key);
        if (kv != null && kv.size() > 0) {
            return kv.get(0).value;
        }
        return null;
    }

}
