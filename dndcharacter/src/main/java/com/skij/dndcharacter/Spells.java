package com.skij.dndcharacter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import core.DnDCharacterManipulator;


public class Spells extends BaseActivity {
    DnDCharacterManipulator character;
    int posInArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spells);

        Intent i = getIntent();
        posInArray = i.getIntExtra("Character", -1);
        if (posInArray == -1 || posInArray >= Utils.getCharacterList(this).size()) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            return;
        }

        character = Utils.getCharacter(posInArray, this);

        setOriginalValues();
    }

    private void setOriginalValues() {
        updateSpells();
    }

    private void updateSpells() {
        CustomSpellAdapter arrayAdapter;
        arrayAdapter = new CustomSpellAdapter(this, character.getSpellSet(0));
        ListView statusListView = (ListView) findViewById(R.id.spells_list);
        statusListView.setAdapter(arrayAdapter);
        registerForContextMenu(statusListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    public void apply(View view) {
        Toast.makeText(this, "Applying Changes", Toast.LENGTH_LONG).show();
        Utils.editCharacter(character, posInArray, this);
        finish();
    }

    private Integer getEditTextContentAsInteger(int identifier) {
        try {
            return Integer.parseInt(((EditText) findViewById(identifier)).getText().toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void addSpell(View view) {
        String name = ((EditText) findViewById(R.id.spells_new_spell_name)).getText().toString();
        int level;
        try {
            level = Integer.parseInt(((EditText) findViewById(R.id.spells_new_spell_lvl)).getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Missing required parameters", Toast.LENGTH_SHORT).show();
            return;
        }

        String descr = ((EditText) findViewById(R.id.spells_new_spell_descr)).getText().toString();
        if (!name.equals("")) {
            character.setChosenSpellsofIndex(name, descr, level, 0, 0, false);
            updateSpells();
            ((EditText) findViewById(R.id.spells_new_spell_name)).getText().clear();
            ((EditText) findViewById(R.id.spells_new_spell_descr)).getText().clear();
            ((EditText) findViewById(R.id.spells_new_spell_lvl)).getText().clear();
        }
    }

    public class CustomSpellAdapter extends BaseAdapter {

        private ListView listView;
        private Context context;
        private List<String> list;
        private View.OnClickListener mOnPlusClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = listView.getPositionForView((View) v.getParent());
                String spell = (listView.getChildAt(listView.getPositionForView((View) v.getParent()))).toString();
                Log.e("Mario", "Title clicked, row " + spell);
            }
        };
        private View.OnClickListener mOnTextClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = listView.getPositionForView((View) v.getParent());
            }
        };

        public CustomSpellAdapter(Context context, List<String> list) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        @Override
        public View getView(int index, View view, final ViewGroup parent) {

            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                view = inflater.inflate(R.layout.spell_item, parent, false);
            }

            final String ai = (String) getItem(index);

            TextView textView = (TextView) view.findViewById(R.id.spell_item_name);
            textView.setText(ai);

            Button button = (Button) view.findViewById(R.id.spell_item_plus);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String spell = ai.split("\\(lv[0-9]*\\) x")[0];
                    int level = Integer.parseInt(ai.split("\\(lv")[1].split("\\) x")[0]);
                    character.setChosenSpellUsageDelta(spell, level, +1, 0);
                    updateSpells();
                }
            });
            button = (Button) view.findViewById(R.id.spell_item_minus);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String spell = ai.split("\\(lv[0-9]*\\) x")[0];
                    int level = Integer.parseInt(ai.split("\\(lv")[1].split("\\) x")[0]);
                    character.setChosenSpellUsageDelta(spell, level, -1, 0);
                    updateSpells();
                }
            });
            button = (Button) view.findViewById(R.id.spell_item_description);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String spell = ai.split("\\(lv[0-9]*\\) x")[0];
                    int level = Integer.parseInt(ai.split("\\(lv")[1].split("\\) x")[0]);
                    Toast.makeText(parent.getContext(), character.getSpellDesciption(0, spell, level), Toast.LENGTH_LONG).show();
                    updateSpells();
                }
            });
            button = (Button) view.findViewById(R.id.spell_item_delete);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String spell = ai.split("\\(lv[0-9]*\\) x")[0];
                    int level = Integer.parseInt(ai.split("\\(lv")[1].split("\\) x")[0]);
                    character.setChosenSpellsofIndex(spell, null, level, 0, 0, true);
                    updateSpells();
                }
            });

            return view;
        }

    }
}
