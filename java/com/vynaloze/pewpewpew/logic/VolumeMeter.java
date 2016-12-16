package com.vynaloze.pewpewpew.logic;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class VolumeMeter implements Runnable {

    private AudioRecord audioRecord = null;
    private int minSize;
    private final int rate;
    private volatile double amp;

    public VolumeMeter() {
        this.rate = getMinSupportedSampleRate();
    }

    private void start() {
        minSize = AudioRecord.getMinBufferSize(rate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, rate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, minSize);
        audioRecord.startRecording();
    }

    private void stop() {
        if (audioRecord != null) {
            audioRecord.stop();
        }
    }

    private double readAmplitude() {
        short[] buffer = new short[minSize];
        audioRecord.read(buffer, 0, minSize);
        int max = 0;
        for (short s : buffer) {
            if (Math.abs(s) > max) {
                max = Math.abs(s);
            }
        }
        return max;
    }

    private int getMinSupportedSampleRate() {
        final int validSampleRates[] = new int[]{8000, 11025, 16000, 22050, 32000, 37800, 44056, 44100, 48000};
        for (int rate : validSampleRates) {
            int result = AudioRecord.getMinBufferSize(rate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            if (result != AudioRecord.ERROR && result != AudioRecord.ERROR_BAD_VALUE) {
                return rate;
            }
        }
        return -1;
    }

    public double getAmp() {
        return amp;
    }

    @Override
    public void run() {
        start();
        amp = readAmplitude();
        Log.d("AMP", "amp: " + amp);
        stop();
    }
}
