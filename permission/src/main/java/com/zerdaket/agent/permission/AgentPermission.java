package com.zerdaket.agent.permission;

import android.content.Context;
import android.net.Uri;
import android.os.Build;

import com.zerdaket.agent.permission.option.Boot;
import com.zerdaket.agent.permission.source.ActivitySource;
import com.zerdaket.agent.permission.source.XFragmentSource;

import java.io.File;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by zerdaket on 2019-11-19.
 */
public class AgentPermission {

    private AgentPermission() {}

    public static Boot with(FragmentActivity activity) {
        ActivitySource source = new ActivitySource(activity);
        return new Boot(source);
    }

    public static Boot with(Fragment fragment) {
        XFragmentSource source = new XFragmentSource(fragment);
        return new Boot(source);
    }

    public static Uri getFileUri(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
        }
        return Uri.fromFile(file);
    }

}
