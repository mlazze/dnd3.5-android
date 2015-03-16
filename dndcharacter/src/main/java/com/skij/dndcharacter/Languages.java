package com.skij.dndcharacter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import core.DnDCharacterManipulator;


public class Languages extends ActionBarActivity {
    DnDCharacterManipulator character;
    int posInArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);

        Intent i = getIntent();
        posInArray = i.getIntExtra("Character", -1);
        if (posInArray == -1 || posInArray >= Utils.getCharacterList(this).size()) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            return;
        }

        character = Utils.getCharacter(posInArray, this);

        setOriginalValues();

        setScrollBehaviour();
    }

    private void setScrollBehaviour() {
        ScrollView parentScroll = (ScrollView) findViewById(R.id.scrollView);
        ListView childScroll = (ListView) findViewById(R.id.languages_list);
        parentScroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                Log.e("", "PARENT TOUCH");
                findViewById(R.id.languages_list).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        childScroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                Log.e("", "PARENT TOUCH");
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void setOriginalValues() {
        updateLanguages();
    }

    private void updateLanguages() {
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, character.getLanguages());
        ListView statusListView = (ListView) findViewById(R.id.languages_list);
        statusListView.setAdapter(arrayAdapter);
        registerForContextMenu(statusListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.languages_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_entry:
                character.setKnownLanguage(character.getLanguages().get((int) (info.id)), true);
                updateLanguages();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds languages to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_languages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem language) {
        // Handle action bar language clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = language.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(language);
    }

    public void apply(View view) {
        Toast.makeText(this, "Applying Changes", Toast.LENGTH_LONG).show();
        Utils.editCharacter(character, posInArray, this);
        finish();
    }

    public void addLanguage(View view) {
        String name = ((EditText) findViewById(R.id.languages_new_language_name)).getText().toString();
        if (!name.equals("")) {
            Log.d("Setting", "Setting new language " + name);
            character.setKnownLanguage(name, false);
            ((EditText) findViewById(R.id.languages_new_language_name)).getText().clear();
            updateLanguages();
        }
    }
}
