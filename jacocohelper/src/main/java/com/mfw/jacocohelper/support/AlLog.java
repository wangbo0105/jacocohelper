package com.mfw.jacocohelper.support;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * function :
 *
 * <p> Create Time:  2019年05月16日 10:01 </p>
 * <p> @author Jimmie.Qian  </p>
 */
public final class AlLog {
    private static String sTag = "m4399IM";
    private static int sLevel = Log.WARN;
    private static String sName = android.os.Process.myPid() + "";

    public static void processNamed(Context ctx) {
        sName = android.os.Process.myPid() + AlProcess.getProcessName(ctx);
    }

    static void tag(String tag) {
        sTag = tag;
    }

    public static void level(int level) {
        sLevel = level;
    }

    static void debuggable(boolean debug) {
        if (debug) sLevel = Log.VERBOSE;
        else sLevel = Log.WARN;
    }

    public static void v(String format, Object... args) {
        printLog(Log.VERBOSE, format, args);
    }

    public static void d(String format, Object... args) {
        printLog(Log.DEBUG, format, args);
    }

    public static void i(String format, Object... args) {
        printLog(Log.INFO, format, args);
    }

    public static void w(String format, Object... args) {
        printLog(Log.WARN, format, args);
    }

    public static void e(String format, Object... args) {
        printLog(Log.ERROR, format, args);
    }

    public static void v(Object format) {
        if (format == null) format = "";
        printLog(Log.VERBOSE, format.toString());
    }

    public static void d(Object format) {
        if (format == null) format = "";
        printLog(Log.DEBUG, format.toString());
    }

    public static void i(Object format) {
        if (format == null) format = "";
        printLog(Log.INFO, format.toString());
    }

    public static void w(Object format) {
        if (format == null) format = "";
        printLog(Log.WARN, format.toString());
    }

    public static void e(Object format) {
        if (format == null) format = "";
        printLog(Log.ERROR, format.toString());
    }

    public static void prettyV(String json) {
        printPretty(Log.VERBOSE, json);
    }

    public static void prettyD(String json) {
        printPretty(Log.DEBUG, json);
    }

    public static void prettyI(String json) {
        printPretty(Log.INFO, json);
    }

    public static void prettyW(String json) {
        printPretty(Log.WARN, json);
    }

    public static void prettyE(String json) {
        printPretty(Log.ERROR, json);
    }

    private static void printLog(int priority, String format, Object... args) {
        if (priority < sLevel) return;
        Log.println(priority, sTag, buildMessage(format, args));
    }

    private static void printPretty(int priority, String json) {
        if (priority < sLevel) return;
        try {
            String msg;
            if (json.startsWith("[")) {
                JSONArray o = new JSONArray(json);
                msg = o.toString(4);
            } else {
                JSONObject o = new JSONObject(json);
                msg = o.toString(4);
            }
            Log.println(priority, sTag, buildMessage(msg));
        } catch (Exception ignored) {
            printLog(priority, "not JSON format : " + json);
        }
    }

    private static String buildMessage(String format, Object... args) {
        String msg = (args == null || args.length == 0) ? format : AlString.format(format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so update there.
        for (int i = 3; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(AlLog.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }
        return AlString.format("[%s-%s] %s: %s",
                sName, Thread.currentThread().getName(), caller, msg);
    }
}
