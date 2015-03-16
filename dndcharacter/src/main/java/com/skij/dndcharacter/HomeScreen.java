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

import java.util.ArrayList;

import core.DnDCharacterManipulator;


public class HomeScreen extends BaseActivity {


    private AdapterView.OnItemClickListener characterListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i;
            if (position == Utils.getCharacterList(HomeScreen.this).size()) //addnew {
                i = new Intent(HomeScreen.this, NewCharacter.class);
            else {
                i = new Intent(HomeScreen.this, CharacterScreen.class);
                i.putExtra("Character", position);
            }
            startActivity(i);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        setListViewtoCharList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setListViewtoCharList();
    }

    private void setListViewtoCharList() {
        ArrayList<String> characterInfoList;
        ArrayAdapter<String> arrayAdapter;
        ListView characterListView = (ListView) findViewById(R.id.characterList);

        characterInfoList = getInfoFromCharacterList(Utils.getCharacterList(this));

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, characterInfoList);
        characterListView.setAdapter(arrayAdapter);
        characterListView.setOnItemClickListener(characterListener);
        registerForContextMenu(characterListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.charlist_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_entry:
                if ((int) info.id < Utils.getCharacterList(HomeScreen.this).size()) {
                    Utils.deleteCharacter(info.id, this);
                    finish();
                    startActivity(getIntent());
                    return true;
                }
            default:
                return super.onContextItemSelected(item);
        }
    }

    private ArrayList<String> getInfoFromCharacterList(ArrayList<DnDCharacterManipulator> characterList) {
        String tmp;
        ArrayList<String> res = new ArrayList<>(characterList.size() + 1);

        for (int i = 0; i < characterList.size(); i++) {
            DnDCharacterManipulator tmpchar = characterList.get(i);
            tmp = tmpchar.getName() + "\nClass: " + tmpchar.getClassesNames().get(0) + "\nLevel " + tmpchar.getGlobalLevel();
            res.add(tmp);
        }

        res.add("Add New");
        return res;
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
        Intent i;
        //noinspection SimplifiableIfStatement
        //buttons

        if (id == R.id.action_save_data) {
            Utils.saveData(this);
        }
        if (id == R.id.action_load_data) {
            Utils.loadData(this);
            finish();
            startActivity(getIntent());
        }
        return super.onOptionsItemSelected(item);
    }
}
