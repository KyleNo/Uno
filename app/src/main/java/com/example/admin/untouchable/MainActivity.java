package com.example.admin.untouchable;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    boolean firstDraw = true;
    int cardNumber=7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        LinearLayout yep = (LinearLayout) findViewById(R.id.okay);
        //ImageButton[] cards = new ImageButton[7];
        String names ="card";

        String Convert;

        for(int i=0; i<7; i++){
            ImageButton cards = new ImageButton(this.getApplicationContext());
            cards.setId(i);
            yep.addView(cards);
            hideCard(i);
            pickCard(i);
        }
        ImageButton drawCard = (ImageButton) findViewById(R.id.DrawButton);
        drawCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstDraw){
                    for(int i=0; i<7; i++){
                        showCard(i);
                    }
                    firstDraw=false;
                }
                else{
                    cardNumber++;
                }
            }
        });
    }

    protected void hideCard(int i){
        ImageButton cards = (ImageButton) findViewById(i);
        cards.setImageResource(R.drawable.red1);
        cards.setClickable(true);
        cards.setLongClickable(true);
        cards.setVisibility(View.GONE);
    }

    protected void pickCard(int i){
        ImageButton card = (ImageButton) findViewById(i);
        Random rng = new Random();
        int[] names = {
                R.drawable.red1,R.drawable.red2,R.drawable.red3,R.drawable.red4,
                R.drawable.yellow1,R.drawable.yellow2,R.drawable.yellow3,R.drawable.yellow4,
                R.drawable.blue1,R.drawable.blue2,R.drawable.blue3,R.drawable.blue4,
                R.drawable.green1,R.drawable.green2,R.drawable.green3,R.drawable.green4};
        card.setImageResource(names[rng.nextInt(16)]);
    }

    protected void showCard(int i){
        ImageButton cards = (ImageButton) findViewById(i);
        cards.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
