package com.example.expo.blogapp.Activities.Modal;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * CustomDurationScroller
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-3-2
 */
public class DurationScroller extends Scroller {

    private double scrollFactor = 1;

    public DurationScroller(Context context) {
        super(context);
    }

    public DurationScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public void setScrollDurationFactor(double scrollFactor) {
        this.scrollFactor = scrollFactor;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (int)(duration * scrollFactor));
    }
}