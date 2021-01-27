package com.mfw.jacocohelper.support;

import android.text.TextUtils;

import java.util.Locale;

public final class AlString {
    public static final String NULL = "";

    public static String substring(String str, int fromIndex, int toIndex) {
        if (TextUtils.isEmpty(str) || str.length() <= fromIndex) {
            return "";
        }

        if (str.length() <= toIndex) {
            return str.substring(fromIndex);
        }

        return str.substring(fromIndex, toIndex);
    }

    public static String format(String source, Object... args) {
        try {
            return String.format(Locale.CHINA, source, args);
        } catch (Exception ignore) {
            return NULL;
        }
    }
}
