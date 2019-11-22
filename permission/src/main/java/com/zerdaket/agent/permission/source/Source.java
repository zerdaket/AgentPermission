package com.zerdaket.agent.permission.source;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

/**
 * Created by zerdaket on 2019-11-20.
 */
public abstract class Source {

    public abstract FragmentManager getFragmentManager();

    public abstract String getTag();

    public abstract Lifecycle getLifecycle();

}
