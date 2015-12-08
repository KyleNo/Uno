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
    int[] cardValues;

    //Context context = this.getApplicationContext();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        LinearLayout yep = (LinearLayout) findViewById(R.id.okay);
        //ImageButton[] cards = new ImageButton[7];
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
                    createCard();
                    for(int i=0; i<cardNumber; i++){
                        //resize image buttons???
                        Log.v("what","?");
                        resizeCards(i);
                    }

                }
            }
        });
    }

    protected void resizeCards(int i){
        LinearLayout yep = (LinearLayout) findViewById(R.id.okay);
        ImageButton card = (ImageButton) findViewById(i);
        //?????
    }

    protected void createCard(){
        ImageButton card = new ImageButton (this.getApplicationContext());
        card.setId(cardNumber + 0);
        LinearLayout yep = (LinearLayout) findViewById(R.id.okay);
        yep.addView(card);
        hideCard(cardNumber);
        pickCard(cardNumber);
        showCard(cardNumber);
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
        int temp=cardValues[i];
        card.setImageResource(names[temp]);
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
