package com.example.braintrainer;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainResult extends AppCompatActivity {
    ProgressBar brainProgress;
    TextView brainScore, brainPercentage;
    int progress;
    int score;
    Button gotoGameAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_result);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        brainProgress = findViewById(R.id.brainProgress);
        brainScore = findViewById(R.id.brainScore);
        gotoGameAgain = findViewById(R.id.gotoGameAgain);
        brainPercentage = findViewById(R.id.percentageId);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();

            }
        });

        thread.start();

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");
        brainScore.setText(message);

        score = Integer.parseInt(message);

        gotoGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainResult.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


        if(score <= 2){
            brainProgress.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            brainScore.setText("Ouch! Your Brain Score is: "+ score * 10 +"%");
            brainPercentage.setText(score * 10 +"%");
            brainScore.setTextColor(Color.RED);
            brainPercentage.setTextColor(Color.RED);

        }else if(score <= 5){
            brainProgress.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
            brainScore.setText("Not Bad! Your Brain Score is: "+ score * 10 +"%");
            brainPercentage.setText(score * 10 +"%");
            brainScore.setTextColor(Color.BLUE);
            brainPercentage.setTextColor(Color.BLUE);
        }else if(score <= 7){
            brainProgress.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
            brainScore.setText("Good! Your Brain Score is: "+ score * 10 +"%");
            brainPercentage.setText(score * 10 +"%");
            brainScore.setTextColor(Color.GREEN);
            brainPercentage.setTextColor(Color.GREEN);
        }else if(score <= 10){
            brainProgress.getProgressDrawable().setColorFilter(Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN);
            brainScore.setText("OW! Your Brain Score is: "+ score * 10 +"%");
            brainPercentage.setText(score * 10 +"%");
            brainScore.setTextColor(Color.YELLOW);
            brainPercentage.setTextColor(Color.YELLOW);
        }
    }


    public void doWork() {
        for (progress = 0; progress <= score*10; progress = progress+10){
            try {
                Thread.sleep(200);
                brainProgress.setProgress(progress);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }



    }
}
