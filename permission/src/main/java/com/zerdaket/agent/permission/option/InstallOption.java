package com.zerdaket.agent.permission.option;

import com.zerdaket.agent.permission.listener.Rationale;
import com.zerdaket.agent.permission.listener.Result;
import com.zerdaket.agent.permission.source.Source;

/**
 * Created by zerdaket on 2019-11-21.
 */
public class InstallOption extends Option {

    private Result<Void> mGrantedResult;
    private Result<Void> mDeniedResult;
    private Rationale<Void> mRationale;

    InstallOption(Source source) {
        super(source);
    }

    public InstallOption onGranted(Result<Void> result) {
        mGrantedResult = result;
        return this;
    }

    public InstallOption onDenied(Result<Void> result) {
        mDeniedResult = result;
        return this;
    }

    public InstallOption rationale(Rationale<Void> rationale) {
        mRationale = rationale;
        return this;
    }

    public void start() {
        mRequestFragment.getInstallResultObserver().setGrantedResult(mGrantedResult);
        mRequestFragment.getInstallResultObserver().setDeniedResult(mDeniedResult);
        mRequestFragment.getInstallResultObserver().setRationale(mRationale);
        mSource.getLifecycle().addObserver(mRequestFragment.getInstallResultObserver());
        mRequestFragment.checkInstall();
    }

}
