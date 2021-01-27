package com.mfw.jacocohelper.control.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import com.mfw.jacocohelper.model.jacoco.Report;
import com.mfw.jacocohelper.support.AlContext;

public class VolumeBroadcastReceiver extends BroadcastReceiver {
    private static long mLastGenerate = System.currentTimeMillis();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (System.currentTimeMillis() - mLastGenerate > 1200) {
            Report.generate();
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone rt = RingtoneManager.getRingtone(AlContext.getAppContext(), uri);
            rt.play();
            Toast.makeText(AlContext.getAppContext(), "create ec", Toast.LENGTH_SHORT)
                    .show();
            mLastGenerate = System.currentTimeMillis();
        }
    }
}
