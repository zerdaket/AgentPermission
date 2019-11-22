package com.zerdaket.agent.permission.listener;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by zerdaket on 2019-11-20.
 */
public class ResultObserver<T> implements LifecycleEventObserver {

    private Result<T> mGrantedResult;
    private Result<T> mDeniedResult;
    private Rationale<T> mRationale;

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (source.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            source.getLifecycle().removeObserver(this);
            mGrantedResult = null;
            mDeniedResult = null;
            mRationale = null;
        }
    }

    public void setGrantedResult(Result<T> grantedResult) {
        mGrantedResult = grantedResult;
    }

    public void setDeniedResult(Result<T> deniedResult) {
        mDeniedResult = deniedResult;
    }

    public void setRationale(Rationale<T> rationale) {
        mRationale = rationale;
    }

    public Result<T> getGrantedResult() {
        return mGrantedResult;
    }

    public Result<T> getDeniedResult() {
        return mDeniedResult;
    }

    public Rationale<T> getRationale() {
        return mRationale;
    }
}
