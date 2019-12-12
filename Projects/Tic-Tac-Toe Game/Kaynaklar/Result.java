package com.example.user.tic_tac_toe;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends AppCompatActivity {

    String namePlayer1;
    String namePlayer2;

    ImageView buttonPlayAgain;

    TextView textViewWinner;

    ImageView imageViewSituation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        buttonPlayAgain = (ImageView) findViewById(R.id.buttonPlayAgain);
        textViewWinner = (TextView) findViewById(R.id.textViewWinnerName);
        imageViewSituation = (ImageView) findViewById(R.id.imageViewSituation);


        Intent intent = getIntent();

        String situation = intent.getStringExtra("situation");
        // learn the situation , whether it's a tie or a win

        int theWinner = intent.getIntExtra("winner", 0);
        // who is the winner, by default 0

        namePlayer1 = intent.getStringExtra("name1");
        namePlayer2 = intent.getStringExtra("name2");
        // learn the names of the players



        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Result.this, Game.class);
                intent.putExtra("name1",namePlayer1);
                intent.putExtra("name2",namePlayer2);
                startActivity(intent);
            }
        });



        if(situation.equals("tie")){
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.game_over);
            mediaPlayer.start();
            imageViewSituation.setImageResource(R.drawable.tie_representation);
            imageViewSituation.animate().scaleYBy(0.8f).setDuration(1000);
            imageViewSituation.animate().scaleXBy(0.8f).setDuration(1000);
            textViewWinner.setTextColor(getResources().getColor(R.color.reddish));
            textViewWinner.setText("A TIE!");
        }
        else{
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.victory);
            mediaPlayer.start();
            imageViewSituation.setImageResource(R.drawable.win_representation);
            textViewWinner.setTextColor(getResources().getColor(R.color.greenish));

            if(theWinner == 1){
                textViewWinner.setText(namePlayer1);
            }
            else if(theWinner == 2){
                textViewWinner.setText(namePlayer2);
            }
        }

    }
}
