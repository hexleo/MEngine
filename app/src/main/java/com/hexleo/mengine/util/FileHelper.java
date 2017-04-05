package com.hexleo.mengine.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

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
        StringBuilder fileContext = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().getAssets().open(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while((line = reader.readLine()) != null){
                fileContext.append(line);
                fileContext.append("\n");
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContext.toString();
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

}
