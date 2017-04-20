package com.jjkbashlord.menu;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by JJK on 4/18/17.
 */

public class Extension extends AppCompatActivity {
    String fontPath_Helve = "fonts/HelveticaNeue-Bold.ttf";
    /*
    *   // Font path
        String fontPath = "fonts/DS-DIGIT.TTF";

        // text view label
        TextView txtGhost = (TextView) findViewById(R.id.ghost);

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        txtGhost.setTypeface(tf);
    * */

    public Typeface getHelve(){
        //Typeface tf = Typeface.createFromAsset(getAssets(), fontPath_Helve);
        return Typeface.createFromAsset(getAssets(), fontPath_Helve);
    }
}
