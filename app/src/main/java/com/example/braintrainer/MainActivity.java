package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView setInputForGame, gameResultId,gameScoreCountId,secondCountId, actonBarText;
    ArrayList<Integer> winPredict = new ArrayList<Integer>();
    Button btn0, btn1, btn2, btn3;
    int locationOfCorrectAnswer;
    int score = 0;
    int wrongScore = 0;
    int a;
    int b;
    int question = 0;
    private SoundPool soundPool;
    private int correctSound, wrongSound, bellSound;
    private int correctSoundStream, wrongSoundStream, bellSoundStream;
    private int allSummationRandom;
    String[] allSummation = {"+", "-", "*","/"};
    boolean checkSign = true;
    public void playAgainFunction(View view){
        gameResultId.setText("Game is Running.....");
        gameResultId.setVisibility(View.VISIBLE);
        score = 0;
        wrongScore = 0;
        secondCountId.setText("30s");
        gameScoreCountId.setText(score +"/"+ wrongScore);
        Question();
        new CountDownTimer(30100,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                secondCountId.setText(millisUntilFinished / 1000 +"s");
            }

            @Override
            public void onFinish() {
                gameResultId.setVisibility(View.INVISIBLE);
                if(question != 10){
                    bellSoundStream  = soundPool.play(bellSound, 1,1,0,0,1.0f);
                    soundPool.pause(correctSoundStream);
                    soundPool.pause(wrongSoundStream);
                    Intent intent = new Intent(MainActivity.this, MainResult.class);
                    intent.putExtra("message", Integer.toString(score));
                    startActivity(intent);
                    finish();
                }

            }
        }.start();

    }

    public void checkAnswer(View view){
       if(Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())){
           gameResultId.setBackgroundColor(getResources().getColor(R.color.green));
            correctSoundStream = soundPool.play(correctSound, 1,1,0,0,1.0f);
           soundPool.pause(wrongSoundStream);
            gameResultId.setText("Congrats! Now your Score is: "+ (score + 1));
            score++;
        }else {
            gameResultId.setBackgroundColor(Color.RED);
            wrongSoundStream = soundPool.play(wrongSound, 1,1,0,0,1.0f);
            soundPool.pause(correctSoundStream);
            gameResultId.setText("Ops! Wrong Answer.");
            wrongScore++;
        }
       gameScoreCountId.setText(score +"/"+ wrongScore);
       question++;

       Question();

        if(question == 10){
            Intent intent = new Intent(MainActivity.this, MainResult.class);
            intent.putExtra("message", Integer.toString(score));
            startActivity(intent);
            finish();
        }
    }

    public void Question(){
        Random random = new Random();



        allSummationRandom = random.nextInt(3);

        if(allSummation[allSummationRandom].equals("+")){
           a = random.nextInt(20) + 1;
           b = random.nextInt(20) + 1;
        }else if(allSummation[allSummationRandom].equals("-")){
            a = random.nextInt(20) + 1;
            b = random.nextInt(20) + 1;
            while (a <= b){
                b = random.nextInt(20) + 1;
            }
        }else if(allSummation[allSummationRandom].equals("*")){
            a = random.nextInt(9) + 1;
            b = random.nextInt(9) + 1;
        }

        setInputForGame.setText(a +" "+ allSummation[allSummationRandom]+" "+ b);

        locationOfCorrectAnswer = random.nextInt(4);

        winPredict.clear();

        for (int i = 0; i < 4; i++ ){
            if(locationOfCorrectAnswer == i){
               switch (allSummation[allSummationRandom]){
                   case "+":
                       winPredict.add(a + b);
                       break;

                   case "-":
                       winPredict.add(a - b);
                       break;
                   case "*":
                       winPredict.add(a * b);
                       break;
            }
            }else {
                int wrongAnswer = random.nextInt(42);
                if(allSummation[allSummationRandom].equals("+")){
                    while (wrongAnswer == a + b){
                        wrongAnswer = random.nextInt(42);
                    }
                }else if(allSummation[allSummationRandom].equals("-")){
                    while (wrongAnswer == a - b){
                        wrongAnswer = random.nextInt(42);
                    }
                }else if(allSummation[allSummationRandom].equals("*")){
                    while (wrongAnswer == a * b){
                        wrongAnswer = random.nextInt(90)+1;
                    }
                }
                winPredict.add(wrongAnswer);
            }


        }

        btn0.setText(Integer.toString(winPredict.get(0)));
        btn1.setText(Integer.toString(winPredict.get(1)));
        btn2.setText(Integer.toString(winPredict.get(2)));
        btn3.setText(Integer.toString(winPredict.get(3)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setInputForGame = findViewById(R.id.gameInputId);

        btn0 = findViewById(R.id.btn0);
        btn1= findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        gameResultId = findViewById(R.id.gameResultId);
        gameScoreCountId = findViewById(R.id.gameScoreCountId);
        secondCountId = findViewById(R.id.secondCountId);
        actonBarText = findViewById(R.id.actiionBartext);
        soundControl();
        Question();
        playAgainFunction(findViewById(R.id.gameResultId));
    }

    public void soundControl(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(3)
                    .setAudioAttributes(audioAttributes)
                    .build();

        }
        else {
            SoundPool soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        }

        correctSound = soundPool.load(getApplicationContext(), R.raw.correct,1);
        wrongSound = soundPool.load(getApplicationContext(), R.raw.wrong,1);
        bellSound = soundPool.load(getApplicationContext(), R.raw.bell,1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }

}
