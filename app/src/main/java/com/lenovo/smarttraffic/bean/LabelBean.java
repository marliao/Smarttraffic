package com.lenovo.smarttraffic.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.ObjectOutput;

@DatabaseTable(tableName = "label")
public class LabelBean {
    @DatabaseField(generatedId = true, columnName = "_id")
    private int _id;
    @DatabaseField(columnName = "newsId")
    private int newsId;
    @DatabaseField(columnName = "labelName")
    private String labelName;
    @DatabaseField(columnName = "isSelect")
    private boolean isSelect;
    private boolean isTop;

    public LabelBean() {
    }

    public LabelBean(int newsId, String labelName, boolean isSelect, boolean isTop) {
        this.newsId = newsId;
        this.labelName = labelName;
        this.isSelect = isSelect;
        this.isTop = isTop;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    @Override
    public String toString() {
        return "LabelBean{" +
                "_id=" + _id +
                ", newsId=" + newsId +
                ", labelName='" + labelName + '\'' +
                ", isSelect=" + isSelect +
                ", isTop=" + isTop +
                '}';
    }
}