package com.zerdaket.agent.permission.option;

import com.zerdaket.agent.permission.source.Source;

/**
 * Created by zerdaket on 2019-11-21.
 */
public class Boot {

    private Source mSource;

    public Boot(Source source) {
        mSource = source;
    }

    public RuntimeOption runtime() {
        return new RuntimeOption(mSource);
    }

    public InstallOption install() {
        return new InstallOption(mSource);
    }

}
