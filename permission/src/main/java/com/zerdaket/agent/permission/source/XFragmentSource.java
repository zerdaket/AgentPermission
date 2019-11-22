package com.zerdaket.agent.permission.source;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

/**
 * Created by zerdaket on 2019-11-20.
 */
public class XFragmentSource extends Source {

    private Fragment mFragment;

    public XFragmentSource(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public FragmentManager getFragmentManager() {
        return mFragment.getChildFragmentManager();
    }

    @Override
    public String getTag() {
        return mFragment.toString();
    }

    @Override
    public Lifecycle getLifecycle() {
        return mFragment.getLifecycle();
    }
}
