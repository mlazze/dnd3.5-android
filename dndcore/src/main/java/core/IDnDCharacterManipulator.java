package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import core.DnDCharacter.InvalidCharacterException;
import core.DnDCharacter.SAVING;
import core.DnDCharacter.STATS;

public interface IDnDCharacterManipulator extends Serializable {

    public void clearMisc();

    public void clearTemp();

    // getters
    public ArrayList<String> getAbilities();

    public int getAbilityMod(ABILITIES a);

    public int getAC();

    public int getAttackBonus(Weapon w, int mods);

    public int getAttackBonus(Weapon w, int index, int mods);

    public ArrayList<Integer> getAttackBonuses(Weapon w, int mods);

    public ArrayList<Integer> getBasicAttackBonuses();

    public Set<DNDCLASS> getClasses();

    public int getClassLevel(DNDCLASS s);

    public int getcurrentHP();

    public String getDamageCrit(Weapon w, String misc);

    public String getDamageNonCrit(Weapon w, String misc);

    public int getDamageReduction();

    public ArrayList<String> getEquipment();

    public ArrayList<String> getFeats();

    public int getGlobalLevel();

    public int getInititative();

    public ArrayList<String> getInventory();

    public ArrayList<String> getLanguages();

    public int getMod(STATS stat);

    public String getName();

    public int getRunspeed();

    public ArrayList<String> getSpecialAbilities()
            throws InvalidCharacterException;

    public int getSpellResist();

    public ArrayList<String> getSpells();

    public ArrayList<String> getSpellSets();

    public int getSprovvista();

    public int getStat(STATS stat);

    public ArrayList<String> getStatuses();

    public ArrayList<Integer> getTempHP();

    public int getThrow(SAVING s);

    public int getTotalHP();

    public int getTouch();

    public ArrayList<String> getWeapons();

    public String levelup(DNDCLASS improvedclass, int liferoll,
                          int[] newattackrolls, STATS newstat, int newstatdelta, int[] newsavingthrows);

    public void recalculate();

    public void setAbility(ABILITIES ability, boolean forget);

    public void setAbilitySkill(ABILITIES ability, int value);

    public void setAttackBonus(int index, int value, boolean clear);

    public void setChosenSpellsofIndex(Spell spell, int index, int quantity,
                                       boolean clear);

    public void setDamageReductionDelta(int value);

    public void setEquipment(Equipment pieceofequipment, boolean clear);

    public void setFeat(Feat feat, boolean clear);

    public void setInventory(String itemname, int quantity, boolean clear);

    public void setKnownLanguage(String language, boolean clear);

    public void setKnownSpell(Spell spell, boolean clear);

    public void setMiscACDelta(int value);

    public void setMiscAttackRollDelta(int value);

    public void setMiscHPMAXDelta(int value);

    public void setMiscInitiativeDelta(int value);

    public void setSavingThrowsDelta(SAVING s, int value, boolean magic);

    public void setSpecialAbility(String s, boolean clear);

    public void setSpellResistDelta(int value);

    public void setStat(STATS stat, int value);

    public void setStatDelta(STATS stat, int value);

    public void setTempACDelta(int value);

    public void setTempHPDelta(int value);

    public void setTempSavingDelta(SAVING saving, int value);

    public void setTempStatDelta(STATS stat, int value);

    public void setTempStatus(String s, boolean clear);

    public void setWeapon(Weapon w, boolean clear);
}
