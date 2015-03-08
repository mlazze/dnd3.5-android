package com.skij.dndcharacter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import core.DnDCharacterManipulator;


public class CharacterScreen extends ActionBarActivity {

    DnDCharacterManipulator character;
    int posInArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_screen);

        if (loadChar()) return;

        //TODO

        TextView infos = ((TextView) findViewById(R.id.char_screen_infos));
        infos.setText(character.toString());
    }

    private boolean loadChar() {
        Intent i = getIntent();
        posInArray = i.getIntExtra("Character", -1);
        if (posInArray == -1 || posInArray >= Utils.getCharacterList(this).size()) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            return true;
        }
        character = Utils.getCharacter(posInArray, this);
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadChar();

        //TODO
        TextView infos = ((TextView) findViewById(R.id.char_screen_infos));
        infos.setText(character.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_character_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent i;
        //noinspection SimplifiableIfStatement
        //buttons
        if (id == R.id.action_clear_misc) {
            character.clearMisc();
            Toast.makeText(this, "Miscellaneous stats cleared", Toast.LENGTH_SHORT).show();
            Utils.editCharacter(character,posInArray,this);
            finish();
            startActivity(getIntent());
        }
        if (id == R.id.action_clear_temp) {
            character.clearTemp();
            Toast.makeText(this, "Temporary stats cleared", Toast.LENGTH_SHORT).show();
            Utils.editCharacter(character,posInArray,this);
            finish();
            startActivity(getIntent());
        }
        //otehrs
        if (id == R.id.char_screen_levelup) {
            i = new Intent(this, LevelUp.class);
            i.putExtra("Character", posInArray);
            startActivity(i);
        }
        if (id == R.id.char_screen_abilities) {
            i = new Intent(this, Abilities.class);
            i.putExtra("Character", posInArray);
            startActivity(i);
        }
        if (id == R.id.char_screen_stats) {
            i = new Intent(this, Stats.class);
            i.putExtra("Character", posInArray);
            startActivity(i);
        }
        if (id == R.id.char_screen_misc) {
            i = new Intent(this, Misc.class);
            i.putExtra("Character", posInArray);
            startActivity(i);
        }
        if (id == R.id.char_screen_temp) {
            i = new Intent(this, Temp.class);
            i.putExtra("Character", posInArray);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
