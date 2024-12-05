package com.example.cluboldcars;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Inicializacao extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private static final Object lock = new Object(); // Usado para sincronização
    private static boolean activityStarted = false; // Flag estática para evitar duplicação

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicializacao);

        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ImageView imageView = findViewById(R.id.imageView);
        Animation combinedAnimation = AnimationUtils.loadAnimation(this, R.anim.combined_animation);
        imageView.startAnimation(combinedAnimation);

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setMax(3000);

        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 3000) {
                    progressStatus += 100;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Use synchronized para garantir que apenas um thread execute isso de cada vez
                synchronized (lock) {
                    if (!activityStarted) {
                        activityStarted = true;
                        startActivity(new Intent(Inicializacao.this, MainActivity.class));
                        finish();
                    }
                }
            }
        }).start();
    }
}
