package com.example.user.tic_tac_toe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class DeterminePlayers extends AppCompatActivity {

    ImageView playNowButton;

    String namePlayer1;
    String namePlayer2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_determine_players);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.extended_background);
        mediaPlayer.start();

        playNowButton = (ImageView) findViewById(R.id.playNowImage);
        playNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // when the play now button is clicked
                // go to the game
                // cary the NAMES !
                EditText editTextName1 = (EditText) findViewById(R.id.editTextNamePlayer1);
                EditText editTextName2 = (EditText) findViewById(R.id.editTextNamePlayer2);
                namePlayer1 = editTextName1.getText().toString();
                namePlayer2 = editTextName2.getText().toString();

                if (namePlayer1.equals(""))
                    namePlayer1 = "Player 1";
                if (namePlayer2.equals(""))
                    namePlayer2 = "Player 2";
                /*
                    This is necessary,
                    because if the user refuses to gives us names
                    we will refer to them as PLAYER 1 and PLAYER 2
                 */

                Intent intent = new Intent(DeterminePlayers.this, Game.class);

                intent.putExtra("name1",namePlayer1);
                intent.putExtra("name2",namePlayer2);
                intent.putExtra("continue",mediaPlayer.getCurrentPosition());

                /*
                    CARY THE NAME INFORMATION TO THE GAME
                 */
                mediaPlayer.pause();
                startActivity(intent);
            }
        });
    }
}
