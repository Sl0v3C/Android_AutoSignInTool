package com.pyy.signin;

import android.accessibilityservice.AccessibilityService;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.List;

/**
 * Created by pyy on 2017/8/5.
 */

public class Utils {
    final static String logTag = "[SignIn_Utils]";

    public static void checkSignInServiceEnabled(Context context) {
        if (Utils.isSignInServiceEnabled(context) == false) { // this stands for Context instance of this Activity.
            Utils.showAlertInfo(context);
        }
    }

    public static void showToast(String text, Context context) {
        Toast toast = Toast.makeText(context,
                text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void reLaunch(AccessibilityService service, String text) {
        //prt(Utils.getTopApp(service.getApplicationContext()));
        if (!Utils.getTopApp(service.getApplicationContext()).equals(text)) {
            PackageManager packageManager = service.getApplicationContext().getPackageManager();
            Intent mBootUpIntent = packageManager.getLaunchIntentForPackage(text);
            mBootUpIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            mBootUpIntent.setAction(Intent.ACTION_MAIN);
            //mBootUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            service.startActivity(mBootUpIntent);
        }
    }

    public static String getTopApp(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0及之后的方法
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (usm != null) {
                long now = System.currentTimeMillis();
                // 获取40秒之内的应用程序使用状态
                List<UsageStats> stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 40 * 1000, now);
                String topActivity = "";
                // 获取最新运行的程序
                if ((stats != null) && (!stats.isEmpty())) {
                    int j = 0;
                    for (int i = 0; i < stats.size(); i++) {
                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                            j = i;
                        }
                    }
                    topActivity = stats.get(j).getPackageName();
                    return topActivity;
                }
            }
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){ // 5.0之前的方法
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = activityManager.getRunningTasks(1).get(0).topActivity;
            return cn.getPackageName();
        }
        return "Not found!";
    }


    public static void showAlertInfo(final Context context) {
        new AlertDialog.Builder(context).setTitle("温馨提示")//设置对话框标题
                .setMessage("使用签到助手请开启辅助功能中的签到助手服务开关")//设置显示的内容
                .setPositiveButton("去开启",new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        context.startActivity(intent);
                    }

                }).setNegativeButton("稍后开启",new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//响应事件
                // TODO Auto-generated method stub
            }
        }).show();//在按键响应事件中显示此对话框
    }

    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void prt(String text) {
        Log.i(logTag, " " + text);
    }

    // This method used to checck the SignIn service enabled or not.
    public static boolean isSignInServiceEnabled(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (accessibilityEnabled == 1) {
            // get all the enabled service name, like:
            // com.pyy.signin/com.pyy.signin.SignInService:com.baidu.appsearch/com.baidu.appsearch.util.AppAccessibilityService
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                // Check the enabled service is the same one with this app.
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }


}
