package com.me.ilya.smartalarmclock;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.me.ilya.smartalarmclock.R;

/**
 * Created by Ilya on 6/24/2016.
 */
public class DaySwitcher  extends ImageView implements Checkable, View.OnClickListener {
    private boolean mChecked;

    private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };

    public DaySwitcher(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        toggle();
    }
}