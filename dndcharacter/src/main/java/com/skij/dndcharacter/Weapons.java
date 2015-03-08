package com.skij.dndcharacter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import core.DnDCharacterManipulator;


public class Weapons extends ActionBarActivity {


    DnDCharacterManipulator character;
    int posInArray;
    private AdapterView.OnItemClickListener characterListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == character.getWeapons().size()) {
                Intent i = new Intent(Weapons.this, NewWeapon.class);
                i.putExtra("Character", posInArray);
                startActivity(i);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        updateWeaponList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapons);

        Intent i = getIntent();
        posInArray = i.getIntExtra("Character", -1);
        if (posInArray == -1 || posInArray >= Utils.getCharacterList(this).size()) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            return;
        }

        character = Utils.getCharacter(posInArray, this);
        updateWeaponList();
    }

    private void updateWeaponList() {
        ArrayList<String> weaponList;
        ArrayAdapter<String> arrayAdapter;
        ListView weaponListView = (ListView) findViewById(R.id.weapons_list);
        weaponList = character.getWeapons();
        weaponList.add("Add New");

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, weaponList);
        weaponListView.setAdapter(arrayAdapter);
        weaponListView.setOnItemClickListener(characterListener);
        registerForContextMenu(weaponListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weapon_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_entry:
                if ((int) info.id < character.getWeapons().size()) {
                    character.deleteWeapon((int) info.id);
                    Utils.editCharacter(character, posInArray, this);
                    finish();
                    startActivity(getIntent());
                    return true;
                }
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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

}
