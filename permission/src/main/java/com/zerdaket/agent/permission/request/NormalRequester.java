package com.zerdaket.agent.permission.request;

/**
 * Created by zerdaket on 2019-11-21.
 */
public class NormalRequester implements Requester {

    private RequestFragment mRequestFragment;
    private String[] mPermissions;

    NormalRequester(RequestFragment requestFragment, String[] permissions) {
        mRequestFragment = requestFragment;
        mPermissions = permissions;
    }

    @Override
    public void execute() {
        mRequestFragment.request(mPermissions);
    }

}
