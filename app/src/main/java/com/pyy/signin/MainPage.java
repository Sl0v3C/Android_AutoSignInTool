package com.pyy.signin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pyy on 2017/8/4.
 */

public class MainPage extends Activity {
    final String logTag = "[SignIn_MainPage]";
    private ListView appListView;
    ArrayList<AppInfo> applist;
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.checkSignInServiceEnabled(this);
        appListView = findViewById(R.id.appListView);
        Button start = findViewById(R.id.button);
        getAppList(start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new SignInThread().start();
            }
        });
    }

    class SignInThread extends Thread {
        @Override
        public void run() {
            super.run();
            processSignIn();
        }
    }

    private void processSignIn() {
        for (int i = 0; i < applist.size(); i++) {
            AppInfo tempInfo = applist.get(i);
            if (tempInfo.chosen) {
                autoSignIn(tempInfo);
            }
        }
    }

    private void autoLaunch(String name) {
        PackageManager packageManager = this.getPackageManager();
        Intent mBootUpIntent = packageManager.getLaunchIntentForPackage(name);
        //mBootUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(mBootUpIntent);
    }

    private void autoSignIn(AppInfo info) {
        lock.lock();
        flag = true;
        switch (info.appName) {
            case "京东":
                try {
                    autoLaunch(info.packageName);
                    condition.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    lock.unlock();
                }
                break;
            case "京东金融":
                try {
                    autoLaunch(info.packageName);
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    lock.unlock();
                }
                break;
            case "什么值得买":
                try {
                    autoLaunch(info.packageName);
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    lock.unlock();
                }
                break;
            case "腾讯动漫":
                try {
                    autoLaunch(info.packageName);
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    lock.unlock();
                }
                break;
            default:
                break;

        }

    }

    private void getAppList(Button btn) {
        applist = AppInfo.showSupportApps(this);
        AppAdapter adapter = new AppAdapter(this, R.layout.app_list, applist, btn);
        appListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
