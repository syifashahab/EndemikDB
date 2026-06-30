package com.syifa.endemikdb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.syifa.endemikdb.R;
import com.syifa.endemikdb.repository.EndemikRepository;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView bg = findViewById(R.id.imgBackground);
        TextView title = findViewById(R.id.tvTitle);
        TextView subtitle = findViewById(R.id.tvSubtitle);

        bg.setScaleX(1f);
        bg.setScaleY(1f);

        bg.animate()
                .scaleX(1.12f)
                .scaleY(1.12f)
                .setDuration(SPLASH_DURATION)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        title.setTranslationY(80f);

        title.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(700)
                .setStartDelay(100)
                .start();

        subtitle.setTranslationY(60f);

        subtitle.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(600)
                .setStartDelay(250)
                .start();

        bg.postDelayed(() -> {

            EndemikRepository repository = new EndemikRepository(this);

            repository.syncData(new EndemikRepository.SyncListener() {

                @Override
                public void onSuccess() {

                    startActivity(new Intent(
                            SplashActivity.this,
                            HomeActivity.class
                    ));

                    overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                    );

                    finish();
                }

                @Override
                public void onFailed(String message) {

                    Toast.makeText(
                            SplashActivity.this,
                            "Gagal mengambil data : " + message,
                            Toast.LENGTH_LONG
                    ).show();

                    startActivity(new Intent(
                            SplashActivity.this,
                            HomeActivity.class
                    ));

                    finish();

                }
            });

        }, SPLASH_DURATION);

    }
}