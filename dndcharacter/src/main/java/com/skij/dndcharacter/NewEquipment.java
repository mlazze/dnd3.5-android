package com.skij.dndcharacter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import core.DnDCharacterManipulator;
import core.Equipment;


public class NewEquipment extends ActionBarActivity {


    DnDCharacterManipulator character;
    int posInArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_equipment);

        Intent i = getIntent();
        posInArray = i.getIntExtra("Character", -1);
        if (posInArray == -1 || posInArray >= Utils.getCharacterList(this).size()) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            return;
        }

        character = Utils.getCharacter(posInArray, this);

        ((EditText) findViewById(R.id.new_equipment_maxdex)).getText().insert(0, "" + 999);
        setSpinner(Equipment.TYPE.values(), R.id.new_equipment_type_spinner);
    }

    private <T> void setSpinner(T[] array, int resourceId) {
        Spinner s = (Spinner) findViewById(resourceId);
        s.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, array));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.equipment_context_menu, menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void apply(View view) {
        int maxdex;
        try {
            maxdex = Integer.parseInt(getValueFromEditText(R.id.new_equipment_maxdex));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }
        String name = getValueFromEditText(R.id.new_equipment_name);
        if (name.equals("")) {
            Toast.makeText(this, "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }

        Equipment.TYPE t;
        int x = (((Spinner) findViewById(R.id.new_equipment_type_spinner)).getSelectedItemPosition());
        try {
            t = Equipment.TYPE.values()[x];
        } catch (ArrayIndexOutOfBoundsException e) {
            Toast.makeText(this, "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }

        Integer acbonus = null, deflectionbonus = null, naturalbonus = null, savepenalty = null, speed = null, spellfail = null, weight = null;
        try {
            acbonus = Integer.parseInt(getValueFromEditText(R.id.new_equipment_acbonus));
        } catch (NumberFormatException e) {
        }
        try {
            deflectionbonus = Integer.parseInt(getValueFromEditText(R.id.new_equipment_deflectionbonus));
        } catch (NumberFormatException e) {
        }
        try {
            naturalbonus = Integer.parseInt(getValueFromEditText(R.id.new_equipment_naturalbonus));
        } catch (NumberFormatException e) {
        }
        try {
            savepenalty = Integer.parseInt(getValueFromEditText(R.id.new_equipment_savepenalty));
        } catch (NumberFormatException e) {
        }
        try {
            speed = Integer.parseInt(getValueFromEditText(R.id.new_equipment_speed));
        } catch (NumberFormatException e) {
        }
        try {
            spellfail = Integer.parseInt(getValueFromEditText(R.id.new_equipment_spellfail));
        } catch (NumberFormatException e) {
        }
        try {
            weight = Integer.parseInt(getValueFromEditText(R.id.new_equipment_weight));
        } catch (NumberFormatException e) {
        }

        String specialproperties = getValueFromEditText(R.id.new_equipment_special);


        Equipment e = new Equipment(name, t);
        e.maxdex = maxdex;
        if (acbonus != null)
            e.acbonus = acbonus;
        if (deflectionbonus != null)
            e.deflectionbonus = deflectionbonus;
        if (naturalbonus!= null)
            e.naturalbonus = naturalbonus;
        if (savepenalty != null)
            e.savepenalty = savepenalty;
        if (speed != null)
            e.speed = speed;
        if (spellfail != null)
            e.spellfail = spellfail;
        if (weight != null)
            e.weight = weight;

        if (specialproperties!=null)
            e.specialproperties=specialproperties;

        character.setEquipment(e, false);
        Utils.editCharacter(character, posInArray, this);
        Toast.makeText(this, "Equipment Added", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String getValueFromEditText(int id) {
        return ((EditText) findViewById(id)).getText().toString();
    }
}
