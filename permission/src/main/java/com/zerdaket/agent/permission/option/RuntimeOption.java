package com.zerdaket.agent.permission.option;

import com.zerdaket.agent.permission.listener.Rationale;
import com.zerdaket.agent.permission.listener.Result;
import com.zerdaket.agent.permission.source.Source;

import java.util.List;

/**
 * Created by zerdaket on 2019-11-20.
 */
public class RuntimeOption extends Option {

    private Result<List<String>> mGrantedResult;
    private Result<List<String>> mDeniedResult;
    private Rationale<List<String>> mRationale;
    private String[] mPermissions;

    public RuntimeOption(Source source) {
        super(source);
    }

    public RuntimeOption permission(String... permissions) {
        mPermissions = permissions;
        return this;
    }

    public RuntimeOption onGranted(Result<List<String>> result) {
        mGrantedResult = result;
        return this;
    }

    public RuntimeOption onDenied(Result<List<String>> result) {
        mDeniedResult = result;
        return this;
    }

    public RuntimeOption rationale(Rationale<List<String>> rationale) {
        mRationale = rationale;
        return this;
    }

    public void start() {
        if (mPermissions == null || mPermissions.length == 0) {
            throw new NullPointerException("Permission should be set at least one");
        }
        mRequestFragment.getResultObserver().setGrantedResult(mGrantedResult);
        mRequestFragment.getResultObserver().setDeniedResult(mDeniedResult);
        mRequestFragment.getResultObserver().setRationale(mRationale);
        mSource.getLifecycle().addObserver(mRequestFragment.getResultObserver());
        mRequestFragment.checkPermissions(mPermissions);
    }

}
