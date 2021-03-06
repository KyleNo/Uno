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
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
    int cardsInHand=7;
    int[][] cardValues = new int[4][100];
    int[][] number = new int[4][100];
    int[][] color = new int[4][100];
    int middleColor=10;
    int middleNumber=10;
    int wildColor=-1;
    int activePlayer=0;
    int numberofplayers=0;


    String test;
    //Context context = this.getApplicationContext();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RelativeLayout welp = (RelativeLayout) findViewById(R.id.welp);
        LinearLayout yep = (LinearLayout) findViewById(R.id.okay);//layout that arranges items at bottom of screen centered middle
        welp.setBackgroundColor(Color.parseColor("#f2e6d9"));
        ImageButton drawCard = (ImageButton) findViewById(R.id.DrawButton);
        drawCard.setVisibility(View.GONE);
        setRadioButtons();


        //setUp(1);


    }

    void setRadioButtons(){
        int players =0;
        RadioButton b1 = (RadioButton) findViewById(R.id.b1);
        RadioButton b2 = (RadioButton) findViewById(R.id.b2);
        RadioButton b3 = (RadioButton) findViewById(R.id.b3);
        RadioButton b4 = (RadioButton) findViewById(R.id.b4);
        Button start = (Button) findViewById(R.id.start);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton b1 = (RadioButton) findViewById(R.id.b1);
                RadioButton b2 = (RadioButton) findViewById(R.id.b2);
                RadioButton b3 = (RadioButton) findViewById(R.id.b3);
                RadioButton b4 = (RadioButton) findViewById(R.id.b4);

                b2.setChecked(false);
                b3.setChecked(false);
                b4.setChecked(false);
                numberofplayers=1;
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton b1 = (RadioButton) findViewById(R.id.b1);
                RadioButton b2 = (RadioButton) findViewById(R.id.b2);
                RadioButton b3 = (RadioButton) findViewById(R.id.b3);
                RadioButton b4 = (RadioButton) findViewById(R.id.b4);

                b1.setChecked(false);
                b3.setChecked(false);
                b4.setChecked(false);
                numberofplayers=2;
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton b1 = (RadioButton) findViewById(R.id.b1);
                RadioButton b2 = (RadioButton) findViewById(R.id.b2);
                RadioButton b3 = (RadioButton) findViewById(R.id.b3);
                RadioButton b4 = (RadioButton) findViewById(R.id.b4);

                b1.setChecked(false);
                b2.setChecked(false);
                b4.setChecked(false);
                numberofplayers=3;
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton b1 = (RadioButton) findViewById(R.id.b1);
                RadioButton b2 = (RadioButton) findViewById(R.id.b2);
                RadioButton b3 = (RadioButton) findViewById(R.id.b3);
                RadioButton b4 = (RadioButton) findViewById(R.id.b4);

                b1.setChecked(false);
                b2.setChecked(false);
                b3.setChecked(false);
                numberofplayers=4;
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioButton b1 = (RadioButton) findViewById(R.id.b1);
                RadioButton b2 = (RadioButton) findViewById(R.id.b2);
                RadioButton b3 = (RadioButton) findViewById(R.id.b3);
                RadioButton b4 = (RadioButton) findViewById(R.id.b4);
                Button start = (Button) findViewById(R.id.start);

                b1.setVisibility(View.GONE);
                b2.setVisibility(View.GONE);
                b3.setVisibility(View.GONE);
                b4.setVisibility(View.GONE);
                start.setVisibility(View.GONE);


                setUp(numberofplayers);
            }
        });
    }

    void setUp(int players){
        RelativeLayout welp = (RelativeLayout) findViewById(R.id.welp);
        LinearLayout yep = (LinearLayout) findViewById(R.id.okay);//layout that arranges items at bottom of screen centered middle
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
        drawCard.setVisibility(View.VISIBLE);
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
                    cardsInHand++;
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
                R.drawable.green1,R.drawable.green2,R.drawable.green3,R.drawable.green4,
                R.drawable.wild};
        cardValues[activePlayer][i]=rng.nextInt(17);
        int temp=cardValues[activePlayer][i];
        //kept having issues with the function; make it better if you really want.

        //a loop that determines the number value of each card that is generated and stores them as an int in an array at spot [image button's id].
        if(cardValues[activePlayer][i]!=R.drawable.wild) {
            for (int j = 0; j < 4; j++) {
                //j is the number value of the card.
                for (int k = 0; k < 4; k++) {
                    //k goes through each type of card to check for number.
                    if (temp == j + 4 * k) {
                        //if the randomly selected value matches the value of name[(card number)+4*(the space between each of the same value)]
                        if (i == 0) {
                            middleNumber = j;
                        }
                        number[activePlayer][i] = j;


                    }
                }
            }

            //a similar loop as above except with color.
            for (int j = 0; j < 4; j++) {
                //j is an int that arbitrarily represents a color. 0=red,1=yellow,2=blue,3=green
                for (int k = 0; k < 4; k++) {
                    if (temp == j * 4 + k) {
                        if (i == 0) {
                            middleColor = j;
                        }
                        color[activePlayer][i] = j;
                    }
                }
            }
        }
        else
        {
            middleColor=-1;
            middleNumber=-1;
        }
        test=Integer.toString(number[activePlayer][i]+1);
        test=Integer.toString(color[activePlayer][i]);

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
        ImageView win = (ImageView) findViewById(R.id.win);
        RelativeLayout layout =(RelativeLayout) findViewById(R.id.welp);
        HorizontalScrollView scroll =(HorizontalScrollView) findViewById(R.id.scroll);
        if(id>0) {
            ((ViewGroup)middle.getParent()).removeView(middle);
            layout.addView(middle);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,dpToPixels(getApplicationContext(),90));
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        middle.setLayoutParams(params);
        middleColor=color[activePlayer][id];
        middleNumber=number[activePlayer][id];

        middle.setEnabled(false);
        if(cardsInHand==0){
            //win condition
            win.bringToFront();
            win.setVisibility(View.VISIBLE);
        }
        cardsInHand--;
    }


    void attemptPlay(int id){
        //Log.v("id", Integer.toString(id));
        ImageButton cards = (ImageButton) findViewById(id);
        //Log.e("MidNum",Integer.toString(middleNumber));
        Log.e("number", Integer.toString(number[activePlayer][id]));
        Log.d("color",Integer.toString(color[activePlayer][id]));
        if(middleColor==color[activePlayer][id] || middleNumber==number[activePlayer][id] || cardValues[activePlayer][id]==16){
            replaceMiddle(id);
            if(cardValues[activePlayer][id]==16){
                wild(id);
                setWild();
            }


        }
    }

    void wild(int id){//0=red,1=yellow,2=blue,3=green
        ImageButton red = (ImageButton) findViewById(R.id.redbutt);
        ImageButton green = (ImageButton) findViewById(R.id.greenbutt);
        ImageButton blue = (ImageButton) findViewById(R.id.bluebutt);
        ImageButton yellow = (ImageButton) findViewById(R.id.yellowbutt);

        TextView text = (TextView) findViewById(R.id.wildhelp);

        red.setVisibility(View.VISIBLE);
        red.bringToFront();

        green.setVisibility(View.VISIBLE);
        green.bringToFront();

        blue.setVisibility(View.VISIBLE);
        blue.bringToFront();

        yellow.setVisibility(View.VISIBLE);
        yellow.bringToFront();

        text.setVisibility(View.VISIBLE);

        middleNumber=-1;
    }

    void setWild(){//0=red,1=yellow,2=blue,3=green
        ImageButton red = (ImageButton) findViewById(R.id.redbutt);
        ImageButton green = (ImageButton) findViewById(R.id.greenbutt);
        ImageButton blue = (ImageButton) findViewById(R.id.bluebutt);
        ImageButton yellow = (ImageButton) findViewById(R.id.yellowbutt);

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeEmGoAway();
                wildColor = 0;
                middleColor =0;

            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeEmGoAway();
                wildColor = 3;
                middleColor=3;
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeEmGoAway();
                wildColor = 2;
                middleColor=2;
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeEmGoAway();
                wildColor = 1;
                middleColor=1;
            }
        });
    }
    void makeEmGoAway(){
        ImageButton red = (ImageButton) findViewById(R.id.redbutt);
        ImageButton green = (ImageButton) findViewById(R.id.greenbutt);
        ImageButton blue = (ImageButton) findViewById(R.id.bluebutt);
        ImageButton yellow = (ImageButton) findViewById(R.id.yellowbutt);
        TextView text = (TextView) findViewById(R.id.wildhelp);
        text.setVisibility(View.GONE);
        red.setVisibility(View.GONE);
        green.setVisibility(View.GONE);
        blue.setVisibility(View.GONE);
        yellow.setVisibility(View.GONE);
        Log.d("vanish","yes");
    }

    public static int dpToPixels(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
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
