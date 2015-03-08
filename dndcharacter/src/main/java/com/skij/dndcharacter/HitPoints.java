package com.skij.dndcharacter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import core.DnDCharacter;
import core.DnDCharacterManipulator;


public class HitPoints extends ActionBarActivity {
    DnDCharacterManipulator character;
    int posInArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hit_points);

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
        //Current/Max
        ((TextView) findViewById(R.id.hitpoints_text)).setText(formatCurrentHp());
        //Log
        ((TextView) findViewById(R.id.hitpoints_log)).setText(formatTempHp());

        //healthbar
        ProgressBar healthbar = (ProgressBar) findViewById(R.id.hitpoints_health_bar);
        int hmax = character.getTotalHP(), hmin = 0;
        healthbar.setMax(hmax);
        ((TextView) findViewById(R.id.hitpoints_minhealth)).setText(hmin + "");
        ((TextView) findViewById(R.id.hitpoints_maxhealth)).setText(hmax + "");
        healthbar.setProgress(character.getcurrentHP() < hmin ? hmin : character.getcurrentHP());

        //deathbar
        if (character.getcurrentHP()<0) {
            ((LinearLayout) findViewById(R.id.hitpoints_death_layout)).setVisibility(View.VISIBLE);
        }
        int dmax = 100, dmin = 0;
        ProgressBar deathbar = (ProgressBar) findViewById(R.id.hitpoints_death_bar);
        deathbar.setMax(dmax);
        ((TextView) findViewById(R.id.hitpoints_mindeath)).setText("-100");
        ((TextView) findViewById(R.id.hitpoints_maxdeath)).setText("0");
        deathbar.setProgress(character.getcurrentHP() > dmin ? dmax : dmax + character.getcurrentHP());
        deathbar.setSecondaryProgress(dmax-10);

    }

    private String formatCurrentHp() {
        String res="Current HP: ";
        res+=character.getcurrentHP()+"/"+character.getTotalHP();
        return res;
    }

    private String formatTempHp() {
        ArrayList<Integer> log = character.getTempHP();
        String res = "Log:";
        for (Integer i : log) {
            if (i < 0) {
                res += " -" + (-i);
            } else {
                res += " +" + i;
            }
        }
        return res;
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
        Integer tmp;

        try {
            if ((tmp = getEditTextContentAsInteger(R.id.hitpoints_damage)) != null) {
                character.setTempHPDelta(-tmp);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.hitpoints_heal)) != null) {
                character.setTempHPDelta(tmp);
            }
        } catch (DnDCharacter.InvalidCharacterException e) {
            Toast.makeText(this, "Invalid Parameters", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Applying Changes", Toast.LENGTH_LONG).show();
        Utils.editCharacter(character, posInArray, this);
        finish();
        startActivity(getIntent());
    }

    private Integer getEditTextContentAsInteger(int identifier) {
        try {
            return Integer.parseInt(((EditText) findViewById(identifier)).getText().toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}