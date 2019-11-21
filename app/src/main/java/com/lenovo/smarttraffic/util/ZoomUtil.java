package com.lenovo.smarttraffic.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class ZoomUtil implements View.OnTouchListener {

    private int model;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        try {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    model = 0;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    model = 2;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (model == 2) {
                        float x = event.getX(0);
                        float x1 = event.getX(1);
                        float y = event.getY(0);
                        float y1 = event.getY(1);
                        double sqrt = Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
                        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                        layoutParams.width = (int) sqrt;
                        layoutParams.height = (int) sqrt;
                        v.setLayoutParams(layoutParams);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
