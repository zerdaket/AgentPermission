package com.zerdaket.agent.permission.request;

/**
 * Created by zerdaket on 2019-11-30.
 */
public class OverlaysRequester implements Requester {

    private RequestFragment mRequestFragment;

    OverlaysRequester(RequestFragment requestFragment) {
        mRequestFragment = requestFragment;
    }

    @Override
    public void execute() {
        mRequestFragment.overlaysIntent();
    }

}
