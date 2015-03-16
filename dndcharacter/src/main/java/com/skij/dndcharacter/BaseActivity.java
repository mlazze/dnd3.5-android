package com.skij.dndcharacter;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseActivity extends ActionBarActivity {

    private static Typeface customTypeface;

    private static void setTypeFace(Typeface typeFace, ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View v = parent.getChildAt(i);
            if (v instanceof ViewGroup) {
                setTypeFace(typeFace, (ViewGroup) v);
            } else if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setPaintFlags(tv.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
                tv.setTypeface(typeFace);
            }
        }
    }

    private Typeface getCustomTypeface() {
        if (customTypeface == null) {
            //Only do this once for each typeface used
            //or we will leak unnecessary memory.
            customTypeface = Typeface.createFromAsset(getAssets(), "fonts/dum1.ttf");
        }
        return customTypeface;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Enclose everything in a try block so we can just
        // use the default view if anything goes wrong.
        try {
            // Get the font size value from SharedPreferences.

            final Typeface typeface = getCustomTypeface();
            setTypeFace(typeface, (ViewGroup) findViewById(R.id.char_screen_layout));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}