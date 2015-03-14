package com.skij.dndcharacter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import core.DnDCharacterManipulator;


public class Spells extends ActionBarActivity {
    DnDCharacterManipulator character;
    int posInArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spells);

        Intent i = getIntent();
        posInArray = i.getIntExtra("Character", -1);
        if (posInArray == -1 || posInArray >= Utils.getCharacterList(this).size()) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            return;
        }

        character = Utils.getCharacter(posInArray, this);

        setOriginalValues();
    }

    private void setOriginalValues() {
        updateSpells();
    }

    private void updateSpells() {
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, R.id.spell_item_layout, character.getSpellSet(0));
        ListView statusListView = (ListView) findViewById(R.id.spells_list);
        statusListView.setAdapter(arrayAdapter);
        registerForContextMenu(statusListView);
    }

    private void setEditTextContent(int identifier, String originalValue) {
        ((EditText) findViewById(identifier)).getText().clear();
        ((EditText) findViewById(identifier)).getText().insert(0, originalValue);
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
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void apply(View view) {
        Toast.makeText(this, "Applying Changes", Toast.LENGTH_LONG).show();
        Utils.editCharacter(character, posInArray, this);
        finish();
    }

    private Integer getEditTextContentAsInteger(int identifier) {
        try {
            return Integer.parseInt(((EditText) findViewById(identifier)).getText().toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void addSpell(View view) {
        String name = ((EditText) findViewById(R.id.spells_new_spell_name)).getText().toString();
        int level;
        try {
            level = Integer.parseInt(((EditText) findViewById(R.id.spells_new_spell_lvl)).getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this,"Missing required parameters",Toast.LENGTH_SHORT).show();
            return;
        }

        String descr = ((EditText) findViewById(R.id.spells_new_spell_descr)).getText().toString();
        if (!name.equals("")) {
            character.setChosenSpellsofIndex(name,descr,level,0,0,false);
            updateSpells();
            ((EditText) findViewById(R.id.spells_new_spell_name)).getText().clear();
            ((EditText) findViewById(R.id.spells_new_spell_descr)).getText().clear();
            ((EditText) findViewById(R.id.spells_new_spell_lvl)).getText().clear();
        }
    }

    public void minusUsage(View view) {
    }

    public void plusUsage(View view) {
    }

    public void spellDescription(View view) {
    }

    public void deleteSpell(View view) {
    }
}
