package com.pyy.signin;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pyy on 2017/8/6.
 */

public class AppInfo {
    public String appName="";
    public String packageName = "";
    public String versionName = "";
    public int versionCode = 0;
    public Drawable appIcon = null;
    public boolean chosen = false;

    public static ArrayList<AppInfo> showSupportApps(Context context) {
        ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (int i = 0;i < packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            AppInfo tempInfo = new AppInfo();
            tempInfo.appName = packageInfo.applicationInfo.loadLabel(pm).toString();
            tempInfo.packageName = packageInfo.packageName;
            tempInfo.versionName = packageInfo.versionName;
            tempInfo.versionCode = packageInfo.versionCode;
            tempInfo.appIcon = packageInfo.applicationInfo.loadIcon(pm);
            if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                if (tempInfo.appName.equals("什么值得买") ||
                        tempInfo.appName.equals("京东") ||
                        tempInfo.appName.equals("京东金融") ||
                        tempInfo.appName.equals("腾讯动漫")) {
                    appList.add(tempInfo);
                }
            }
        }

        return appList;
    }
}
