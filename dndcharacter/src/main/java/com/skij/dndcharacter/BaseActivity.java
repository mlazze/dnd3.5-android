package com.skij.dndcharacter;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import core.DnDCharacterManipulator;

public class BaseActivity extends ActionBarActivity {
    protected DnDCharacterManipulator character;
    protected int posInArray = -1;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstancestate) {

        // Enclose everything in a try block so we can just
        // use the default view if anything goes wrong.
        // Get the font size value from SharedPreferences.
        super.onCreate(savedInstancestate);
        if (!(this instanceof HomeScreen) && !(this instanceof NewCharacter))
            if (loadChar()) return;

    }

    @Override
    protected void onStart() {
        super.onStart();
        setFonts();
        setNavBar();
        setTitle();
    }

    private void setTitle() {
        try {
            CharSequence s;
            if ((s = getTitle()).equals(""))
                getSupportActionBar().setTitle(character.getName());
            else getSupportActionBar().setTitle(character.getName() + " - " + s);
        } catch (NullPointerException e) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }
    }

    protected boolean loadChar() {
        Intent i = getIntent();
        posInArray = i.getIntExtra("Character", -1);
        if (posInArray == -1 || posInArray >= Utils.getCharacterList(this).size()) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            return true;
        }
        character = Utils.getCharacter(posInArray, this);
        return false;
    }

    private void setFonts() {
        FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/dum1.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/dum1.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "fonts/dum1.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/dum1.ttf");
    }

    private void setNavBar() {

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.ic_drawer);


        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.ns_menu_open, R.string.ns_menu_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        String[] mActionList = getResources().getStringArray(R.array.editcharactions);
        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mActionList));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0) {
                    startActivityWithCharacterInfo(HitPoints.class);
                }
                if (id == 1) {
                    startActivityWithCharacterInfo(Temp.class);
                }
                if (id == 2) {
                    startActivityWithCharacterInfo(Misc.class);
                }
                if (id == 3) {
                    startActivityWithCharacterInfo(Spells.class);
                }
                if (id == 4) {
                    startActivityWithCharacterInfo(LevelUp.class);
                }
                if (id == 5) {
                    startActivityWithCharacterInfo(Inventory.class);
                }
                if (id == 6) {
                    startActivityWithCharacterInfo(Abilities.class);
                }
                if (id == 7) {
                    startActivityWithCharacterInfo(DamageReduction.class);
                }
                if (id == 8) {
                    startActivityWithCharacterInfo(EquipmentActivity.class);
                }
                if (id == 9) {
                    startActivityWithCharacterInfo(Feats.class);
                }
                if (id == 10) {
                    startActivityWithCharacterInfo(Languages.class);
                }
                if (id == 11) {
                    startActivityWithCharacterInfo(SpecialAbilities.class);
                }
                if (id == 12) {
                    startActivityWithCharacterInfo(Stats.class);
                }
                if (id == 13) {
                    startActivityWithCharacterInfo(Weapons.class);
                }
                if (id == 14) {
                    startActivityWithCharacterInfo(CharInfo.class);
                }
            }
        });
    }

    private void startActivityWithCharacterInfo(Class c) {
        Intent i;
        i = new Intent(this, c);
        i.putExtra("Character", posInArray);
        startActivity(i);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mDrawerToggle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        mDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }
}