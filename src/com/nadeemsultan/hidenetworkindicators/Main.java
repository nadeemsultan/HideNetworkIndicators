package com.nadeemsultan.hidenetworkindicators;

import android.view.View;
import android.widget.ImageView;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;

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

        resparam.res.hookLayout("com.android.systemui", "layout", "signal_cluster_view", new XC_LayoutInflated() {
            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                ImageView wifi_inout = (ImageView) liparam.view.findViewById(
                        liparam.res.getIdentifier("wifi_inout", "id", "com.android.systemui"));
                wifi_inout.setVisibility(View.GONE);
                ImageView mobile_inout = (ImageView) liparam.view.findViewById(
                        liparam.res.getIdentifier("mobile_inout", "id", "com.android.systemui"));
                mobile_inout.setVisibility(View.GONE);
            }
        });
        
        resparam.res.hookLayout("com.android.systemui", "layout", "mobile_signal_group", new XC_LayoutInflated() {
            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                ImageView mobile_inout = (ImageView) liparam.view.findViewById(
                        liparam.res.getIdentifier("mobile_inout", "id", "com.android.systemui"));
                mobile_inout.setVisibility(View.GONE);
            }
        });
    }
}
