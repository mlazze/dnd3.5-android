package main;

import java.util.ArrayList;
import java.util.Set;

import main.DnDCharacter.InvalidCharacterException;
import main.DnDCharacter.SAVING;
import main.DnDCharacter.STATS;

public interface IDnDCharacterManipulator {

	public void clearMisc();

	boolean clearTemp();

	// getters
	public ArrayList<String> getAbilities();

	int getAbilityMod(ABILITIES a);

	int getAC();

	int getAttackBonus(Weapon w, int mods);

	int getAttackBonus(Weapon w, int index, int mods);

	ArrayList<Integer> getAttackBonuses(Weapon w, int mods);

	ArrayList<Integer> getBasicAttackBonuses();

	Set<DNDCLASS> getClasses();

	int getClassLevel(DNDCLASS s);

	int getcurrentHP();

	String getDamageCrit(Weapon w, String misc);

	String getDamageNonCrit(Weapon w, String misc);

	int getDamageReduction();

	ArrayList<String> getEquipment();

	ArrayList<String> getFeats();

	int getGlobalLevel();

	int getInititative();

	ArrayList<String> getInventory();

	ArrayList<String> getLanguages();

	int getMod(STATS stat);

	String getName();

	int getRunspeed();

	ArrayList<String> getSpecialAbilities() throws InvalidCharacterException;

	int getSpellResist();

	ArrayList<String> getSpells();

	ArrayList<String> getSpellSets();

	int getSprovvista();

	int getStat(STATS stat);

	ArrayList<String> getStatuses();

	ArrayList<Integer> getTempHP();

	int getThrow(SAVING s);

	int getTotalHP();

	int getTouch();

	ArrayList<String> getWeapons();

	void levelup(DNDCLASS improvedclass, int liferoll, int[] newattackrolls,
			STATS newstat, int newstatdelta);

	void recalculate();

	void setAbility(ABILITIES ability, boolean forget);

	void setAbilitySkill(ABILITIES ability, int value);

	void setAttackBonus(int index, int value, boolean clear);

	void setChosenSpellsofIndex(Spell spell, int index, int quantity,
			boolean clear);

	void setDamageReductionDelta(int value);

	void setEquipment(Equipment pieceofequipment, boolean clear);

	void setFeat(Feat feat, boolean clear);

	void setInventory(String itemname, int quantity, boolean clear);

	void setKnownLanguage(String language, boolean clear);

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
