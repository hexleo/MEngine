package com.hexleo.mengine.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.hexleo.mengine.application.BaseApplication;
import com.hexleo.mengine.engine.constant.MeConstant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hexleo on 2017/1/18.
 */

public class FileHelper {

    private static String sExtFilesDir;

    public static String getExtFilesDir() {
        if (TextUtils.isEmpty(sExtFilesDir)) {
            try {
                File file = BaseApplication.getBaseApplication().getExternalFilesDir(null);
                if (file == null) {
                    file = BaseApplication.getBaseApplication().getFilesDir();
                }
                sExtFilesDir = file.getAbsolutePath();
            } catch (Exception e) {
                File file = BaseApplication.getBaseApplication().getFilesDir();
                sExtFilesDir = file.getAbsolutePath();
            }
        }
        return sExtFilesDir;
    }

    public static String getIndexPath(String name) {
        return MeConstant.ASSET_FILE_URI_PREFIX + name + File.separator + MeConstant.INDEX_HTML;
    }

    /**
     * 获取app.js内容
     * @param name
     * @param context
     * @return
     */
    public static String getAppJs(String name, Context context) {
        return getAssetFileContext(name + File.separator + MeConstant.APP_JS, context);
    }


    public static String getAssetFileContext(String filePath, Context context) {
        if (context == null) {
            return null;
        }
        try {
            return getFileContent(context.getResources().getAssets().open(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Drawable getAssetResDrawable(String resPath, Context context) {
        Drawable drawable = null;
        try {
            InputStream inputStream = context.getResources().getAssets().open(resPath);
            drawable = Drawable.createFromStream(inputStream, null);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }

    public static String getResRawFileContext(int fileResId, Context context) {
        if (context == null) {
            return null;
        }
        return getFileContent(context.getResources().openRawResource(fileResId));
    }

    private static String getFileContent(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        StringBuilder fileContext = new StringBuilder();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while((line = reader.readLine()) != null){
                fileContext.append(line);
                fileContext.append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileContext.toString();
    }

}
