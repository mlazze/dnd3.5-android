package com.skij.dndcharacter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import core.ABILITIES;
import core.DnDCharacterManipulator;


public class Abilities extends BaseActivity {
    DnDCharacterManipulator character;
    int posInArray = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abilities);

        Intent i = getIntent();
        posInArray = i.getIntExtra("Character", -1);
        if (posInArray == -1 || posInArray >= Utils.getCharacterList(this).size()) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            return;
        }

        character = Utils.getCharacter(posInArray, this);

        setSpinner(ABILITIES.values(), R.id.abilities_abilities_spinner);
        final Spinner s = (Spinner) findViewById(R.id.abilities_abilities_spinner);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                int x = s.getSelectedItemPosition();
                ((EditText) findViewById(R.id.abilities_value)).getText().clear();
                ((EditText) findViewById(R.id.abilities_value)).getText().insert(0, "" + character.getAbilities().get(ABILITIES.values()[x]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ((EditText) findViewById(R.id.abilities_value)).getText().insert(0, "");
            }
        });
    }

    private <T> void setSpinner(T[] array, int resourceId) {
        Spinner s = (Spinner) findViewById(resourceId);
        s.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, array));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_abilities, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void apply(View view) {
        int value;
        try {
            value = Integer.parseInt(((EditText) findViewById(R.id.abilities_value)).getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }

        ABILITIES a;
        int pos = (((Spinner) findViewById(R.id.abilities_abilities_spinner)).getSelectedItemPosition());
        try {
            a = ABILITIES.values()[pos];
        } catch (ArrayIndexOutOfBoundsException e) {
            Toast.makeText(this, "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }

        //applychanges
        character.setAbilitySkill(a, value);
        Toast.makeText(this, "Setting " + a + " to " + value, Toast.LENGTH_SHORT).show();
        Utils.editCharacter(character, posInArray, this);

    }
}
