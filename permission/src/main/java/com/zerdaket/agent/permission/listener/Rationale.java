package com.zerdaket.agent.permission.listener;

import com.zerdaket.agent.permission.request.Requester;

import androidx.annotation.NonNull;

/**
 * Created by zerdaket on 2019-11-20.
 */
public interface Rationale<T> {

    void showRationale(T data, @NonNull final Requester requester);

}
