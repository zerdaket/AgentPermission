package com.zerdaket.agent.permission.source;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

/**
 * Created by zerdaket on 2019-11-20.
 */
public class ActivitySource extends Source {

    private FragmentActivity mActivity;

    public ActivitySource(FragmentActivity activity) {
        mActivity = activity;
    }

    @Override
    public FragmentManager getFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    @Override
    public String getTag() {
        return mActivity.toString();
    }

    @Override
    public Lifecycle getLifecycle() {
        return mActivity.getLifecycle();
    }
}
