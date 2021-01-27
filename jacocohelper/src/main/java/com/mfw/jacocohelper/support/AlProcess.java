package com.mfw.jacocohelper.support;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Func :
 *
 * <p> Create Time:  2019-06-11 16:41 </p>
 * <p> @author Jimmie.Qian  </p>
 */
public final class AlProcess {
    public static String getName() {
        try {
            File file = new File("/proc/" + myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int myPid() {
        return android.os.Process.myPid();
    }

    public static boolean isServiceAlive(Context ctx, Class<?> klass) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(Integer.MAX_VALUE);
        int myUid = android.os.Process.myUid();
        boolean alive = false;
        for (ActivityManager.RunningServiceInfo info : serviceList) {
            if (info.uid == myUid && info.service.getClassName().equals(klass.getName())) {
                alive = true;
            }
        }
        return alive;
    }

    public static boolean isUIProcessAlive(Context ctx) {
        return isRemoteProcessAlice(ctx, null);
    }

    public static boolean isRemoteProcessAlice(Context ctx, String remoteName) {
        String postfix = TextUtils.isEmpty(remoteName) ? "" : ":" + remoteName;
        String target = ctx.getPackageName() + postfix;
        ActivityManager am = ((ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (target.equals(info.processName)) return true;
        }
        return false;
    }

    public static String getProcessName(Context ctx) {
        ActivityManager am = ((ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (android.os.Process.myPid() == info.pid) {
                String name = info.processName;
                if (name.contains(":")) {
                    return name.substring(name.indexOf(":"));
                } else {
                    return "host";
                }
            }
        }
        return "unknown";
    }
}
