package com.me.ilya.smartalarmclock.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;

/**
 * Created by Ilya on 6/27/2016.
 */
public class OnButton extends ImageView implements Checkable{
    private boolean mChecked;

    private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };

    public OnButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int[] onCreateDrawableState(final int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked())
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        return drawableState;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void setChecked(final boolean checked) {
        if (mChecked == checked)
            return;
        mChecked = checked;
        refreshDrawableState();
    }

}
