package nl.vogelbescherming.wadvogels.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;


public class HackyScrollView extends ScrollView {

    private boolean enabled;
    private ViewPager viewPager;

    public HackyScrollView(Context context) {
        super(context);
    }

    public HackyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setViewPager(ViewPager pager){
        viewPager = pager;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            try {
                return super.onTouchEvent(event);
            } catch (Exception e) {
                event.setAction(MotionEvent.ACTION_CANCEL);
                try {
                    return super.onTouchEvent(event);
                } catch (Exception e2) {
                    return false;
                }
            }

        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean result = true;
        try {

            if (viewPager != null) {

                Rect rect = new Rect();

                if(viewPager != null){

                    int scrcoords[] = new int[2];
                    View w = viewPager;
                    w.getLocationOnScreen(scrcoords);
                    float x = event.getRawX() + w.getLeft() - scrcoords[0];
                    float y = event.getRawY() + w.getTop() - scrcoords[1];
                    if (!(x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) {
                        setScrollingEnabled(false);
                        result = false;
                    } else {
                        setScrollingEnabled(true);
                    }

                }

            } else return super.onInterceptTouchEvent(event);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

        return result;

    }

}