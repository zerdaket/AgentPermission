package com.zerdaket.agentpermission.bean;

import android.Manifest;

/**
 * Created by zerdaket on 2019-11-19.
 */
public enum PermissionEnum implements EnumBean {

    WRITE_EXTERNAL_STORAGE("WRITE_EXTERNAL_STORAGE", Manifest.permission.WRITE_EXTERNAL_STORAGE),
    CAMERA("CAMERA", Manifest.permission.CAMERA),
    RECORD_AUDIO("RECORD_AUDIO", Manifest.permission.RECORD_AUDIO),
    ACCESS_FINE_LOCATION("ACCESS_FINE_LOCATION", Manifest.permission.ACCESS_FINE_LOCATION);

    private String name;

    private String code;

    PermissionEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }
}
