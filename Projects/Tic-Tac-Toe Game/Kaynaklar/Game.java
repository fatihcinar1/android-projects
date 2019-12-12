package com.example.user.tic_tac_toe;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;


public class Game extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    String namePlayer1;

    String namePlayer2;

    /*
        Player 1 -> plays with X
        Player 2 -> plays with O
     */
    int EMPTY = 0; // There is nothing at this index

    int[] gameState = {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY};
    /*
        In this game state array,
        EMPTY 0 -> not touched yet
        1 -> player 1 chose
        2 -> player 2 chose
     */
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    /*
        In TIC-TAC-TOE, there are 8 different winning situations
        We have to check each of them !
        In each iteration, CHECK THE GAME STATE
     */


    int activePlayer; // which player is playing

    int Winner; // the winner of the game

    public void Reveal(View touchedThis){
        /*
            We invoke reveal function when any of the object is pressed

         */

        ImageView touchedOne = (ImageView) touchedThis;
        int index = Integer.parseInt(touchedOne.getTag().toString());
        // to which index the player has touched

        /*
            Since we used TAG, we know where the user has touched, i.e. THE INDEX
        */

       if (gameState[index] == EMPTY){
           MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.pop);
           mediaPlayer.start();


           gameState[index] = activePlayer;

           if(activePlayer == 1){

               touchedOne.setImageResource(R.drawable.letter_x);

               activePlayer = 2;
               // but do not forget that player1 might have won after this hand
           }
           else if (activePlayer == 2){

               touchedOne.setImageResource(R.drawable.letter_o);

               activePlayer = 1;
               // but do not forget that player2 might have won after this hand
           }
       }

       String theBoard = new String();
       for(int M = 0; M < 9 ; M++){
           theBoard = theBoard + "i" + String.valueOf(M) + "->" + String.valueOf(gameState[M]) + " ";
       }
       Log.i("theBoard", theBoard);

       // Now, we have to check the game

        for(int[] possibility: winningPositions){
            if(gameState[possibility[0]] == gameState[possibility[1]]
                    && gameState[possibility[1]] == gameState[possibility[2]]
                    && gameState[possibility[0]] != EMPTY){
                /*
                    Search the game state array
                    If one of the winning possibilities OCCUR!
                    and if any one of them is not EMPTY
                    THIS MEANS A WIN
                 */
                // BUT WHO WON?
                if (activePlayer == 2){
                    // if the player 1 has put X and won
                    // according to this algorithm
                    // player2 is the active player but player 1 has won
                    Winner = 1;
                    break;
                }
                else
                {
                    // if the player 2 has put O and won
                    // according to this algorithm
                    // player1 is the active player but player 2 has won
                    Winner = 2;
                    break;
                }

            }
        }
        if(Winner == 1 || Winner == 2){
            /*
               Initially winner variable was 0,
               but there is a winner
               LEAVE THE GAME
             */
            Intent intent = new Intent(Game.this, Result.class);

            intent.putExtra("situation","win");
            intent.putExtra("winner",Winner);
            intent.putExtra("name1",namePlayer1);
            intent.putExtra("name2",namePlayer2);

            mediaPlayer.pause();
            startActivity(intent);
            return; // IF YOU DO NOT RETURN, THERE IS GOING TO BE A BUG!
        }

        // IT MIGHT BE A TIE

        boolean isTie = true; // let's assume it's a tie

        for(int itr = 0; itr < gameState.length; itr++){
            // iterate on the game state array
            if(gameState[itr] == EMPTY){
                // if there is even one empty slot,
                // it can't be tie in that case
                // but if it is full and there is not a winner
                // this means it's a tie
                isTie = false;
                break;
            }
        }

        if(isTie){
            // if it's a tie

            // log it out
            for(int m = 0; m < gameState.length; m++){
                Log.i("element " + String.valueOf(m) , "value : " + String.valueOf(gameState[m]));
            }

            Intent intent = new Intent(Game.this, Result.class);

            intent.putExtra("situation","tie");
            intent.putExtra("winner",Winner);  // winner = 0 will pass , not necessary
            intent.putExtra("name1",namePlayer1);
            intent.putExtra("name2",namePlayer2);

            mediaPlayer.pause();
            startActivity(intent);
        }


    }

    public void RefreshTheBoard(){

        // the game starts with the player 1
        activePlayer = 1;

        Winner = 0;
        // no winner right at the start

        // refresh the game state array

        for(int s = 0; s < gameState.length; s++){
            gameState[s] = EMPTY;
        }
        // refreshed the gamestate

        // now we have to remove the Xs and Os from the board
        // Find the grid layout
        GridLayout theBoard = (GridLayout) findViewById(R.id.gridLayoutGameBoard);

        // learn how many children it has
        for(int X = 0; X < theBoard.getChildCount(); X++){
            // we have to cast the child to the ImageView
            // because child is a View
            ImageView oneChild = (ImageView) theBoard.getChildAt(X);
            oneChild.setImageDrawable(null);
        }
        // we refresh the board
        // removed the Xs and Os

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        RefreshTheBoard();
        /*
            Refresh the board while starting the game
         */

        Intent intent = getIntent();
        //get the attached extras from the intent
        //we should use the same key as we used to attach the data.

        namePlayer1 = intent.getStringExtra("name1");
        namePlayer2 = intent.getStringExtra("name2");
        /*
            Get the names of the players
         */

        mediaPlayer = MediaPlayer.create(this, R.raw.extended_background);
        mediaPlayer.seekTo(intent.getIntExtra("continue",0));
        mediaPlayer.start();


    }
}
