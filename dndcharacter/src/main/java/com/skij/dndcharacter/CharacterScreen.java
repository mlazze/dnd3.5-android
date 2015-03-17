package com.skij.dndcharacter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import core.ABILITIES;
import core.DNDCLASS;
import core.DnDCharacter;
import core.Weapon;


public class CharacterScreen extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_screen);

        if (loadChar()) return;


        //TODO
        setOriginalValues();
        setOnClickListeners();
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
        setTextViewTo(R.id.cs_name, character.getName());
        setTextViewTo(R.id.cs_classes, formatClasses());
        setHealthBar();
        setTextViewTo(R.id.cs_exp, format("Exp", character.getExp()));
        setTextViewTo(R.id.cs_runspeed, format("Runspeed", character.getRunspeed()));
        setTextViewTo(R.id.cs_str, formatStat(DnDCharacter.STATS.STR, getResources().getString(R.string.strength)));
        setTextViewTo(R.id.cs_for, formatSaving(DnDCharacter.SAVING.FORTITUDE, getResources().getString(R.string.fortitude)));
        setTextViewTo(R.id.cs_dex, formatStat(DnDCharacter.STATS.DEX, getResources().getString(R.string.dexterity)));
        setTextViewTo(R.id.cs_ref, formatSaving(DnDCharacter.SAVING.REFLEX, getResources().getString(R.string.reflex)));
        setTextViewTo(R.id.cs_con, formatStat(DnDCharacter.STATS.CON, getResources().getString(R.string.constitution)));
        setTextViewTo(R.id.cs_wil, formatSaving(DnDCharacter.SAVING.WILL, getResources().getString(R.string.will)));
        setTextViewTo(R.id.cs_int, formatStat(DnDCharacter.STATS.INT, getResources().getString(R.string.intellect)));
        setTextViewTo(R.id.cs_init, format("Initiative", character.getInititative()));
        setTextViewTo(R.id.cs_wis, formatStat(DnDCharacter.STATS.WIS, getResources().getString(R.string.wisdom)));
        setTextViewTo(R.id.cs_baseatk, formatAsList("BAB", character.getBasicAttackBonuses(), "/", "", ""));
        setTextViewTo(R.id.cs_cha, formatStat(DnDCharacter.STATS.CHA, getResources().getString(R.string.charisma)));
        setTextViewTo(R.id.cs_lotta, format("Lotta", character.getLotta()));
        setTextViewTo(R.id.cs_ac, format("AC", character.getAC()));
        setTextViewTo(R.id.cs_touch, format("Cont", character.getTouch()));
        setTextViewTo(R.id.cs_sprovvista, format("Sprovv", character.getSprovvista()));
        setTextViewTo(R.id.cs_dmgred, format("Dmg. Red", character.getDamageReduction()));
        setTextViewTo(R.id.cs_spellres, format("Spell. Res", character.getSpellResist()));
        setTextViewTo(R.id.cs_weap1, formatWeapon(0));
        setTextViewTo(R.id.cs_weap2, formatWeapon(1));
        setTextViewTo(R.id.cs_abilit, formatAbilities());
        setTextViewTo(R.id.cs_spells, formatSpells(0));
        setTextViewTo(R.id.cs_feats, formatAsList("Feats", character.getFeatsAsStringListWithoutDesc(), "\n", "\n", "  "));
        setTextViewTo(R.id.cs_special, formatAsList("Special Abilities", character.getSpecialAbilities(), "\n", "\n", "  "));
        setTextViewTo(R.id.cs_inventory, formatAsList("Inventory", character.getInventoryAsStringList(), "\n", "\n", "  "));
        setTextViewTo(R.id.cs_languages, formatAsList("Langauges", character.getLanguages(), "\n", "\n", "  "));

    }

    private String formatAsList(String label, List list, String entryseparator, String labelseparator, String preentryseparator) {
        String res = "";
        res += label + ":" + labelseparator;
        for (Object s : list)
            res += preentryseparator + s.toString() + entryseparator;
        int sizetoberemoved = entryseparator.length();
        if (entryseparator.equals("\n"))
            sizetoberemoved = 1;
        return res.substring(0, res.length() - sizetoberemoved);
    }

    private String formatSpells(int index) {
        try {
            return formatAsList("Spells", character.getSpellSet(index), "\n", "\n", "  ");
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private String formatAbilities() {
        String res = "";
        ABILITIES[] set = character.getAbilities().keySet().toArray(new ABILITIES[character.getAbilities().keySet().size()]);
        Arrays.sort(set);
        for (ABILITIES a : set)
            res += a.toString() + ": " + character.getAbilityMod(a) + "\n";
        return res.substring(0, res.length() - 1);
    }

    private String formatWeapon(int index) {
        Weapon w;
        String res = "";
        try {
            w = character.getWeapon(index);
            res += w.name + " [";
            for (Integer i : character.getAttackBonuses(w, 0))
                res += i + "/";
            res = res.substring(0, res.length() - 1);
            res += "]\n[";
            res += character.getDamageNonCrit(w, null) + "]\n";
            res += w.toString();
            return res;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
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

    private String format(String label, String value) {
        return label + ": " + value;
    }

    private String format(String label, int value) {
        return format(label, "" + value);
    }

    private String formatStat(DnDCharacter.STATS s, String label) {
        return label + ": " + character.getStat(s) + " | M: " + character.getMod(s);
    }

    private String formatSaving(DnDCharacter.SAVING s, String label) {
        return format(label, character.getThrow(s));
    }

    private String formatClasses() {
        String res = "";
        for (DNDCLASS c : character.getClasses()) {
            res += character.getClassName(c) + " " + character.getClassLevel(c) + " | ";
        }
        return res.substring(0, res.length() - 2);
    }

    private void setTextViewTo(int resource, String value) {
        if (value == null)
            (findViewById(resource)).setVisibility(View.GONE);
        ((TextView) findViewById(resource)).setText(value);
    }

    private boolean loadChar() {
        Intent i = getIntent();
        posInArray = i.getIntExtra("Character", -1);
        if (posInArray == -1 || posInArray >= Utils.getCharacterList(this).size()) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            return true;
        }
        character = Utils.getCharacter(posInArray, this);
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadChar();
        setOriginalValues();
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
        Intent i;
        //buttons

        if (id == R.id.action_clear_temp) {
            character.clearTemp();
            Toast.makeText(this, "Temporary stats cleared", Toast.LENGTH_SHORT).show();
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
        //otehrs
        if (id == R.id.char_screen_levelup) {
            startActivityWithCharacterInfo(LevelUp.class);
        }
        if (id == R.id.char_screen_abilities) {
            startActivityWithCharacterInfo(Abilities.class);
        }
        if (id == R.id.char_screen_stats) {
            startActivityWithCharacterInfo(Stats.class);
        }
        if (id == R.id.char_screen_misc) {
            startActivityWithCharacterInfo(Misc.class);
        }
        if (id == R.id.char_screen_temp) {
            startActivityWithCharacterInfo(Temp.class);
        }
        if (id == R.id.char_screen_hp) {
            startActivityWithCharacterInfo(HitPoints.class);
        }
        if (id == R.id.char_screen_Weapon) {
            startActivityWithCharacterInfo(Weapons.class);
        }
        if (id == R.id.char_screen_equipment) {
            startActivityWithCharacterInfo(EquipmentActivity.class);
        }
        if (id == R.id.char_screen_dmgred) {
            startActivityWithCharacterInfo(DamageReduction.class);
        }
        if (id == R.id.char_screen_feat) {
            startActivityWithCharacterInfo(Feats.class);
        }
        if (id == R.id.char_screen_Inventory) {
            startActivityWithCharacterInfo(Inventory.class);
        }
        if (id == R.id.char_screen_Langauges) {
            startActivityWithCharacterInfo(Languages.class);
        }
        if (id == R.id.char_screen_spabilities) {
            startActivityWithCharacterInfo(SpecialAbilities.class);
        }
        if (id == R.id.char_screen_spells) {
            startActivityWithCharacterInfo(Spells.class);
        }
        if (id == R.id.char_screen_CharInfo) {
            startActivityWithCharacterInfo(CharInfo.class);
        }
        return super.onOptionsItemSelected(item);
    }

    private void startActivityWithCharacterInfo(Class c) {
        Intent i;
        i = new Intent(this, c);
        i.putExtra("Character", posInArray);
        startActivity(i);
    }


}
