package com.example.user.currencyconverter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class CurrencyInformation extends AppCompatActivity {

    ImageButton imageButtonContinue;
    EditText editTextCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_information);

        imageButtonContinue = (ImageButton) findViewById(R.id.imageButtonContinue);

        final MediaPlayer mp = MediaPlayer.create(CurrencyInformation.this,R.raw.action_music);
        try{
            mp.start();

        }catch(Exception e){e.printStackTrace();}



        imageButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int lastPosition = mp.getCurrentPosition();
                mp.stop();

                MediaPlayer buttonClicked = MediaPlayer.create(CurrencyInformation.this, R.raw.button_click_sound);
                buttonClicked.start();

                editTextCurrency = (EditText) findViewById(R.id.editTextCurrency);

                String currency_information =
                        editTextCurrency.getText().toString();

                String stringLastPosition = Integer.toString(lastPosition);
                Intent ourNewIntent = new Intent(CurrencyInformation.this,CurrencyCalculator.class);


                ourNewIntent.putExtra("whereSoundLeft",stringLastPosition);
                ourNewIntent.putExtra("currenyRate",currency_information);
                startActivity(ourNewIntent);




            }
        });


    }
}
