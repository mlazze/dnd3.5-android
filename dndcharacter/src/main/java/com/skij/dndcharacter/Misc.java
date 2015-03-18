package com.skij.dndcharacter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import core.DnDCharacter;


public class Misc extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc);

        setOriginalValues();
    }

    private void setOriginalValues() {
        setEditTextContent(R.id.misc_ac, character.getMiscAC() + "");
        setEditTextContent(R.id.misc_attack_roll, character.getMiscattackroll() + "");
        setEditTextContent(R.id.misc_max_hp, character.getMischitpointsmax() + "");
        setEditTextContent(R.id.misc_initiative, character.getMiscinitiative() + "");
        setEditTextContent(R.id.misc_str, character.getMiscstats(DnDCharacter.STATS.STR) + "");
        setEditTextContent(R.id.misc_dex, character.getMiscstats(DnDCharacter.STATS.DEX) + "");
        setEditTextContent(R.id.misc_con, character.getMiscstats(DnDCharacter.STATS.CON) + "");
        setEditTextContent(R.id.misc_int, character.getMiscstats(DnDCharacter.STATS.INT) + "");
        setEditTextContent(R.id.misc_wis, character.getMiscstats(DnDCharacter.STATS.WIS) + "");
        setEditTextContent(R.id.misc_cha, character.getMiscstats(DnDCharacter.STATS.CHA) + "");
        setEditTextContent(R.id.misc_for, character.getMiscsavingthrows(DnDCharacter.SAVING.FORTITUDE) + "");
        setEditTextContent(R.id.misc_ref, character.getMiscsavingthrows(DnDCharacter.SAVING.REFLEX) + "");
        setEditTextContent(R.id.misc_wil, character.getMiscsavingthrows(DnDCharacter.SAVING.WILL) + "");
        setEditTextContent(R.id.misc_magic_for, character.getMiscmagicsavingthrows(DnDCharacter.SAVING.FORTITUDE) + "");
        setEditTextContent(R.id.misc_magic_ref, character.getMiscmagicsavingthrows(DnDCharacter.SAVING.REFLEX) + "");
        setEditTextContent(R.id.misc_magic_wil, character.getMiscmagicsavingthrows(DnDCharacter.SAVING.WILL) + "");


    }

    private void setEditTextContent(int identifier, String originalValue) {
        ((EditText) findViewById(identifier)).getText().clear();
        ((EditText) findViewById(identifier)).getText().insert(0, originalValue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_misc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_clear_misc) {
            character.clearMisc();
            Toast.makeText(getApplicationContext(), "Miscellaneous stats cleared", Toast.LENGTH_SHORT).show();
            Utils.editCharacter(character, posInArray, this);
            finish();
            startActivity(getIntent());
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    public void apply(View view) {
        Integer tmp;

        try {
            if ((tmp = getEditTextContentAsInteger(R.id.misc_ac)) != null) {
                character.setMiscAC(tmp);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_attack_roll)) != null) {
                character.setMiscAttackRoll(tmp);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_max_hp)) != null) {
                character.setMiscHPMAX(tmp);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_initiative)) != null) {
                character.setMiscInitiative(tmp);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_str)) != null) {
                character.setMiscStat(DnDCharacter.STATS.STR, tmp);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_dex)) != null) {
                character.setMiscStat(DnDCharacter.STATS.DEX, tmp);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_con)) != null) {
                character.setMiscStat(DnDCharacter.STATS.CON, tmp);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_int)) != null) {
                character.setMiscStat(DnDCharacter.STATS.INT, tmp);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_wis)) != null) {
                character.setMiscStat(DnDCharacter.STATS.WIS, tmp);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_cha)) != null) {
                character.setMiscStat(DnDCharacter.STATS.CHA, tmp);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_for)) != null) {
                character.setMiscSavingThrows(DnDCharacter.SAVING.FORTITUDE, tmp, false);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_ref)) != null) {
                character.setMiscSavingThrows(DnDCharacter.SAVING.REFLEX, tmp, false);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_wil)) != null) {
                character.setMiscSavingThrows(DnDCharacter.SAVING.WILL, tmp, false);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_magic_for)) != null) {
                character.setMiscSavingThrows(DnDCharacter.SAVING.FORTITUDE, tmp, true);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_magic_ref)) != null) {
                character.setMiscSavingThrows(DnDCharacter.SAVING.REFLEX, tmp, true);
            }
            if ((tmp = getEditTextContentAsInteger(R.id.misc_magic_wil)) != null) {
                character.setMiscSavingThrows(DnDCharacter.SAVING.WILL, tmp, true);
            }
        } catch (DnDCharacter.InvalidCharacterException e) {
            Toast.makeText(getApplicationContext(), "Invalid Parameters", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(getApplicationContext(), "Applying Changes", Toast.LENGTH_LONG).show();
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
