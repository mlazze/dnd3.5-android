package com.skij.dndcharacter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
        ProgressBar healthbar = (ProgressBar) findViewById(R.id.hitpoints_health_bar);
        int hmax = character.getTotalHP(), hmin=0;
        healthbar.setMax(hmax);
        ((TextView) findViewById(R.id.hitpoints_minhealth)).setText(hmin+"");
        ((TextView) findViewById(R.id.hitpoints_maxhealth)).setText(hmax+"");
        healthbar.setProgress(10);
//        setEditTextContent(R.id.hit_points_ac, character.getHit_pointsAC() + "");
//        setEditTextContent(R.id.hit_points_attack_roll, character.getHit_pointsattackroll() + "");
//        setEditTextContent(R.id.hit_points_max_hp, character.getHit_pointsHPMax() + "");
//        setEditTextContent(R.id.hit_points_str, character.getHit_pointsstats(DnDCharacter.STATS.STR) + "");
//        setEditTextContent(R.id.hit_points_dex, character.getHit_pointsstats(DnDCharacter.STATS.DEX) + "");
//        setEditTextContent(R.id.hit_points_con, character.getHit_pointsstats(DnDCharacter.STATS.CON) + "");
//        setEditTextContent(R.id.hit_points_int, character.getHit_pointsstats(DnDCharacter.STATS.INT) + "");
//        setEditTextContent(R.id.hit_points_wis, character.getHit_pointsstats(DnDCharacter.STATS.WIS) + "");
//        setEditTextContent(R.id.hit_points_cha, character.getHit_pointsstats(DnDCharacter.STATS.CHA) + "");
//        setEditTextContent(R.id.hit_points_for, character.getHit_pointssavingthrows(DnDCharacter.SAVING.FORTITUDE) + "");
//        setEditTextContent(R.id.hit_points_ref, character.getHit_pointssavingthrows(DnDCharacter.SAVING.REFLEX) + "");
//        setEditTextContent(R.id.hit_points_wil, character.getHit_pointssavingthrows(DnDCharacter.SAVING.WILL) + "");

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

//        try {
//            if ((tmp = getEditTextContentAsInteger(R.id.hit_points_ac)) != null) {
//                character.setHit_pointsAC(tmp);
//            }
//            if ((tmp = getEditTextContentAsInteger(R.id.hit_points_attack_roll)) != null) {
//                character.setHit_pointsAttackRoll(tmp);
//            }
//            if ((tmp = getEditTextContentAsInteger(R.id.hit_points_max_hp)) != null) {
//                character.setHit_pointsHPMax(tmp);
//            }
//            if ((tmp = getEditTextContentAsInteger(R.id.hit_points_str)) != null) {
//                character.setHit_pointsStat(DnDCharacter.STATS.STR, tmp);
//            }
//            if ((tmp = getEditTextContentAsInteger(R.id.hit_points_dex)) != null) {
//                character.setHit_pointsStat(DnDCharacter.STATS.DEX, tmp);
//            }
//            if ((tmp = getEditTextContentAsInteger(R.id.hit_points_con)) != null) {
//                character.setHit_pointsStat(DnDCharacter.STATS.CON, tmp);
//            }
//            if ((tmp = getEditTextContentAsInteger(R.id.hit_points_int)) != null) {
//                character.setHit_pointsStat(DnDCharacter.STATS.INT, tmp);
//            }
//            if ((tmp = getEditTextContentAsInteger(R.id.hit_points_wis)) != null) {
//                character.setHit_pointsStat(DnDCharacter.STATS.WIS, tmp);
//            }
//            if ((tmp = getEditTextContentAsInteger(R.id.hit_points_cha)) != null) {
//                character.setHit_pointsStat(DnDCharacter.STATS.CHA, tmp);
//            }
//            if ((tmp = getEditTextContentAsInteger(R.id.hit_points_for)) != null) {
//                character.setHit_pointsSaving(DnDCharacter.SAVING.FORTITUDE, tmp);
//            }
//            if ((tmp = getEditTextContentAsInteger(R.id.hit_points_ref)) != null) {
//                character.setHit_pointsSaving(DnDCharacter.SAVING.REFLEX, tmp);
//            }
//            if ((tmp = getEditTextContentAsInteger(R.id.hit_points_wil)) != null) {
//                character.setHit_pointsSaving(DnDCharacter.SAVING.WILL, tmp);
//            }
//        } catch (DnDCharacter.InvalidCharacterException e) {
//            Toast.makeText(this, "Invalid Parameters", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        Toast.makeText(this, "Applying Changes", Toast.LENGTH_LONG).show();
//        Utils.editCharacter(character, posInArray, this);
//        finish();
    }

//    private Integer getEditTextContentAsInteger(int identifier) {
//        try {
//            return Integer.parseInt(((EditText) findViewById(identifier)).getText().toString());
//        } catch (NumberFormatException e) {
//            return null;
//        }
//    }
}
