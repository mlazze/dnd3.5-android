package com.skij.dndcharacter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import core.DnDCharacter;
import core.DnDCharacterManipulator;


public class DamageReduction extends ActionBarActivity {
    DnDCharacterManipulator character;
    int posInArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage_reduction);

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
        setEditTextContent(R.id.damage_reduction_damage_reduction, character.getDamageReduction() + "");
        setEditTextContent(R.id.damage_reduction_spell_resist, character.getSpellResist() + "");
    }

    private void setEditTextContent(int identifier, String originalValue) {
        ((EditText) findViewById(identifier)).getText().clear();
        ((EditText) findViewById(identifier)).getText().insert(0, originalValue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_damage_reduction, menu);
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
        Integer tmp;

        try {
            if ((tmp = getEditTextContentAsInteger(R.id.damage_reduction_damage_reduction)) != null) {
                character.setDamageReduction(tmp);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.damage_reduction_spell_resist)) != null) {
                character.setSpellResist(tmp);
            }
        } catch (DnDCharacter.InvalidCharacterException e) {
            Toast.makeText(this, "Invalid Parameters", Toast.LENGTH_LONG).show();
            return;
        }

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
}