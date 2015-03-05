package com.skij.dndcharacter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import core.DnDCharacterManipulator;

public class Utils {
    public static ArrayList<DnDCharacterManipulator> characterList;


    @SuppressLint("CommitPrefEdits")
    public static void savePrefs(Context c) {
        SharedPreferences mPrefs = c.getSharedPreferences(c.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mPrefs.edit();
        String tmp = arrListToJSONString(characterList);
        ed.putString("CharacterList", tmp);
        ed.commit();
    }

    @SuppressLint("CommitPrefEdits")
    public static void resetPrefs(Context c) {
        SharedPreferences mPrefs = c.getSharedPreferences(c.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.clear();
        ed.commit();
    }

    public static void loadPrefs(Context c) {
        SharedPreferences mPrefs = c.getSharedPreferences(c.getApplicationInfo().name, Context.MODE_PRIVATE);
        //blackmagic
        String tmp = mPrefs.getString("CharacterList", null);
        if (tmp != null) {
            characterList = jsonStringtoArrList(tmp);
//            Log.d("PREFS", "CurrentJson:" + tmp);
        } else {
            characterList = new ArrayList<>(0);
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
        Log.d("Removing", "Removing " + id);
        characterList.remove((int) id);
        savePrefs(c);
    }

    public static void addCharacter(DnDCharacterManipulator charac, Context c) {
        characterList.add(charac);
        savePrefs(c);
    }
}
