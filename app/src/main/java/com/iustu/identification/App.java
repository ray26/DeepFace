package com.iustu.identification;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePalApplication;

/**
 * Created by Liu Yuchuan on 2017/11/4.
 */

public class App extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
//        CrashReport.initCrashReport(getApplicationContext());
        Bugly.init(getApplicationContext(), "9c3bdbe293", false );
    }
}