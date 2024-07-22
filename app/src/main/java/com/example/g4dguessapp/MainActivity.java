package com.example.g4dguessapp;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, ShakeDetector.ShakeListener,OnItemClickListener {

    TextView rightWrongText,countText,resetedText;
    Random r=new Random();
    int guessedNumber;
    byte wrongs;
    boolean gameStarted,soundOn=true;
    MediaPlayer mediaPlayer;

    HashSet<TextView> data=new HashSet<>();
    TextToSpeech tts;
    ImageView soundIcon;
    SharedPreferences pref;
   GameItemAdapter adapter;
    Button startButton;
    RecyclerView recyclerView;
    private ArrayList<GameItem> gameList = new ArrayList<>(); // Change here


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        rightWrongText=findViewById(R.id.rightWrongText);
        countText=findViewById(R.id.countText);
        soundIcon=findViewById(R.id.soundIcon);
        resetedText=findViewById(R.id.resttv);
        startButton=findViewById(R.id.startButton);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
       mediaPlayer = MediaPlayer.create(this, R.raw.shake);
       fillData();
        adapter = new GameItemAdapter(gameList, this); // Pass RecyclerView here

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        // Example data update


        tts=new TextToSpeech(this,this);
        pref=getSharedPreferences("settings",MODE_PRIVATE);
        soundOn=pref.getBoolean("sound",true);
        if (soundOn)
            soundIcon.setImageResource(R.drawable.icon_sound_on);
        else
            soundIcon.setImageResource(R.drawable.icon_sound_off);

        Sensey.getInstance().init(this);
        Sensey.getInstance().startShakeDetection(this);

    }
    private void fillData(){
        for (int i = 1; i < 10; i++) {
            gameList.add(new GameItem(i,true,gameStarted));
        }
    }
    @Override
    public void onItemClick(GameItem gameItem,int position) {
        if (!gameStarted) {
            YoYo.with(Techniques.Shake).duration(500).repeat(3).playOn(startButton);
            return;
        }



        YoYo.with(Techniques.FadeInDown).duration(1000).playOn(rightWrongText);
        YoYo.with(Techniques.FadeInDown).duration(1000).playOn(countText);

        int number = gameItem.getNumber();;
        if (soundOn)
            tts.speak(String.valueOf(number), TextToSpeech.QUEUE_FLUSH, null, null);

        if (number == guessedNumber) {
            if(soundOn)mediaPlayer.start();
            rightWrongText.setText("right");
            gameStarted = false;
            // Update all items to make images visible
            for (GameItem item : gameList) {
                item.setCardShown(true); // Make all images visible
            }
            // Make the clicked item's image view Invisible
            gameItem.setCardShown(false);
            adapter.notifyDataSetChanged();


            if (soundOn)
                tts.speak("You are Winner", TextToSpeech.QUEUE_FLUSH, null, null);
          //  timerTv();

        } else {
            rightWrongText.setText("wrong");
            wrongs++;
            countText.setText(String.valueOf(wrongs));
        }

        if (wrongs == 3) {
            Toast.makeText(this, "game over", Toast.LENGTH_SHORT).show();
            gameStarted = false;
            resetData();
        }





    }

    private void timerTv() {
        // Total duration to wait after the loop
        final int totalDuration = 5 * 1000; // 5 seconds, as the loop runs 5 times with 1-second delay each

        // Loop to update the TextView with delays
        for (int i = 1; i <= 5; i++) {
            final int value = i;
            new Handler().postDelayed(() -> {
                // Update the TextView's text
                resetedText.setText("Winner and The Game Will Be Reset after \n" + value + " seconds");
            }, i * 1000); // Delay in milliseconds
        }

        // Call initiate() after the last update
        new Handler().postDelayed(this::initiate, totalDuration);

    }


    @Override
    protected void onStop() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("sound",soundOn);
        editor.apply();

        tts.stop();
        tts.shutdown();
        mediaPlayer.stop();
        Sensey.getInstance().stopShakeDetection(this);
        Sensey.getInstance().stop();

        super.onStop();
    }



    public void start(View view) {

        initiate();
    }

    private void initiate() {

        gameStarted=true;
        wrongs=0;
        rightWrongText.setText("");
        resetedText.setText("");
        countText.setText("");

         resetData();

        guessedNumber = r.nextInt(9)+1;

        Toast.makeText(this, ""+ guessedNumber, Toast.LENGTH_LONG).show();
    }
    void resetData() {


        // Shuffle the gameList to randomize the items
        Collections.shuffle(gameList);

        // Reset the properties of each GameItem
        for (GameItem gameItem : gameList) {
            gameItem.setCardShown(true);
            gameItem.setGameStared(true);// Reset properties as needed
        }
       adapter.notifyDataSetChanged();

    }
    @Override
    public void onInit(int status) {
//        tts.setPitch(0.7f);
//        tts.setSpeechRate(0.6f);
//        tts.setLanguage(new Locale("en"));
    }

    public void changeSound(View view) {
        if (soundOn){
            soundIcon.setImageResource(R.drawable.icon_sound_off);
        }else{
            soundIcon.setImageResource(R.drawable.icon_sound_on);
        }
        soundOn=!soundOn;
    }

    @Override
    public void onShakeDetected() {

    }

    @Override
    public void onShakeStopped() {
        initiate();

    }
}
