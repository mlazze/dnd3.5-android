package com.skij.dndcharacter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import core.DnDCharacter;


public class CharacterScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        suffix = "";
        super.onCreate(savedInstanceState);
        if (posInArray == -1) {
            setContentView(R.layout.activity_character_screen_nochar);
            String s = "";
            s += "1. Choose or add a character from Character List\n";
            s += "2. Edit its properties\n";
            s += "3. Profit!";
            ((TextView) findViewById(R.id.char_screen_tutorial)).setText(s);
        } else {
            setContentView(R.layout.activity_character_screen);

            //TODO
            setOriginalValues();
            setOnClickListeners();
        }
    }

    private void setOnClickListeners() {
        setOnClickStartActivity(LevelUp.class, R.id.cs_classes);
        setOnClickStartActivity(HitPoints.class, R.id.hitpoints_health_bar);
        setOnClickStartActivity(Exp.class, R.id.cs_exp);
        setOnClickStartActivity(Temp.class, R.id.cs_str);
        setOnClickStartActivity(Temp.class, R.id.cs_for);
        setOnClickStartActivity(Temp.class, R.id.cs_dex);
        setOnClickStartActivity(Temp.class, R.id.cs_ref);
        setOnClickStartActivity(Temp.class, R.id.cs_con);
        setOnClickStartActivity(Temp.class, R.id.cs_wil);
        setOnClickStartActivity(Temp.class, R.id.cs_int);
        setOnClickStartActivity(Misc.class, R.id.cs_init);
        setOnClickStartActivity(Temp.class, R.id.cs_wis);
        setOnClickStartActivity(Temp.class, R.id.cs_baseatk);
        setOnClickStartActivity(Temp.class, R.id.cs_cha);
        setOnClickStartActivity(Temp.class, R.id.cs_lotta);
        setOnClickStartActivity(Temp.class, R.id.cs_ac);
        setOnClickStartActivity(Temp.class, R.id.cs_touch);
        setOnClickStartActivity(Temp.class, R.id.cs_sprovvista);
        setOnClickStartActivity(DamageReduction.class, R.id.cs_dmgred);
        setOnClickStartActivity(DamageReduction.class, R.id.cs_spellres);
        setOnClickStartActivity(Weapons.class, R.id.cs_weap1);
        setOnClickStartActivity(Weapons.class, R.id.cs_weap2);
        setOnClickStartActivity(Abilities.class, R.id.cs_abilit);
        setOnClickStartActivity(Spells.class, R.id.cs_spells);
        setOnClickStartActivity(Feats.class, R.id.cs_feats);
        setOnClickStartActivity(SpecialAbilities.class, R.id.cs_special);
        setOnClickStartActivity(Inventory.class, R.id.cs_inventory);
        setOnClickStartActivity(Languages.class, R.id.cs_languages);
    }

    private void setOnClickStartActivity(final Class activityToStart, int view) {
        findViewById(view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityWithCharacterInfo(activityToStart);
            }
        });
    }

    private void setOriginalValues() {
        CharacterFormatter f = new CharacterFormatter(character);
        setTextViewContent(R.id.cs_name, character.getName());
        setTextViewContent(R.id.cs_classes, f.formatClasses());
        setHealthBar();
        setTextViewContent(R.id.cs_exp, f.format("Exp", character.getExp()));
        setTextViewContent(R.id.cs_runspeed, f.format("Runspeed", character.getRunspeed()));
        setTextViewContent(R.id.cs_str, f.formatStat(DnDCharacter.STATS.STR, getResources().getString(R.string.strength)));
        setTextViewContent(R.id.cs_for, f.formatSaving(DnDCharacter.SAVING.FORTITUDE, getResources().getString(R.string.fortitude)));
        setTextViewContent(R.id.cs_dex, f.formatStat(DnDCharacter.STATS.DEX, getResources().getString(R.string.dexterity)));
        setTextViewContent(R.id.cs_ref, f.formatSaving(DnDCharacter.SAVING.REFLEX, getResources().getString(R.string.reflex)));
        setTextViewContent(R.id.cs_con, f.formatStat(DnDCharacter.STATS.CON, getResources().getString(R.string.constitution)));
        setTextViewContent(R.id.cs_wil, f.formatSaving(DnDCharacter.SAVING.WILL, getResources().getString(R.string.will)));
        setTextViewContent(R.id.cs_int, f.formatStat(DnDCharacter.STATS.INT, getResources().getString(R.string.intellect)));
        setTextViewContent(R.id.cs_init, f.format("Initiative", character.getInititative()));
        setTextViewContent(R.id.cs_wis, f.formatStat(DnDCharacter.STATS.WIS, getResources().getString(R.string.wisdom)));
        setTextViewContent(R.id.cs_baseatk, f.formatAsList("BAB", character.getBasicAttackBonuses(), "/", " ", ""));
        setTextViewContent(R.id.cs_cha, f.formatStat(DnDCharacter.STATS.CHA, getResources().getString(R.string.charisma)));
        setTextViewContent(R.id.cs_lotta, f.format("Lotta", character.getLotta()));
        setTextViewContent(R.id.cs_ac, f.format("AC", character.getAC()));
        setTextViewContent(R.id.cs_touch, f.format("Cont", character.getTouch()));
        setTextViewContent(R.id.cs_sprovvista, f.format("Sprovv", character.getSprovvista()));
        setTextViewContent(R.id.cs_dmgred, f.format("Dmg. Red", character.getDamageReduction()));
        setTextViewContent(R.id.cs_spellres, f.format("Spell. Res", character.getSpellResist()));
        setTextViewContent(R.id.cs_weap1, f.formatWeapon(0));
        setTextViewContent(R.id.cs_weap2, f.formatWeapon(1));
        setTextViewContent(R.id.cs_abilit, f.formatAbilities());
        setTextViewContent(R.id.cs_spells, f.formatSpells(0));
        setTextViewContent(R.id.cs_feats, f.formatAsList("Feats", character.getFeatsAsStringListWithoutDesc(), "\n", "\n", "  "));
        setTextViewContent(R.id.cs_special, f.formatAsList("Special Abilities", character.getSpecialAbilities(), "\n", "\n", "  "));
        setTextViewContent(R.id.cs_inventory, f.formatAsList("Inventory", character.getInventoryAsStringList(), "\n", "\n", "  "));
        setTextViewContent(R.id.cs_languages, f.formatAsList("Langauges", character.getLanguages(), "\n", "\n", "  "));

    }

    private void setHealthBar() {
        //healthbar
        ProgressBar healthbar = (ProgressBar) findViewById(R.id.hitpoints_health_bar);
        int hmax = character.getTotalHP(), hmin = 0;
        healthbar.setMax(hmax);
        ((TextView) findViewById(R.id.hitpoints_minhealth)).setText(hmin + "");
        ((TextView) findViewById(R.id.hitpoints_maxhealth)).setText(hmax + "");
        healthbar.setProgress(character.getcurrentHP() < hmin ? hmin : character.getcurrentHP());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (posInArray != -1) {
            super.loadChar();
            setOriginalValues();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_character_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //buttons
        if (posInArray == -1) {
            Toast.makeText(getApplicationContext(), "Choose a character first", Toast.LENGTH_SHORT).show();
            return super.onOptionsItemSelected(item);
        }
        if (id == R.id.action_clear_temp) {
            character.clearTemp();
            Toast.makeText(getApplicationContext(), "Temporary stats cleared", Toast.LENGTH_SHORT).show();
            Utils.editCharacter(character, posInArray, this);
            finish();
            startActivity(getIntent());
        }
        if (id == R.id.action_temp) {
            startActivityWithCharacterInfo(Temp.class);
        }
        if (id == R.id.action_hit_points) {
            startActivityWithCharacterInfo(HitPoints.class);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("UnusedDeclaration")
    private void setActionBarOverflowBehaviours(int id) {
//        otehrs
//        if (id == R.id.char_screen_levelup) {
//            startActivityWithCharacterInfo(LevelUp.class);
//        }
//        if (id == R.id.char_screen_abilities) {
//            startActivityWithCharacterInfo(Abilities.class);
//        }
//        if (id == R.id.char_screen_stats) {
//            startActivityWithCharacterInfo(Stats.class);
//        }
//        if (id == R.id.char_screen_misc) {
//            startActivityWithCharacterInfo(Misc.class);
//        }
//        if (id == R.id.char_screen_temp) {
//            startActivityWithCharacterInfo(Temp.class);
//        }
//        if (id == R.id.char_screen_hp) {
//            startActivityWithCharacterInfo(HitPoints.class);
//        }
//        if (id == R.id.char_screen_Weapon) {
//            startActivityWithCharacterInfo(Weapons.class);
//        }
//        if (id == R.id.char_screen_equipment) {
//            startActivityWithCharacterInfo(EquipmentActivity.class);
//        }
//        if (id == R.id.char_screen_dmgred) {
//            startActivityWithCharacterInfo(DamageReduction.class);
//        }
//        if (id == R.id.char_screen_feat) {
//            startActivityWithCharacterInfo(Feats.class);
//        }
//        if (id == R.id.char_screen_Inventory) {
//            startActivityWithCharacterInfo(Inventory.class);
//        }
//        if (id == R.id.char_screen_Langauges) {
//            startActivityWithCharacterInfo(Languages.class);
//        }
//        if (id == R.id.char_screen_spabilities) {
//            startActivityWithCharacterInfo(SpecialAbilities.class);
//        }
//        if (id == R.id.char_screen_spells) {
//            startActivityWithCharacterInfo(Spells.class);
//        }
//        if (id == R.id.char_screen_CharInfo) {
//            startActivityWithCharacterInfo(CharInfo.class);
//        }
    }

    private void startActivityWithCharacterInfo(Class c) {
        Intent i;
        i = new Intent(this, c);
        i.putExtra("Character", posInArray);
        startActivity(i);
    }


}
