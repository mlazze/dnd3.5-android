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
        setEditTextToValue(R.id.stats_str, "" + character.getUnmodifiedStat(DnDCharacter.STATS.STR));
        setEditTextToValue(R.id.stats_dex, "" + character.getUnmodifiedStat(DnDCharacter.STATS.DEX));
        setEditTextToValue(R.id.stats_con, "" + character.getUnmodifiedStat(DnDCharacter.STATS.CON));
        setEditTextToValue(R.id.stats_int, "" + character.getUnmodifiedStat(DnDCharacter.STATS.INT));
        setEditTextToValue(R.id.stats_wis, "" + character.getUnmodifiedStat(DnDCharacter.STATS.WIS));
        setEditTextToValue(R.id.stats_cha, "" + character.getUnmodifiedStat(DnDCharacter.STATS.CHA));
    }

    private void setEditTextToValue(int identifier, String value) {
        ((EditText) findViewById(identifier)).getText().clear();
        ((EditText) findViewById(identifier)).getText().insert(0, value);
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

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void apply(View view) {
        int str, dex, con, inte, wis, cha;
        try {
            str = Integer.parseInt(((EditText) findViewById(R.id.stats_str)).getText().toString());
            dex = Integer.parseInt(((EditText) findViewById(R.id.stats_dex)).getText().toString());
            con = Integer.parseInt(((EditText) findViewById(R.id.stats_con)).getText().toString());
            inte = Integer.parseInt(((EditText) findViewById(R.id.stats_int)).getText().toString());
            wis = Integer.parseInt(((EditText) findViewById(R.id.stats_wis)).getText().toString());
            cha = Integer.parseInt(((EditText) findViewById(R.id.stats_cha)).getText().toString());
        } catch (NumberFormatException e) {
            finish();
            return;
        }
        int[] stats = {str, dex, con, inte, wis, cha};

        for (DnDCharacter.STATS s : DnDCharacter.STATS.values()) {
            character.setStat(s, stats[s.ordinal()]);
        }

        //applychanges
        Toast.makeText(this, "Setting new stats", Toast.LENGTH_SHORT).show();
        Utils.editCharacter(character, posInArray, this);
        finish();

    }
}
