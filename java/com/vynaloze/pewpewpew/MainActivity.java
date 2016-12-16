package com.vynaloze.pewpewpew;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.vynaloze.pewpewpew.options.OptionsActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        animateBackground();
        initialiseButtons();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void animateBackground() {
        int red = ContextCompat.getColor(this, R.color.red);
        int blue = ContextCompat.getColor(this, R.color.blue);
        int green = ContextCompat.getColor(this, R.color.green);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), red, blue, green);
        colorAnimation.setDuration(30000);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                findViewById(R.id.main_linear).setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.start();
    }

    private void initialiseButtons() {
        Button buttonPlay = (Button) findViewById(R.id.main_start);
        Button buttonOptions = (Button) findViewById(R.id.main_options);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GameActivity.class);
                startActivity(intent);
            }
        });
        buttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OptionsActivity.class);
                startActivity(intent);
            }
        });


    }


}
