package com.skij.dndcharacter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import core.DnDCharacter;


public class Stats extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        setOriginalValues();
    }

    private void setOriginalValues() {
        setEditTextContentValue(R.id.stats_str, "" + character.getUnmodifiedStat(DnDCharacter.STATS.STR));
        setEditTextContentValue(R.id.stats_dex, "" + character.getUnmodifiedStat(DnDCharacter.STATS.DEX));
        setEditTextContentValue(R.id.stats_con, "" + character.getUnmodifiedStat(DnDCharacter.STATS.CON));
        setEditTextContentValue(R.id.stats_int, "" + character.getUnmodifiedStat(DnDCharacter.STATS.INT));
        setEditTextContentValue(R.id.stats_wis, "" + character.getUnmodifiedStat(DnDCharacter.STATS.WIS));
        setEditTextContentValue(R.id.stats_cha, "" + character.getUnmodifiedStat(DnDCharacter.STATS.CHA));
        setEditTextContentValue(R.id.level_up_for, character.getSavingthrowsbases()[DnDCharacter.SAVING.FORTITUDE.ordinal()] + "");
        setEditTextContentValue(R.id.level_up_ref, character.getSavingthrowsbases()[DnDCharacter.SAVING.REFLEX.ordinal()] + "");
        setEditTextContentValue(R.id.level_up_wil, character.getSavingthrowsbases()[DnDCharacter.SAVING.WILL.ordinal()] + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats, menu);
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

    public void apply(View view) {
        int str, dex, con, inte, wis, cha;
        int[] saving = new int[3];
        try {
            str = Integer.parseInt(((EditText) findViewById(R.id.stats_str)).getText().toString());
            dex = Integer.parseInt(((EditText) findViewById(R.id.stats_dex)).getText().toString());
            con = Integer.parseInt(((EditText) findViewById(R.id.stats_con)).getText().toString());
            inte = Integer.parseInt(((EditText) findViewById(R.id.stats_int)).getText().toString());
            wis = Integer.parseInt(((EditText) findViewById(R.id.stats_wis)).getText().toString());
            cha = Integer.parseInt(((EditText) findViewById(R.id.stats_cha)).getText().toString());

            saving[DnDCharacter.SAVING.FORTITUDE.ordinal()] = Integer.parseInt(((EditText) findViewById(R.id.level_up_for)).getText().toString());
            saving[DnDCharacter.SAVING.REFLEX.ordinal()] = Integer.parseInt(((EditText) findViewById(R.id.level_up_ref)).getText().toString());
            saving[DnDCharacter.SAVING.WILL.ordinal()] = Integer.parseInt(((EditText) findViewById(R.id.level_up_wil)).getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Missing required parameters", Toast.LENGTH_SHORT).show();
            return;
        }
        int[] stats = {str, dex, con, inte, wis, cha};

        for (DnDCharacter.STATS s : DnDCharacter.STATS.values()) {
            character.setStat(s, stats[s.ordinal()]);
        }
        for (DnDCharacter.SAVING s : DnDCharacter.SAVING.values()) {
            character.setSavingThrow(s, saving[s.ordinal()]);
        }

        //applychanges
        Toast.makeText(getApplicationContext(), "Setting new stats", Toast.LENGTH_SHORT).show();
        Utils.editCharacter(character, posInArray, this);
        finish();

    }
}
