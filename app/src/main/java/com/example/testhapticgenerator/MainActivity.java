package com.example.testhapticgenerator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.audiofx.HapticGenerator;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        button = findViewById(R.id.button);
        textView = findViewById(R.id.label);

        if (HapticGenerator.isAvailable()) {
            textView.setText("HapticGenerator supported");
            textView.setTextColor(Color.GREEN);
        } else {
            textView.setText("HapticGenerator is not supported");
            textView.setTextColor(Color.RED);
        }

        button.setOnClickListener(view -> play());
    }

    private MediaPlayer player = null;
    private HapticGenerator hapticGenerator = null;

    private void releasePlayer() {
        if (player != null) {
            player.release();
        }
        if (hapticGenerator != null) {
            hapticGenerator.release();
        }
    }

    private void play() {
        button.setEnabled(false);
        releasePlayer();
        player = MediaPlayer.create(getApplicationContext(), R.raw.sniper_rifle);
        if (HapticGenerator.isAvailable()) {
            hapticGenerator = HapticGenerator.create(player.getAudioSessionId());
            hapticGenerator.setEnabled(true);
        }
        player.setOnCompletionListener(mp -> button.setEnabled(true));
        player.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }
}