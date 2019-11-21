package com.lenovo.smarttraffic.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.lenovo.smarttraffic.ui.customerActivity.AddLabelActivity;

import api.Status;

public class DraggedGridView extends GridView {
    private Status status;
    private boolean isViewDragged = false;
    private int prePosition = AdapterView.INVALID_POSITION;
    private ImageView imageView;
    private WindowManager.LayoutParams layoutParams;
    private WindowManager manager;
    private float downX;
    private float downY;

    public DraggedGridView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                status.getStatus(true);
                prePosition = position;
                view.destroyDrawingCache();
                view.setDrawingCacheEnabled(true);
                view.buildDrawingCache();
                Bitmap bitmap = view.getDrawingCache();
                layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
                layoutParams.width = bitmap.getWidth();
                layoutParams.height = bitmap.getHeight();
                layoutParams.x = (int) (downX - layoutParams.width / 2);
                layoutParams.y = (int) (downY - layoutParams.height / 2);
                layoutParams.format = PixelFormat.TRANSLUCENT;
                layoutParams.windowAnimations = 0;
                if ((int) imageView.getTag() == 1) {
                    manager.removeView(view);
                    imageView.setTag(0);
                }
                imageView.setImageBitmap(bitmap);
                manager.addView(imageView, layoutParams);
                ((AddLabelActivity.GridViewAdapter) getAdapter()).hideView(position);
                imageView.setTag(1);
                // TODO: 2019/6/2  
                isViewDragged = true;
                return false;
            }
        });
        imageView = new ImageView(getContext());
        imageView.setTag(0);
        layoutParams = new WindowManager.LayoutParams();
        manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getRawX();
                downY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isViewDragged) {
                    layoutParams.x = (int) (ev.getRawX() - layoutParams.width / 2);
                    layoutParams.y = (int) (ev.getRawY() - layoutParams.height / 2);
                    manager.updateViewLayout(imageView, layoutParams);
                    int currentPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
                    if (currentPosition != AdapterView.INVALID_POSITION && currentPosition != prePosition) {
                        ((AddLabelActivity.GridViewAdapter) getAdapter()).swapView(prePosition, currentPosition);
                        prePosition = currentPosition;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                ((AddLabelActivity.GridViewAdapter) getAdapter()).showHideView();
                if ((int) imageView.getTag() == 1) {
                    manager.removeView(imageView);
                    imageView.setTag(0);
                }
                isViewDragged = false;
                break;
        }
        return super.onTouchEvent(ev);
    }

    public DraggedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DraggedGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
