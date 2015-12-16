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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    boolean firstDraw = true;
    int cardNumber=7;
    int cardsOnScreen=7;
    int[] cardValues = new int[100];


    //Context context = this.getApplicationContext();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RelativeLayout welp = (RelativeLayout) findViewById(R.id.welp);
        LinearLayout yep = (LinearLayout) findViewById(R.id.okay);//layout that arranges items at bottom of screen centered middle
        welp.setBackgroundColor(Color.parseColor("#f2e6d9"));


        for(int i=0; i<7; i++){//draws 7 new cards
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
                if(firstDraw){

                    for(int i=0; i<7; i++){
                        showCard(i);
                    }
                    firstDraw=false;

                }
                else{
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
        cardValues[i]=rng.nextInt(16);
        int temp=cardValues[i];//kept having issues with the function;
        card.setImageResource(names[temp]);

    }

    protected void showCard(int i){
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
    void attemptPlay(int id){
        ImageButton cards = (ImageButton) findViewById(id);
        
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
