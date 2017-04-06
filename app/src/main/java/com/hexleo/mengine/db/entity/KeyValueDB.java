package com.hexleo.mengine.db.entity;

import com.orm.SugarRecord;

/**
 * Created by hexleo on 2017/4/6.
 */

public class KeyValueDB extends SugarRecord {
    public String key;
    public String value;

    public KeyValueDB() {
    }

    public KeyValueDB(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
