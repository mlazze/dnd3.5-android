package com.skij.dndcharacter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import core.ABILITIES;


public class Abilities extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abilities);

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


        return super.onOptionsItemSelected(item);
    }

    public void apply(View view) {
        int value;
        try {
            value = Integer.parseInt(((EditText) findViewById(R.id.abilities_value)).getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }

        ABILITIES a;
        int pos = (((Spinner) findViewById(R.id.abilities_abilities_spinner)).getSelectedItemPosition());
        try {
            a = ABILITIES.values()[pos];
        } catch (ArrayIndexOutOfBoundsException e) {
            Toast.makeText(getApplicationContext(), "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }

        //applychanges
        character.setAbilitySkill(a, value);
        Toast.makeText(getApplicationContext(), "Setting " + a + " to " + value, Toast.LENGTH_SHORT).show();
        Utils.editCharacter(character, posInArray, this);

    }
}
