package com.mfw.jacocohelper.support;

import android.content.Context;
import android.media.AudioManager;

public class AlAudio {
    public static int getVolume() {
        AudioManager audioManager = (AudioManager)AlContext.getAppContext().getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getStreamVolume(AudioManager.STREAM_RING);
    }
}
