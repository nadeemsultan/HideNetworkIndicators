package com.nadeemsultan.hidenetworkindicators;

import android.content.res.XModuleResources;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

public class Main implements IXposedHookZygoteInit, IXposedHookInitPackageResources {
    private static String MODULE_PATH = null;

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }

    @Override
    public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.android.systemui"))
            return;

        XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
        // Hide Wifi
        resparam.res.setReplacement("com.android.systemui", "drawable", "stat_sys_wifi_in", modRes.fwd(R.drawable.wifi_activity));
        resparam.res.setReplacement("com.android.systemui", "drawable", "stat_sys_wifi_inout", modRes.fwd(R.drawable.wifi_activity));
        resparam.res.setReplacement("com.android.systemui", "drawable", "stat_sys_wifi_out", modRes.fwd(R.drawable.wifi_activity));

        //Hide Mobile Data
        resparam.res.setReplacement("com.android.systemui", "drawable", "stat_sys_signal_in", modRes.fwd(R.drawable.wifi_activity));
        resparam.res.setReplacement("com.android.systemui", "drawable", "stat_sys_signal_inout", modRes.fwd(R.drawable.wifi_activity));
        resparam.res.setReplacement("com.android.systemui", "drawable", "stat_sys_signal_out", modRes.fwd(R.drawable.wifi_activity));
        resparam.res.setReplacement("com.android.systemui", "drawable", "stat_sys_signal_noinout", modRes.fwd(R.drawable.wifi_activity));
    }
}
