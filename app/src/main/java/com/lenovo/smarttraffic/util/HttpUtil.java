package com.lenovo.smarttraffic.util;

import android.graphics.Bitmap;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lenovo.smarttraffic.App;

import org.json.JSONObject;

import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class HttpUtil {
    public static void doPost(String path, Map map, Response.Listener<JSONObject> listener) {
        String url = "http://192.168.1.104:8088/transportservice/action/" + path + ".do";
        map.put("UserName", SpUtil.getString(SpUtil.USERNAME, "user1"));
        JSONObject jsonObject = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(POST, url, jsonObject, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        App.getQueue().add(jsonObjectRequest);
    }

    public static void getImage(String path, Response.Listener<Bitmap> listener, Response.ErrorListener errorListener) {
        String url = "http://192.168.1.104:8088/transportservice" + path;
        ImageRequest imageRequest = new ImageRequest(url, listener, 0, 0, Bitmap.Config.RGB_565, errorListener);
        App.getQueue().add(imageRequest);
    }
}
