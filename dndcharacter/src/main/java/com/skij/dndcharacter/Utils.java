package com.skij.dndcharacter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import core.IDnDCharacterManipulator;

public class Utils {
    public static ArrayList<IDnDCharacterManipulator> characterList;

    public static void savePrefs(Context c) {
        SharedPreferences mPrefs=c.getSharedPreferences(c.getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=mPrefs.edit();
        Gson gson = new Gson();
        ed.putString("CharacterList", gson.toJson(characterList));
        ed.commit();
    }

    public static void loadPrefs(Context c) {
        Gson gson = new Gson();
        SharedPreferences mPrefs=c.getSharedPreferences(c.getApplicationInfo().name, Context.MODE_PRIVATE);
        //blackmagic
        Type collectionType = new TypeToken<Collection<IDnDCharacterManipulator>>(){}.getType();
        Collection<IDnDCharacterManipulator> list = gson.fromJson(mPrefs.getString("CharacterList", null), collectionType);
        characterList=(ArrayList)list;
    }
}
