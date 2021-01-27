package com.mfw.jacocohelper.support;

import android.content.Context;

/**
 * Created by wangbo4@mafengwo.com on 2018-07-14.
 */
public class AlContext {
    private static Context sAppContext;
    private static boolean sDebuggable;

    public static void init(Options options) {
        AlLog.processNamed(options.ctx);
        AlLog.debuggable(options.debuggable);

        sAppContext = options.ctx;
        sDebuggable = options.debuggable;
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    public static boolean getDebuggable() {
        return sDebuggable;
    }

    public static class Options {
        private final Context ctx;
        private boolean debuggable = false;

        public Options(Context context) {
            ctx = context.getApplicationContext();
        }

        public Options setDebuggable(boolean debuggable) {
            this.debuggable = debuggable;
            return this;
        }

        public boolean isDebuggable() {
            return debuggable;
        }


        @Override
        public String toString() {
            return "Options{" +
                    "ctx=" + ctx +
                    ", debuggable=" + debuggable +
                    '}';
        }
    }
}
