package com.me.ilya.smartalarmclock.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.ilya.smartalarmclock.BuildConfig;
import com.me.ilya.smartalarmclock.R;
import com.me.ilya.smartalarmclock.Titleable;

/**
 * Created by Ilya on 6/15/2016.
 */
public class AboutFragment extends Fragment implements Titleable {
    public static AboutFragment create() {
        return new AboutFragment();
    }
    TextView versionTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String s = String.format(getString(R.string.about_text_3), BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        versionTextView.setText(s);
    }
    @Override
    public String getTitle(Context context) {
        return getString(R.string.about_title);
    }
}
