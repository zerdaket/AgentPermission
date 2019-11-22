package com.zerdaket.agent.permission.request;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.zerdaket.agent.permission.listener.ResultObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * Created by zerdaket on 2019-11-20.
 */
public class RequestFragment extends Fragment {

    private final int REQUEST_NORMAL_PERMISSION_CODE = 200;
    private final int REQUEST_INSTALL_PERMISSION_CODE = 210;

    private final int REQUEST_INSTALL_CODE = 300;

    private ResultObserver<List<String>> mResultObserver = new ResultObserver<>();
    private ResultObserver<Void> mInstallResultObserver = new ResultObserver<>();
    private ArrayList<String> mGrantedPermissions = new ArrayList<>();
    private ArrayList<String> mDeniedPermissions = new ArrayList<>();
    private ArrayList<String> mRationalePermissions = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public ResultObserver<List<String>> getResultObserver() {
        return mResultObserver;
    }

    public ResultObserver<Void> getInstallResultObserver() {
        return mInstallResultObserver;
    }

    public void checkPermissions(String[] permissions) {
        if (hasPermissions(permissions)) {
            // 已经获得权限
            if (mResultObserver.getGrantedResult() == null) return;
            mResultObserver.getGrantedResult().onResult(Arrays.asList(permissions));
        } else {
            // 没有获得权限
            if (shouldShowRationale(permissions)) {
                // 是否展示说明
                if (mResultObserver.getRationale() == null) {
                    // 不展示，直接申请权限
                    request(permissions);
                } else {
                    // 展示
                    mResultObserver.getRationale()
                            .showRationale(mRationalePermissions, new NormalRequester(this, permissions));
                }
            } else {
                // 申请权限
                request(permissions);
            }
        }
    }

    private boolean hasPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;

        boolean result = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_DENIED) {
                result = false;
            }
        }
        return result;
    }

    private boolean shouldShowRationale(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false;

        mRationalePermissions.clear();
        boolean result = false;
        for (String permission : permissions) {
            if (shouldShowRequestPermissionRationale(permission)) {
                mRationalePermissions.add(permission);
                result = true;
            }
        }
        return result;
    }

    void request(String[] permissions) {
        requestPermissions(permissions, REQUEST_NORMAL_PERMISSION_CODE);
    }

    public void checkInstall() {
        if (canInstall()) {
            if (mInstallResultObserver.getGrantedResult() == null) return;
            mInstallResultObserver.getGrantedResult().onResult(null);
        } else {
            if (mInstallResultObserver.getRationale() == null) {
                installRequest();
            } else {
                mInstallResultObserver.getRationale().showRationale(null, new InstallRequester(this));
            }
        }
    }

    private boolean canInstall() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return true;
        if (getContext() == null) return false;
        return getContext().getPackageManager().canRequestPackageInstalls();
    }

    void installRequest() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        requestPermissions(new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, REQUEST_INSTALL_PERMISSION_CODE);
    }

    private void installIntent() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;
        Uri uri = Uri.parse("package:" + getContext().getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri);
        startActivityForResult(intent, REQUEST_INSTALL_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length == 0) return;
        if (requestCode == REQUEST_NORMAL_PERMISSION_CODE) {
            mGrantedPermissions.clear();
            mDeniedPermissions.clear();
            for (int index = 0; index < grantResults.length; index++) {
                int grantResult = grantResults[index];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    mGrantedPermissions.add(permissions[index]);
                } else {
                    mDeniedPermissions.add(permissions[index]);
                }
            }
            if (mResultObserver.getGrantedResult() != null && mDeniedPermissions.isEmpty()) {
                mResultObserver.getGrantedResult().onResult(mGrantedPermissions);
                return;
            }
            if (mResultObserver.getDeniedResult() != null) {
                mResultObserver.getDeniedResult().onResult(mDeniedPermissions);
            }
        } else if (requestCode == REQUEST_INSTALL_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mInstallResultObserver.getGrantedResult().onResult(null);
            } else {
                installIntent();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_INSTALL_CODE) return;
        if (canInstall()) {
            if (mInstallResultObserver.getGrantedResult() == null) return;
            mInstallResultObserver.getGrantedResult().onResult(null);
        } else {
            if (mInstallResultObserver.getDeniedResult() == null) return;
            mInstallResultObserver.getDeniedResult().onResult(null);
        }
    }

}
