package com.mfw.jacocohelper.model.jacoco;

import android.os.Environment;

import com.mfw.jacocohelper.support.AlContext;
import com.mfw.jacocohelper.support.AlLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.mfw.jacocohelper.api.JacocoHelper;

public class Reflect {
    //ec文件的路径
    private static String DEFAULT_COVERAGE_DIR = Environment.getExternalStorageDirectory()
            .getPath() + "/data/jacoco";


    public static void generateEcFile(String filename, boolean isNew) {
        //读取文件夹路径
        File file = new File(DEFAULT_COVERAGE_DIR);
        //判断是否存在
        if (!file.exists() && !file.isDirectory()) {
            try {
                //生成文件夹
                file.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                if (!f.getName().contains(JacocoHelper.versionName)) {
                    f.delete();
                }
            }
        }
        if (!AlContext.getDebuggable()) return;
        if (!AlContext.getDebuggable()) return;
        OutputStream out = null;
        File mCoverageFilePath = new File(DEFAULT_COVERAGE_DIR + "/" + filename);
        try {
            if (isNew && mCoverageFilePath.exists()) {
                AlLog.d("JacocoHelper_generateEcFile: 清除旧的ec文件");
                mCoverageFilePath.delete();
            }
            if (!mCoverageFilePath.exists()) {
                mCoverageFilePath.createNewFile();
            }
            out = new FileOutputStream(mCoverageFilePath.getPath(), true);
            Object agent = Class.forName("org.jacoco.agent.rt.RT")
                    .getMethod("getAgent")
                    .invoke(null);
            out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class)
                    .invoke(agent, false));
        } catch (Exception e) {
            AlLog.d("JacocoHelper_generateEcFile: " + e.getMessage());
        } finally {
            if (out == null)
                return;
            try {
                out.close();
                AlLog.d("JacocoHelper_generateEcFile: " + mCoverageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        AlLog.d("created ec file");
    }
}
