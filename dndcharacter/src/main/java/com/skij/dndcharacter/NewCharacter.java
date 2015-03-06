package com.skij.dndcharacter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import core.DNDCLASS;
import core.DnDCharacterManipulator;


public class NewCharacter extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_character);
        setClassSpinner();


        //prova
        int[] stats = {1, 1, 1, 1, 1, 1};
        int[] sav = {1, 1, 1};
        DnDCharacterManipulator mario = new DnDCharacterManipulator("Provanova", "Umano", DNDCLASS.BARBARIAN, stats, 12, sav);
        Utils.addCharacter(mario, this);
    }

    private void setClassSpinner() {
        Spinner s = (Spinner) findViewById(R.id.new_char_class_spinner);
        s.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DNDCLASS.values()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_character, menu);
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

    public void createNewCharacter(View view) {
        int str, dex, con, inte, wis, cha, fort, ref, wil, runspeed;
        try {
            str = Integer.parseInt(((EditText) findViewById(R.id.new_char_str)).getText().toString());
            dex = Integer.parseInt(((EditText) findViewById(R.id.new_char_dex)).getText().toString());
            con = Integer.parseInt(((EditText) findViewById(R.id.new_char_con)).getText().toString());
            inte = Integer.parseInt(((EditText) findViewById(R.id.new_char_int)).getText().toString());
            wis = Integer.parseInt(((EditText) findViewById(R.id.new_char_wis)).getText().toString());
            cha = Integer.parseInt(((EditText) findViewById(R.id.new_char_cha)).getText().toString());
            fort = Integer.parseInt(((EditText) findViewById(R.id.new_char_for)).getText().toString());
            ref = Integer.parseInt(((EditText) findViewById(R.id.new_char_ref)).getText().toString());
            wil = Integer.parseInt(((EditText) findViewById(R.id.new_char_wis)).getText().toString());
            runspeed = Integer.parseInt(((EditText) findViewById(R.id.new_char_runspeed)).getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }
        int[] stats = {str, dex, con, inte, wis, cha, fort, ref, wil};
        int[] sav = {fort, ref, wil};

        String name, race, classname;
        name = ((EditText) findViewById(R.id.new_char_name)).getText().toString();
        race = ((EditText) findViewById(R.id.new_char_race)).getText().toString();
        classname = ((EditText) findViewById(R.id.new_char_name)).getText().toString();
        if (name.equals("") || race.equals("") || classname.equals("")) {
            Toast.makeText(this, "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }

        //class
        DNDCLASS dndclass;
        try {
            dndclass = DNDCLASS.values()[(((Spinner) findViewById(R.id.new_char_class_spinner)).getSelectedItemPosition())];
        } catch (ArrayIndexOutOfBoundsException e) {
            Toast.makeText(this, "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }

        DnDCharacterManipulator newChar = new DnDCharacterManipulator(name, race, dndclass, stats, runspeed, sav);
        Utils.addCharacter(newChar, this);

        finish();
    }
}
