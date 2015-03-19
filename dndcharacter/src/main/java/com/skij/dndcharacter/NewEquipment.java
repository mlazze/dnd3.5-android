package com.skij.dndcharacter;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import core.Equipment;


public class NewEquipment extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_equipment);

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
        getMenuInflater().inflate(R.menu.menu_new_equipment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("EmptyCatchBlock")
    public void apply(View view) {
        int maxdex;
        try {
            maxdex = Integer.parseInt(getEditTextContent(R.id.new_equipment_maxdex));
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }
        String name = getEditTextContent(R.id.new_equipment_name);
        if (name.equals("")) {
            Toast.makeText(getApplicationContext(), "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }

        Equipment.TYPE t;
        int x = (((Spinner) findViewById(R.id.new_equipment_type_spinner)).getSelectedItemPosition());
        try {
            t = Equipment.TYPE.values()[x];
        } catch (ArrayIndexOutOfBoundsException e) {
            Toast.makeText(getApplicationContext(), "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }

        Integer acbonus = null, deflectionbonus = null, naturalbonus = null, savepenalty = null, speed = null, spellfail = null;
        Double weight = null;
        try {
            acbonus = Integer.parseInt(getEditTextContent(R.id.new_equipment_acbonus));
        } catch (NumberFormatException e) {
        }
        try {
            deflectionbonus = Integer.parseInt(getEditTextContent(R.id.new_equipment_deflectionbonus));
        } catch (NumberFormatException e) {
        }
        try {
            naturalbonus = Integer.parseInt(getEditTextContent(R.id.new_equipment_naturalbonus));
        } catch (NumberFormatException e) {
        }
        try {
            savepenalty = Integer.parseInt(getEditTextContent(R.id.new_equipment_savepenalty));
        } catch (NumberFormatException e) {
        }
        try {
            speed = Integer.parseInt(getEditTextContent(R.id.new_equipment_speed));
        } catch (NumberFormatException e) {
        }
        try {
            spellfail = Integer.parseInt(getEditTextContent(R.id.new_equipment_spellfail));
        } catch (NumberFormatException e) {
        }
        try {
            weight = Double.parseDouble(getEditTextContent(R.id.new_equipment_weight));
        } catch (NumberFormatException e) {
        }

        String specialproperties = getEditTextContent(R.id.new_equipment_special);


        Equipment e = new Equipment(name, t);
        e.maxdex = maxdex;
        if (acbonus != null)
            e.acbonus = acbonus;
        if (deflectionbonus != null)
            e.deflectionbonus = deflectionbonus;
        if (naturalbonus != null)
            e.naturalbonus = naturalbonus;
        if (savepenalty != null)
            e.savepenalty = savepenalty;
        if (speed != null)
            e.speed = speed;
        if (spellfail != null)
            e.spellfail = spellfail;
        if (weight != null)
            e.weight = weight;

        if (specialproperties != null)
            e.specialproperties = specialproperties;

        character.setEquipment(e, false);
        Utils.editCharacter(character, posInArray, this);
        Toast.makeText(getApplicationContext(), "Equipment Added", Toast.LENGTH_SHORT).show();
        finish();
    }


}
