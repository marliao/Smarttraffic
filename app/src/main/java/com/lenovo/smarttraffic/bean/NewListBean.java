package com.lenovo.smarttraffic.bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.util.HttpUtil;

import api.Update;

public class NewListBean {
    /**
     * id : 1
     * category : 1
     * title : 福州地铁2号线本月下旬试乘运营
     * content : 福州地铁2号线本月下旬试乘运营，南门兜站施工正在全力冲刺；设8个进出口，换乘跑离约百米
     * createtime : 2019-04-10 08:19:21
     */

    private int id;
    private int category;
    private String title;
    private String content;
    private String createtime;
    private String img;
    private Bitmap image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void init(Update update) {
        if (!TextUtils.isEmpty(this.img)) {
            HttpUtil.getImage(this.img, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    NewListBean.this.image = bitmap;
                    update.refresh();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    NewListBean.this.image = BitmapFactory.decodeResource(App.getContext().getResources(), R.mipmap.loading);
                    update.refresh();
                }
            });
        }
    }

    @Override
    public String toString() {
        return "NewListBean{" +
                "id=" + id +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}