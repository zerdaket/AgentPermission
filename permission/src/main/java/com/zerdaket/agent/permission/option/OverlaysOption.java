package com.zerdaket.agent.permission.option;

import com.zerdaket.agent.permission.listener.Rationale;
import com.zerdaket.agent.permission.listener.Result;
import com.zerdaket.agent.permission.source.Source;

/**
 * Created by zerdaket on 2019-11-30.
 */
public class OverlaysOption extends Option {

    private Result<Void> mGrantedResult;
    private Result<Void> mDeniedResult;
    private Rationale<Void> mRationale;

    OverlaysOption(Source source) {
        super(source);
    }

    public OverlaysOption onGranted(Result<Void> result) {
        mGrantedResult = result;
        return this;
    }

    public OverlaysOption onDenied(Result<Void> result) {
        mDeniedResult = result;
        return this;
    }

    public OverlaysOption rationale(Rationale<Void> rationale) {
        mRationale = rationale;
        return this;
    }

    public void start() {
        mRequestFragment.getOverlaysResultObserver().setGrantedResult(mGrantedResult);
        mRequestFragment.getOverlaysResultObserver().setDeniedResult(mDeniedResult);
        mRequestFragment.getOverlaysResultObserver().setRationale(mRationale);
        mSource.getLifecycle().addObserver(mRequestFragment.getOverlaysResultObserver());
        mRequestFragment.checkOverlays();
    }

}
