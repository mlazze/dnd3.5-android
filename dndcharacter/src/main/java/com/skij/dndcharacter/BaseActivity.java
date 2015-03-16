package com.skij.dndcharacter;

import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onStart() {

        // Enclose everything in a try block so we can just
        // use the default view if anything goes wrong.
        // Get the font size value from SharedPreferences.
        super.onStart();
        try {
            FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/dum1.ttf");
            FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/dum1.ttf");
            FontsOverride.setDefaultFont(this, "SERIF", "fonts/dum1.ttf");
            FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/dum1.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}