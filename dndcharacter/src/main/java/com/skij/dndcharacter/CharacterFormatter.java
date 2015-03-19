package com.skij.dndcharacter;

import java.util.Arrays;
import java.util.List;

import core.ABILITIES;
import core.DNDCLASS;
import core.DnDCharacter;
import core.DnDCharacterManipulator;
import core.Weapon;

public class CharacterFormatter {
    DnDCharacterManipulator character;

    public CharacterFormatter(DnDCharacterManipulator character) {
        this.character = character;
    }

    public String formatAsList(String label, List list, String entryseparator, String labelseparator, String preentryseparator) {
        String res = "";
        res += label + ":" + labelseparator;
        for (Object s : list)
            res += preentryseparator + s.toString() + entryseparator;
        int sizetoberemoved = entryseparator.length();
        if (entryseparator.equals("\n"))
            sizetoberemoved = 1;
        return res.substring(0, res.length() - sizetoberemoved);
    }

    public String formatSpells(int index) {
        try {
            return formatAsList("Spells", character.getSpellSet(index), "\n", "\n", "  ");
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public String formatAbilities() {
        String res = "";
        ABILITIES[] set = character.getAbilities().keySet().toArray(new ABILITIES[character.getAbilities().keySet().size()]);
        Arrays.sort(set);
        for (ABILITIES a : set)
            res += a.toString() + ": " + character.getAbilityMod(a) + "\n";
        return res.substring(0, res.length() - 1);
    }

    public String formatWeapon(int index) {
        Weapon w;
        String res = "";
        try {
            w = character.getWeapon(index);
            res += w.name + " [";
            for (Integer i : character.getAttackBonuses(w, 0))
                res += i + "/";
            res = res.substring(0, res.length() - 1);
            res += "]\n[";
            res += character.getDamageNonCrit(w, null) + "]\n";
            res += w.toString();
            return res;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public String format(String label, String value) {
        return label + ": " + value;
    }

    public String format(String label, int value) {
        return format(label, "" + value);
    }

    public String formatStat(DnDCharacter.STATS s, String label) {
        return label + ": " + character.getStat(s) + " | " + character.getMod(s);
    }

    public String formatSaving(DnDCharacter.SAVING s, String label) {
        return format(label, character.getThrow(s));
    }

    public String formatClasses() {
        String res = "";
        for (DNDCLASS c : character.getClasses()) {
            res += character.getClassName(c) + " " + character.getClassLevel(c) + " | ";
        }
        return res.substring(0, res.length() - 2);
    }
}
