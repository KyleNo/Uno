package com.example.admin.untouchable;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;



public class MainActivity extends AppCompatActivity {
    boolean firstDraw = true;
    int cardNumber=8;
    int cardsOnScreen=8;
    int[] cardValues = new int[100];
    int[] number = new int[100];
    int[] color = new int[100];
    int middleColor=10;
    int middleNumber=10;

    String test;
    //Context context = this.getApplicationContext();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RelativeLayout welp = (RelativeLayout) findViewById(R.id.welp);
        LinearLayout yep = (LinearLayout) findViewById(R.id.okay);//layout that arranges items at bottom of screen centered middle
        welp.setBackgroundColor(Color.parseColor("#f2e6d9"));

        ImageButton firstCard = new ImageButton(this.getApplicationContext(),null, android.R.attr.borderlessButtonStyle);
        welp.addView(firstCard);
        firstCard.setId(0);
        hideCard(0);
        pickCard(0);
        showCard(0);

        replaceMiddle(0);
        for(int i=1; i<8; i++){//draws 7 new cards
            ImageButton cards = new ImageButton(this.getApplicationContext(), null, android.R.attr.borderlessButtonStyle);
            cards.setId(i);
            yep.addView(cards);
            hideCard(i);
            pickCard(i);
        }
        ImageButton drawCard = (ImageButton) findViewById(R.id.DrawButton);

        drawCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstDraw) {

                    for (int i = 1; i < 8; i++) {
                        showCard(i);
                    }
                    firstDraw = false;

                } else {
                    cardNumber++;
                    cardsOnScreen++;
                    createCard();

                }
            }
        });
    }


    protected void createCard(){
        ImageButton card = new ImageButton (this.getApplicationContext(), null, android.R.attr.borderlessButtonStyle);//creates new image button
        LinearLayout yep = (LinearLayout) findViewById(R.id.okay);//declare view
        card.setId(cardNumber + 0);//says it can just be the variable but doesn't compile, so just keep it.
        //sets id as an integer based on which card it is.

        yep.addView(card);
        hideCard(cardNumber);//sets basic layout for cards
        pickCard(cardNumber);//assigns card to image button
        showCard(cardNumber);//makes card visible. Wanted to do something, but didn't work.
    }


    protected void hideCard(int i){
        ImageButton cards = (ImageButton) findViewById(i);
        cards.setImageResource(R.drawable.red1);
        cards.setClickable(true);
        cards.setLongClickable(true);
        cards.setVisibility(View.INVISIBLE);
    }

    protected void pickCard(int i){
        ImageButton card = (ImageButton) findViewById(i);
        Random rng = new Random();
        int[] names = {
                R.drawable.red1,R.drawable.red2,R.drawable.red3,R.drawable.red4,
                R.drawable.yellow1,R.drawable.yellow2,R.drawable.yellow3,R.drawable.yellow4,
                R.drawable.blue1,R.drawable.blue2,R.drawable.blue3,R.drawable.blue4,
                R.drawable.green1,R.drawable.green2,R.drawable.green3,R.drawable.green4};
        cardValues[i]=rng.nextInt(16);
        int temp=cardValues[i];
        //kept having issues with the function; make it better if you really want.

        //a loop that determines the number value of each card that is generated and stores them as an int in an array at spot [image button's id].
        for(int j =0; j<4; j++){
        //j is the number value of the card.
            for(int k=0; k<4; k++){
            //k goes through each type of card to check for number.
                if(temp==j+4*k){
                //if the randomly selected value matches the value of name[(card number)+4*(the space between each of the same value)]
                    if(i==0){
                        middleNumber=j;
                    }
                    number[i]=j;


                }
            }
        }

        //a similar loop as above except with color.
        for(int j=0; j<4; j++){
        //j is an int that arbitrarily represents a color. 0=red,1=yellow,2=blue,3=green
            for(int k=0; k<4; k++){
                if(temp==j*4+k){
                    if(i==0){
                        middleColor=j;
                    }
                    color[i]=j;
                }
            }
        }
        test=Integer.toString(number[i]+1);
        test=Integer.toString(color[i]);

        card.setImageResource(names[temp]);

    }

    void showCard(int i){
        final ImageButton cards = (ImageButton) findViewById(i);
        cards.setVisibility(View.VISIBLE);

        cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = cards.getId();
                attemptPlay(id);
            }
        });

    }


    void replaceMiddle(int id){
        ImageButton middle = (ImageButton) findViewById(id);
        RelativeLayout layout =(RelativeLayout) findViewById(R.id.welp);
        HorizontalScrollView scroll =(HorizontalScrollView) findViewById(R.id.scroll);
        if(id>0) {
            ((ViewGroup)middle.getParent()).removeView(middle);
            layout.addView(middle);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        middle.setLayoutParams(params);
        middleColor=color[id];
        middleNumber=number[id];

    }


    void attemptPlay(int id){
        ImageButton cards = (ImageButton) findViewById(id);
        Log.e("MidNum",Integer.toString(middleNumber));
        Log.e("Num",Integer.toString(number[id]));
        if(middleColor==color[id] || middleNumber==number[id]){
            Log.v("id", Integer.toString(id));
            replaceMiddle(id);
        }
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
