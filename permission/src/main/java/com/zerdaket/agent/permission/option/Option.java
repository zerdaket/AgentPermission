package com.zerdaket.agent.permission.option;

import com.zerdaket.agent.permission.request.RequestFragment;
import com.zerdaket.agent.permission.source.Source;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Created by zerdaket on 2019-11-21.
 */
public class Option {

    protected Source mSource;
    protected RequestFragment mRequestFragment;

    Option(Source source) {
        mSource = source;
        mRequestFragment = getRequestFragment();
    }

    private RequestFragment getRequestFragment() {
        RequestFragment fragment = findRequestFragment();
        if (fragment != null) {
            return fragment;
        }
        fragment = createRequestFragment();
        return fragment;
    }

    private RequestFragment createRequestFragment() {
        RequestFragment fragment = new RequestFragment();
        mSource.getFragmentManager()
                .beginTransaction()
                .add(fragment, mSource.getTag())
                .commitNowAllowingStateLoss();
        return fragment;
    }

    private RequestFragment findRequestFragment() {
        FragmentManager fragmentManager = mSource.getFragmentManager();
        String tag = mSource.getTag();
        if (fragmentManager.isDestroyed()) {
            throw new IllegalStateException("Can't access RequestFragment from onDestroy");
        }

        Fragment fragmentByTag = fragmentManager.findFragmentByTag(tag);
        if (fragmentByTag != null && !(fragmentByTag instanceof RequestFragment)) {
            throw new IllegalStateException("Unexpected "
                    + "fragment instance was returned by " + tag);
        }
        return (RequestFragment) fragmentByTag;
    }

}
