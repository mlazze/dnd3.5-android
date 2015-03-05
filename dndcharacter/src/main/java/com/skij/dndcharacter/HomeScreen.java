package com.skij.dndcharacter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import core.DNDCLASS;
import core.DnDCharacterManipulator;
import core.IDnDCharacterManipulator;


public class HomeScreen extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        ArrayList<String> characterInfoList;
        ArrayAdapter<String> arrayAdapter;

        Utils.loadPrefs(this);
//        Utils.characterList = populateCharList();
        ListView characterListView = (ListView) findViewById(R.id.characterList);

        characterInfoList = getInfoFromCharacterList(Utils.characterList);

        //set adapter for list
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, characterInfoList);
        characterListView.setAdapter(arrayAdapter);
        characterListView.setOnItemClickListener(characterListener);
    }

    private ArrayList<String> getInfoFromCharacterList(ArrayList<DnDCharacterManipulator> characterList) {
        String tmp;
        ArrayList<String> res = new ArrayList<>(characterList.size() + 1);

        for (int i = 0; i < characterList.size(); i++) {
            IDnDCharacterManipulator tmpchar = characterList.get(i);
            tmp = tmpchar.getName() + "\nClass: " + tmpchar.getClasses().iterator().next() + "\nLevel " + tmpchar.getGlobalLevel();
            res.add(tmp);
        }

        res.add("Add New");
        return res;
    }

    private ArrayList<IDnDCharacterManipulator> populateCharList() {
        ArrayList<IDnDCharacterManipulator> lst = new ArrayList<>(1);
        int[] stats = {1, 1, 1, 1, 1, 1};
        int[] sav = {1, 1, 1};
        lst.add(new DnDCharacterManipulator("Provanova", DNDCLASS.BARBARIAN, stats, 12, sav));
        return lst;
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private AdapterView.OnItemClickListener characterListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i;
            if (position == Utils.characterList.size()) //addnew {
                i = new Intent(HomeScreen.this, NewCharacter.class);
            else {
                i = new Intent(HomeScreen.this, CharacterScreen.class);
                i.putExtra("Character", position);
            }
            startActivity(i);
        }
    };

}
