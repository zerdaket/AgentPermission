package com.zerdaket.agent.permission.request;

/**
 * Created by zerdaket on 2019-11-22.
 */
public class InstallRequester implements Requester {

    private RequestFragment mRequestFragment;

    public InstallRequester(RequestFragment requestFragment) {
        mRequestFragment = requestFragment;
    }

    @Override
    public void execute() {
        mRequestFragment.installRequest();
    }
}
