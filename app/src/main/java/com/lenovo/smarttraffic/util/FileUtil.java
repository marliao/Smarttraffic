package com.lenovo.smarttraffic.util;

import com.google.gson.Gson;
import com.lenovo.smarttraffic.App;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {
    public static Object getFileCalss(String fileName, Class clazz) {
        Object object = null;
        StringBuffer sb = new StringBuffer();
        try {
            InputStream open = App.getContext().getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(open));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            object = new Gson().fromJson(sb.toString(), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
