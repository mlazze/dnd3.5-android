package com.skij.dndcharacter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import core.DnDCharacterManipulator;


public class LevelUp extends ActionBarActivity {
    DnDCharacterManipulator character;
    int posInArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_up);

        Intent i = getIntent();
        posInArray = i.getIntExtra("Character", -1);
        if (posInArray == -1 || posInArray >= Utils.characterList.size()) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            return;
        }

        character = Utils.characterList.get(posInArray);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level_up, menu);
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
