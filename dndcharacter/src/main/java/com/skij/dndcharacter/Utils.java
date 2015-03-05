package com.skij.dndcharacter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import core.DnDCharacterManipulator;

public class Utils {
    public static ArrayList<DnDCharacterManipulator> characterList;


    public static void savePrefs(Context c) {
        SharedPreferences mPrefs = c.getSharedPreferences(c.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mPrefs.edit();
        Gson gson = new Gson();
        String tmp = gson.toJson(characterList);
        ed.putString("CharacterList", tmp);
        ed.commit();
    }

    public static void resetPrefs(Context c) {
        SharedPreferences mPrefs = c.getSharedPreferences(c.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.clear();
        ed.commit();
    }

    public static void loadPrefs(Context c) {
        Gson gson = new Gson();
        SharedPreferences mPrefs = c.getSharedPreferences(c.getApplicationInfo().name, Context.MODE_PRIVATE);
        //blackmagic
        String tmp = mPrefs.getString("CharacterList", null);
        if (tmp != null) {
            Type t = new TypeToken<ArrayList<DnDCharacterManipulator>>(){}.getType();
            Log.d("PREFS","C8urrentJson:" + tmp);
            characterList = gson.fromJson(tmp,t);
        } else {
            characterList = new ArrayList<>(0);
        }
    }
}
