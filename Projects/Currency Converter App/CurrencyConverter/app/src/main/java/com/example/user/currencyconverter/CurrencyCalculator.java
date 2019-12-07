package com.example.user.currencyconverter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CurrencyCalculator extends AppCompatActivity {

    Button buttonChangeConversion;
    ImageView imageViewFROM;
    ImageView imageViewTO;
    EditText editTextAmount;
    ImageButton imageButtonCalculation;
    TextView textViewResult;


    int counter = 0 ;

    double theCurrency;

    String stringCurrencyRate;
    String specifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_calculator);

        Intent newIntentToGet = getIntent();

        String stringLastPosition = newIntentToGet.getStringExtra("whereSoundLeft");
        stringCurrencyRate = newIntentToGet.getStringExtra("currenyRate");


        int lastPosition = Integer.parseInt(stringLastPosition);

        MediaPlayer mp = MediaPlayer.create(CurrencyCalculator.this,R.raw.action_music);
        mp.start();
        mp.seekTo(lastPosition);

        theCurrency = Double.parseDouble(stringCurrencyRate);

        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        editTextAmount.setHint("....... $");



        textViewResult = (TextView) findViewById(R.id.textViewResult);

        textViewResult.setText(" ");

        buttonChangeConversion = (Button) findViewById(R.id.buttonChangeConversion);
        buttonChangeConversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer buttonClicked = MediaPlayer.create(CurrencyCalculator.this, R.raw.button_click_sound);
                buttonClicked.start();



                imageViewFROM = (ImageView) findViewById(R.id.imageViewFROM);
                imageViewTO = (ImageView) findViewById(R.id.imageViewTO);

                if (counter % 2 == 0) {
                    // dollar -> TL anlamında
                    // ama TL -> dolar yap
                    theCurrency = 1 / theCurrency;
                    imageViewFROM.setImageResource(R.drawable.tl_logo);
                    imageViewTO.setImageResource(R.drawable.dollar_logo);
                    counter++;
                    editTextAmount.setHint("....... TL");


                }
                else{
                    // TL -> dollar anlamında
                    // dolar -> TL haline getir
                    theCurrency = 1 / theCurrency;
                    imageViewFROM.setImageResource(R.drawable.dollar_logo);
                    imageViewTO.setImageResource(R.drawable.tl_logo);
                    counter++;

                    editTextAmount.setHint("....... $");

                }


            }
        });


        imageButtonCalculation = (ImageButton) findViewById(R.id.imageButtonCalculate);
        imageButtonCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer buttonClicked = MediaPlayer.create(CurrencyCalculator.this, R.raw.sound_excitement);
                buttonClicked.start();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        String tempAmount = editTextAmount.getText().toString();
                        double tempDoubleAmount = Double.parseDouble(tempAmount);
                        double result = tempDoubleAmount * theCurrency;

                        // precision calculation

                        int tempIntWhole = (int) result;
                        double tempResult = result - tempIntWhole;

                        tempResult = tempResult*100;
                        int tempIntFloat = (int) tempResult;
                        double finalResult = ((double) tempIntFloat)/100 + tempIntWhole;

                        if (counter % 2 == 0){
                            specifier = "TL";
                        }
                        else{
                            specifier = "$";
                        }
                        textViewResult.setText(Double.toString(finalResult) + " " +specifier);

                    }
                }, 1200);
            }


        });

    }
}
