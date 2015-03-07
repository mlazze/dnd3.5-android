package com.skij.dndcharacter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import core.DnDCharacterManipulator;

public class Utils {
    private static ArrayList<DnDCharacterManipulator> characterList;
    private static boolean isReloadNecessary = true;


    @SuppressLint("CommitPrefEdits")
    private static void savePrefs(Context c) {
        SharedPreferences mPrefs = c.getSharedPreferences(c.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mPrefs.edit();
        String tmp = arrListToJSONString(characterList);
        ed.putString("CharacterList", tmp);
        ed.commit();
        isReloadNecessary = true;
    }

    @SuppressLint("CommitPrefEdits")
    private static void resetPrefs(Context c) {
        SharedPreferences mPrefs = c.getSharedPreferences(c.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.clear();
        ed.commit();
    }

    private static void loadPrefs(Context c) {
        if (isReloadNecessary) {
//            Log.e("", "loading");
            SharedPreferences mPrefs = c.getSharedPreferences(c.getApplicationInfo().name, Context.MODE_PRIVATE);
            //blackmagic
            String tmp = mPrefs.getString("CharacterList", null);
            if (tmp != null) {
                characterList = jsonStringtoArrList(tmp);
//            Log.d("PREFS", "CurrentJson:" + tmp);
            } else {
                characterList = new ArrayList<>(0);
            }
            isReloadNecessary = false;
        }
    }

    private static String arrListToJSONString(ArrayList<DnDCharacterManipulator> arr) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.enableComplexMapKeySerialization().create();
        Type t = new TypeToken<ArrayList<DnDCharacterManipulator>>() {
        }.getType();
        return gson.toJson(arr, t);
    }

    private static ArrayList<DnDCharacterManipulator> jsonStringtoArrList(String json) {
        Gson gson = new Gson();
        Type t = new TypeToken<ArrayList<DnDCharacterManipulator>>() {
        }.getType();
        return gson.fromJson(json, t);
    }

    public static void deleteCharacter(long id, Context c) {
//        Log.d("Removing", "Removing " + id);
        characterList.remove((int) id);
        savePrefs(c);
    }

    public static void addCharacter(DnDCharacterManipulator charac, Context c) {
        characterList.add(charac);
        savePrefs(c);
    }

    public static void editCharacter(DnDCharacterManipulator charac, int index, Context c) {
        characterList.set(index, charac);
        savePrefs(c);
    }

    public static DnDCharacterManipulator getCharacter(long id, Context c) {
        loadPrefs(c);
        return characterList.get((int) id);
    }

    public static ArrayList<DnDCharacterManipulator> getCharacterList(Context c) {
        loadPrefs(c);
        return characterList;
    }

}
