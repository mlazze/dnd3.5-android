package com.skij.dndcharacter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import core.DnDCharacterManipulator;

public class BaseActivity extends ActionBarActivity {
    protected DnDCharacterManipulator character;
    protected int posInArray = -1;
    protected String suffix = " - " + getClass().getSimpleName();
    ActionBarDrawerToggle mDrawerToggle;
    private boolean initializing = true;

    @Override
    protected void onCreate(Bundle savedInstancestate) {

        // Enclose everything in a try block so we can just
        // use the default view if anything goes wrong.
        // Get the font size value from SharedPreferences.
        super.onCreate(savedInstancestate);
        setFonts();
        if (!(this instanceof NewCharacter))
            setCharSpinner();
        if (!(this instanceof HomeScreen) && !(this instanceof NewCharacter))
            loadChar();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!(this instanceof NewCharacter))
            setNavBar();
        if (!(this instanceof NewCharacter))
            setTitle();
        if (!(this instanceof NewCharacter))
            getSupportActionBar().setSelectedNavigationItem(posInArray + 1);
    }

    private void setCharSpinner() {

        final ArrayList<String> characterInfoList = getInfoFromCharacterList(Utils.getCharacterList(this), "", suffix);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), R.layout.char_spinner_item, characterInfoList);
        //noinspection deprecation
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        final Context c = this;
        final Class cl = this.getClass();

        ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                if (!initializing) {
                    Intent i;
                    if (position == Utils.getCharacterList(BaseActivity.this).size() + 1) {
                        i = new Intent(c, HomeScreen.class);
                        finish();
                        startActivity(i);
                    } else if (position == 0) {
                        return true;
                    } else {
                        i = new Intent(c, cl);
                        i.putExtra("Character", position - 1);
                        finish();
                        startActivity(i);
                    }
                } else
                    initializing = false;
                return true;
            }
        };

        /** Setting dropdown items and item navigation listener for the actionbar */
        //noinspection deprecation
        getSupportActionBar().setListNavigationCallbacks(adapter, navigationListener);
        //noinspection deprecation
        getSupportActionBar().setSelectedNavigationItem(posInArray + 1);
    }

    private ArrayList<String> getInfoFromCharacterList
            (ArrayList<DnDCharacterManipulator> characterList, String prefix, String suffix) {
        ArrayList<String> res = new ArrayList<>(characterList.size() + 2);
        res.add("Character List");

        for (int i = 0; i < characterList.size(); i++) {
            res.add(prefix + characterList.get(i).getName() + suffix);
        }

        res.add("Manage Characters");
        return res;
    }


    private void setTitle() {
        getSupportActionBar().setTitle("");
//        try {
//            CharSequence s;
//            if ((s = getTitle()).equals(""))
//                getSupportActionBar().setTitle(character.getName());
//            else getSupportActionBar().setTitle(character.getName() + " - " + s);
//        } catch (NullPointerException e) {
//            getSupportActionBar().setTitle(getString(R.string.app_name));
//        }
    }

    protected boolean loadChar() {
        Intent i = getIntent();
        posInArray = i.getIntExtra("Character", -1);
        if (posInArray == -1 || posInArray >= Utils.getCharacterList(this).size()) {
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.ns_menu_open, R.string.ns_menu_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        String[] mActionList = getResources().getStringArray(R.array.editcharactions);
        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mActionList));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (posInArray == -1) {
                    Toast.makeText(getApplicationContext(), "Choose a character first", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (id == 0) {
                    startActivityWithCharacterInfo(CharacterScreen.class);
                }
                if (id == 1) {
                    startActivityWithCharacterInfo(HitPoints.class);
                }
                if (id == 2) {
                    startActivityWithCharacterInfo(Temp.class);
                }
                if (id == 3) {
                    startActivityWithCharacterInfo(Misc.class);
                }
                if (id == 4) {
                    startActivityWithCharacterInfo(Spells.class);
                }
                if (id == 5) {
                    startActivityWithCharacterInfo(LevelUp.class);
                }
                if (id == 6) {
                    startActivityWithCharacterInfo(Inventory.class);
                }
                if (id == 7) {
                    startActivityWithCharacterInfo(Abilities.class);
                }
                if (id == 8) {
                    startActivityWithCharacterInfo(DamageReduction.class);
                }
                if (id == 9) {
                    startActivityWithCharacterInfo(EquipmentActivity.class);
                }
                if (id == 10) {
                    startActivityWithCharacterInfo(Feats.class);
                }
                if (id == 11) {
                    startActivityWithCharacterInfo(Languages.class);
                }
                if (id == 12) {
                    startActivityWithCharacterInfo(SpecialAbilities.class);
                }
                if (id == 13) {
                    startActivityWithCharacterInfo(Stats.class);
                }
                if (id == 14) {
                    startActivityWithCharacterInfo(Weapons.class);
                }
                if (id == 15) {
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
        if (mDrawerToggle != null)
            mDrawerToggle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle != null)
            return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        if (!(this instanceof NewCharacter))
            mDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }
}