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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import core.DnDCharacter;
import core.DnDCharacterManipulator;
import core.Weapon;


public class NewWeapon extends BaseActivity {


    DnDCharacterManipulator character;
    int posInArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_weapon);

        Intent i = getIntent();
        posInArray = i.getIntExtra("Character", -1);
        if (posInArray == -1 || posInArray >= Utils.getCharacterList(this).size()) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            return;
        }

        character = Utils.getCharacter(posInArray, this);

        ((EditText) findViewById(R.id.new_weapon_damagemod)).getText().insert(0, "" + 1);
        setSpinner(DnDCharacter.STATS.values(), R.id.new_weapon_stat_spinner);
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
        inflater.inflate(R.menu.weapon_context_menu, menu);
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
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void apply(View view) {
        int critminrange;
        double critmult, range;
        try {
            critminrange = Integer.parseInt(getValueFromEditText(R.id.new_weapon_critminrange));
            critmult = Double.parseDouble(getValueFromEditText(R.id.new_weapon_critmult));
            range = Double.parseDouble(getValueFromEditText(R.id.new_weapon_range));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }
        double damagemod;
        try {
            damagemod = Double.parseDouble(getValueFromEditText(R.id.new_weapon_damagemod));
        } catch (NumberFormatException e) {
            damagemod = 1;
        }

        int additionaldamage;
        try {
            additionaldamage = Integer.parseInt(getValueFromEditText(R.id.new_weapon_additionaldamage));
        } catch (NumberFormatException e) {
            additionaldamage = 0;
        }

        String damagedices = getValueFromEditText(R.id.new_weapon_damagedices);
        if (damagedices.equals("")) {
            Toast.makeText(this, "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }
        String name = getValueFromEditText(R.id.new_weapon_name);
        if (name.equals("")) {
            Toast.makeText(this, "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }

        String notes = getValueFromEditText(R.id.new_weapon_notes);
        String type = getValueFromEditText(R.id.new_weapon_type);

        boolean ranged = ((CheckBox) findViewById(R.id.new_weapon_ranged_checkbox)).isChecked();

        DnDCharacter.STATS stat;
        int x = (((Spinner) findViewById(R.id.new_weapon_stat_spinner)).getSelectedItemPosition());
        try {
            stat = DnDCharacter.STATS.values()[x];
        } catch (ArrayIndexOutOfBoundsException e) {
            Toast.makeText(this, "Missing required parameters", Toast.LENGTH_LONG).show();
            return;
        }

        ArrayList<Integer> customatkroll = getAtkBonus();

        Weapon w = new Weapon(name, ranged, stat, damagemod, range, damagedices, critminrange, critmult);
        w.notes = notes.equals("") ? w.notes : notes;
        w.type = type.equals("") ? w.type : type;
        w.additionaldamage = additionaldamage;
        w.customattackbonus = customatkroll;

        character.setWeapon(w, false);
        Utils.editCharacter(character, posInArray, this);
        Toast.makeText(this, "Weapon Added", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String getValueFromEditText(int id) {
        return ((EditText) findViewById(id)).getText().toString();
    }

    private ArrayList<Integer> getAtkBonus() {

        int atkbonus;
        ArrayList<Integer> res = new ArrayList<>();
        try {
            atkbonus = Integer.parseInt(((EditText) findViewById(R.id.new_weapon_atk1)).getText().toString());
        } catch (NumberFormatException e) {
            return null;
        }
        res.add(atkbonus);
        try {
            atkbonus = Integer.parseInt(((EditText) findViewById(R.id.new_weapon_atk2)).getText().toString());
            res.add(atkbonus);
        } catch (NumberFormatException e) {
            return res;
        }
        try {
            atkbonus = Integer.parseInt(((EditText) findViewById(R.id.new_weapon_atk3)).getText().toString());
            res.add(atkbonus);
        } catch (NumberFormatException e) {
            return res;
        }
        try {
            atkbonus = Integer.parseInt(((EditText) findViewById(R.id.new_weapon_atk4)).getText().toString());
            res.add(atkbonus);
        } catch (NumberFormatException e) {
            return res;
        }
        try {
            atkbonus = Integer.parseInt(((EditText) findViewById(R.id.new_weapon_atk5)).getText().toString());
            res.add(atkbonus);
        } catch (NumberFormatException e) {
            return res;
        }
        try {
            atkbonus = Integer.parseInt(((EditText) findViewById(R.id.new_weapon_atk6)).getText().toString());
            res.add(atkbonus);
        } catch (NumberFormatException e) {
            return res;
        }
        try {
            atkbonus = Integer.parseInt(((EditText) findViewById(R.id.new_weapon_atk7)).getText().toString());
            res.add(atkbonus);
        } catch (NumberFormatException e) {
            return res;
        }
        try {
            atkbonus = Integer.parseInt(((EditText) findViewById(R.id.new_weapon_atk8)).getText().toString());
            res.add(atkbonus);
        } catch (NumberFormatException e) {
            return res;
        }
        return res;
    }
}
