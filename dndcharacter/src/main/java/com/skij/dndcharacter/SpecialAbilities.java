package com.skij.dndcharacter;

import android.os.Bundle;
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


public class SpecialAbilities extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_abilities);

        setOriginalValues();

        setScrollBehaviour();
    }

    private void setScrollBehaviour() {
        ScrollView parentScroll = (ScrollView) findViewById(R.id.scrollView);
        ListView childScroll = (ListView) findViewById(R.id.specialabilities_list);
        parentScroll.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                Log.e("", "PARENT TOUCH");
                findViewById(R.id.specialabilities_list).getParent().requestDisallowInterceptTouchEvent(false);
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
        updateSpecialabilities();
    }

    private void updateSpecialabilities() {
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, character.getSpecialAbilities());
        ListView statusListView = (ListView) findViewById(R.id.specialabilities_list);
        statusListView.setAdapter(arrayAdapter);
        registerForContextMenu(statusListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.specialabilities_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_entry:
                character.setSpecialAbility(character.getSpecialAbilities().get((int) (info.id)), true);
                updateSpecialabilities();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds specialabilities to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_special_abilities, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem specialability) {
        // Handle action bar specialability clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = specialability.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(specialability);
    }

    public void apply(View view) {
        Toast.makeText(getApplicationContext(), "Applying Changes", Toast.LENGTH_LONG).show();
        Utils.editCharacter(character, posInArray, this);
        finish();
    }

    public void addSpecialability(View view) {
        String name = ((EditText) findViewById(R.id.specialabilities_new_specialability_name)).getText().toString();
        if (!name.equals("")) {
            Log.d("Setting", "Setting new specialability " + name);
            character.setSpecialAbility(name, false);
            ((EditText) findViewById(R.id.specialabilities_new_specialability_name)).getText().clear();
            updateSpecialabilities();
        }
    }
}
