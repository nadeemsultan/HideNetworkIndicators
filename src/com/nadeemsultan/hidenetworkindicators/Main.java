package com.nadeemsultan.hidenetworkindicators;

import android.content.res.XModuleResources;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookZygoteInit, IXposedHookInitPackageResources, IXposedHookLoadPackage {
    private static String MODULE_PATH = null;
    
    //Log - Remove before release
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("Loaded app: " + lpparam.packageName);
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }

    @Override
    public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.android.systemui"))
            return;

        XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
        resparam.res.setReplacement("com.android.systemui", "drawable", "stat_sys_wifi_in", modRes.fwd(R.drawable.wifi_activity));
        resparam.res.setReplacement("com.android.systemui", "drawable", "stat_sys_wifi_inout", modRes.fwd(R.drawable.wifi_activity));
        resparam.res.setReplacement("com.android.systemui", "drawable", "stat_sys_wifi_out", modRes.fwd(R.drawable.wifi_activity));
    }
}




/*public class Main implements IXposedHookLoadPackage {
	ImageView networkActivity;
	
	public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
		if (!resparam.packageName.equals("com.android.systemui")) {
			return;
		}
		resparam.res.hookLayout("com.android.systemui","layout","signal_cluster_view",new XC_LayoutInflated() {
			@Override
			public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
				networkActivity = (ImageView) liparam.view.findViewById(liparam.res.getIdentifier("wifi_inout", "id" , "com.android.systemui"));
				
			}
		});
	}

	

}*/
