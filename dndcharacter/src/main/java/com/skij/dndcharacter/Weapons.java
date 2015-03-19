package com.skij.dndcharacter;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class Weapons extends BaseActivity {


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
        getMenuInflater().inflate(R.menu.menu_weapons, menu);
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

}
