package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import main.Equipment.TYPE;

public class DnDCharacter implements Serializable {
	public static class NotEnoughAmmoException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	public static class InvalidCharacterException extends RuntimeException {
		private static final long serialVersionUID = 1L;

	}

	private static final long serialVersionUID = 1L;

	public static enum STATS {
		STR, DEX, CON, INT, WIS, CHA
	}

	private static String statToString(STATS s) {
		switch (s) {
		case STR:
			return "Strenght";
		case DEX:
			return "Dexterity";
		case CON:
			return "Constitution";
		case INT:
			return "Intelligence";
		case WIS:
			return "Wisdom";
		case CHA:
			return "Charisma";
		default:
			return null;
		}
	}

	public static enum SAVING {
		FORTITUDE(2), REFLEX(1), WILL(4);
		private final int val;

		private SAVING(int v) {
			val = v;
		}

		public int getVal() {
			return val;
		}
	}

	// main stats
	public int[] stats;

	// savingthrows base stats
	public int[] savingthrowsbases;

	// infos
	public int level;
	public String name;
	public int runspeed;
	public HashMap<DNDCLASS, Integer> classes;
	public ArrayList<Integer> liferolls;
	public ArrayList<String> knownlanguages;
	public ArrayList<Integer> basicattackbonus;
	public int armorbonus;
	public int shieldbonus;
	public int naturalarmor;
	public int deflectionarmor;
	public int spellresist;
	public int damagereduction;
	public int tempAC;

	// abilities
	public HashMap<ABILITIES, Integer> abilities;
	public ArrayList<String> specialabilities;
	public ArrayList<Feat> feats;
	public ArrayList<Spell> knownspells;
	public ArrayList<HashMap<Spell, Integer>> chosenspells;

	// temporaries
	public ArrayList<Integer> temphitpoints;
	public int[] tempstats;
	public int[] tempsavingthrows;
	public ArrayList<String> tempstatuses;

	// equipment & inventory
	public ArrayList<Equipment> equipment;
	public ArrayList<Weapon> weapons;
	public HashMap<String, Integer> inventory;

	// other
	public int mischitpointsmax;
	public int miscAC;
	public int miscinitiative;
	public int[] miscsavingthrows;
	public int[] miscmagicsavingthrows;
	public int miscattackroll;

	// COSTR
	public DnDCharacter(String name, DNDCLASS mainclass, int[] stats,
			int runspeed, int[] savingthrowsbases) {
		this.name = name;
		this.level = 1;
		liferolls = new ArrayList<Integer>(1);
		// implement classes api
		liferolls.add(mainclass.getLifeDice());
		this.runspeed = runspeed;
		classes = new HashMap<DNDCLASS, Integer>();
		classes.put(mainclass, 1);
		this.stats = stats;
		this.savingthrowsbases = savingthrowsbases;
		setupEmptyCharacter();
	}

	private void setupEmptyCharacter() {
		knownlanguages = new ArrayList<String>(1);
		knownlanguages.add("Common");
		basicattackbonus = new ArrayList<Integer>(1);
		basicattackbonus.add(1);
		abilities = new HashMap<ABILITIES, Integer>(0);
		setDefaultAbilities();
		specialabilities = new ArrayList<String>(0);
		feats = new ArrayList<Feat>(0);
		knownspells = new ArrayList<Spell>(0);
		chosenspells = new ArrayList<HashMap<Spell, Integer>>(1);
		chosenspells.add(new HashMap<Spell, Integer>(0));
		temphitpoints = new ArrayList<Integer>(0);
		tempstats = new int[6];
		tempsavingthrows = new int[3];
		tempstatuses = new ArrayList<String>(0);
		equipment = new ArrayList<Equipment>(0);
		weapons = new ArrayList<Weapon>(0);
		inventory = new HashMap<String, Integer>(0);
		miscsavingthrows = new int[3];
		miscmagicsavingthrows = new int[3];
	}

	private void setDefaultAbilities() {
		for (ABILITIES a : ABILITIES.values()) {
			if (a.isDef()) {
				abilities.put(a, 0);
			}
		}
	}

	public void recalculate() {
		// DnDCharacter bak = (DnDCharacter) this.clone();

		try {
			getcurrentHP(); // just to check
			getAbilities();
			for (Weapon w : weapons) {
				getAttackBonuses(w, 0);
				getDamageCrit(w, null);
			}
			getEquipment();
			getFeats();
			getInititative();
			getInventory();
			getSpecialAbilities();
			getSpells();
			getSpellSets();
			getSprovvista();
			getStatuses();
			getTotalHP();
			getWeapons();
			getTouch();
			getAC();
			calculateAC();
			getAC();
			variousChecks();

		} catch (Exception e) {
			// this = bak;
			throw e;
		}
	}

	private void variousChecks() {
		boolean raise = false;

		//
		int level = 0;
		for (int i : classes.values())
			level += i;
		if (level != this.level)
			raise = true;
		//

		if (raise)
			throw new InvalidCharacterException();
	}

	private void calculateAC() {
		int temp = 0;
		for (Equipment e : equipment) {
			if (e.type != TYPE.SHIELD)
				temp += e.acbonus;
		}
		armorbonus = temp;

		temp = 0;
		for (Equipment e : equipment) {
			if (e.type == TYPE.SHIELD)
				temp += e.acbonus;
		}
		shieldbonus = temp;

		temp = 0;
		for (Equipment e : equipment) {

			temp += e.naturalbonus;
		}
		naturalarmor = temp;

		temp = 0;
		for (Equipment e : equipment) {

			temp += e.deflectionbonus;
		}
		deflectionarmor = temp;

	}

	public boolean resettemp() {
		temphitpoints = new ArrayList<Integer>(0);
		tempstats = new int[6];
		tempsavingthrows = new int[3];
		tempstatuses = new ArrayList<String>(0);
		tempAC = 0;
		return true;
	}

	// METHODS
	public int getMod(STATS stat) {
		return (getStat(stat) - 10) / 2;
	}

	public int getStat(STATS stat) {
		if (stats[stat.ordinal()] <= 0)
			throw new InvalidCharacterException();
		int s = stats[stat.ordinal()] + tempstats[stat.ordinal()];
		return s;
	}

	public int getTotalHP() {
		if (level <= 0)
			throw new InvalidCharacterException();
		if (liferolls.size() != level)
			throw new InvalidCharacterException();

		int res = 0;
		for (int i : liferolls) {
			res += i;
		}

		return res + getMod(STATS.CON) * level + mischitpointsmax;
	}

	public int getAC() {
		return 10 + armorbonus + shieldbonus + getMod(STATS.DEX) + naturalarmor
				+ deflectionarmor + miscAC + tempAC;
	}

	public int getTouch() {
		return getAC() - armorbonus - shieldbonus - naturalarmor;
		// return 10 + getMod(STATS.DEX) + deflectionarmor + miscAC;
	}

	public int getSprovvista() {
		return getAC() - getMod(STATS.DEX);
	}

	public int getcurrentHP() {
		int res = getTotalHP();
		for (int i : temphitpoints)
			res += i;
		return res;
	}

	public int getInititative() {
		return getMod(STATS.DEX) + miscinitiative;
	}

	public int getThrow(SAVING s) {
		int index = s.ordinal();
		if (savingthrowsbases[index] < 0)
			throw new InvalidCharacterException();

		return savingthrowsbases[index] + getMod(STATS.values()[s.getVal()])
				+ miscmagicsavingthrows[index] + miscsavingthrows[index]
				+ tempsavingthrows[index];
	}

	public int getAttackBonus(Weapon w, int mods) {
		return getAttackBonus(w, 0, mods);
	}

	public int getAttackBonus(Weapon w, int index, int mods) {
		if (basicattackbonus.size() <= 0)
			throw new InvalidCharacterException();
		int baseattack = basicattackbonus.get(index);
		int mod = getMod(w.stat);
		int miscmod = miscattackroll;
		return baseattack + mod + miscmod + mods;
	}

	public ArrayList<Integer> getAttackBonuses(Weapon w, int mods) {
		ArrayList<Integer> res = new ArrayList<Integer>(0);
		for (int i = 0; i < basicattackbonus.size(); i++)
			res.add(getAttackBonus(w, i, mods));
		return res;
	}

	public String getDamageNonCrit(Weapon w, String misc) {
		if (w.ranged && w.ammo == null)
			throw new NotEnoughAmmoException();
		String res = "";
		res += w.damagedices + " + ";
		if (misc != null)
			res += misc + " + ";
		return res + (getMod(w.stat) * w.damagemod);
	}

	public String getDamageCrit(Weapon w, String misc) {
		if (w.ammo == null)
			return "Not enough ammo";
		String res = "(";
		res += w.damagedices + " + ";
		if (misc != null)
			res += misc + " + ";
		res += (getMod(w.stat) * w.damagemod) + ")";
		return res + "x" + w.critmult;
	}

	public int getAbilityMod(ABILITIES a) {
		if (abilities == null)
			throw new InvalidCharacterException();
		return abilities.get(a) + getMod(a.getStat());
	}

	public ArrayList<String> getAbilities() {
		ArrayList<String> res = new ArrayList<String>(0);
		for (ABILITIES a : abilities.keySet())
			res.add(a.toString() + " mod" + getAbilityMod(a));
		Collections.sort(res);
		return res;
	}

	public ArrayList<String> getEquipment() {
		if (equipment == null)
			throw new InvalidCharacterException();
		ArrayList<String> res = new ArrayList<String>(0);
		for (Equipment a : equipment)
			res.add(a.toString());
		return res;
	}

	public ArrayList<String> getWeapons() {
		if (weapons == null)
			throw new InvalidCharacterException();
		ArrayList<String> res = new ArrayList<String>(0);
		String temp = "";
		for (Weapon w : weapons) {
			temp = "";
			temp += "[ATK ROLLS: ";
			for (Integer i : getAttackBonuses(w, 0))
				temp += i + "/";
			temp = temp.substring(0, temp.length() - 1);
			temp += "] [";
			temp += getDamageNonCrit(w, null) + "] ";
			temp += w.toString();

			res.add(temp);
		}
		return res;
	}

	public ArrayList<String> getFeats() {
		if (feats == null)
			throw new InvalidCharacterException();
		ArrayList<String> res = new ArrayList<String>(0);
		for (Feat a : feats)
			res.add(a.toString());
		return res;
	}

	public ArrayList<String> getSpells() {
		if (knownspells == null)
			throw new InvalidCharacterException();
		ArrayList<String> res = new ArrayList<String>(0);
		for (Spell a : knownspells)
			res.add(a.toString());
		return res;
	}

	public ArrayList<String> getSpellSets() {
		if (chosenspells == null)
			throw new InvalidCharacterException();
		ArrayList<String> res = new ArrayList<String>(0);
		String temp;
		for (HashMap<Spell, Integer> h : chosenspells) {
			temp = "";
			for (Spell s : h.keySet())
				temp += s.toString() + " x" + h.get(s);
			if (!temp.equals(""))
				res.add(temp);
		}
		return res;
	}

	public ArrayList<String> getSpecialAbilities()
			throws InvalidCharacterException {
		if (specialabilities == null)
			throw new InvalidCharacterException();
		return specialabilities;
	}

	public ArrayList<String> getLanguages() {
		if (knownlanguages == null)
			throw new InvalidCharacterException();
		return knownlanguages;
	}

	public ArrayList<String> getInventory() {
		if (inventory == null)
			throw new InvalidCharacterException();
		ArrayList<String> res = new ArrayList<String>(0);
		for (String a : inventory.keySet())
			res.add(a.toString() + " mod" + inventory.get(a));
		return res;
	}

	public ArrayList<String> getStatuses() {
		if (tempstatuses == null)
			throw new InvalidCharacterException();
		return tempstatuses;
	}

	public boolean levelup(String newclass) {
		// clone
		return false;
	}

	public String toString() {
		String res = "";
		try {
			res += "====INFOS====\n";
			// infos
			res += "-Nome: " + getName() + "\n";
			for (DNDCLASS s : getClasses())
				res += "-Class: " + s + " level " + getClassLevel(s) + "\n";
			res += "-ToTLevel: " + getGlobalLevel() + "\n";
			res += "-Run Speed: " + getRunspeed() + "\n";

			// stats
			res += "====STATS====\n";
			for (STATS s : STATS.values())
				res += "-" + statToString(s) + ": " + getStat(s) + " | MOD: "
						+ getMod(s) + "\n";

			// hp
			res += "====HP====\n";

			res += "-MAX Hp: " + getTotalHP() + "\n";
			res += "-Curr Hp: " + getcurrentHP() + "\n";
			res += "===HP LOG===\n";
			for (int i : getTempHP()) {
				if (i >= 0)
					res += "+";
				res += i + " ";
			}

			// statuses
			res += "====STATUSES====\n";
			for (String f : getStatuses())
				res += f + "\n";

			// rolls
			res += "====S.THROWS====\n";
			for (SAVING s : SAVING.values())
				res += "-" + s + ": " + getThrow(s) + "\n";
			res += "-Initiative: " + getInititative() + "\n";

			// AC
			res += "====Armor====\n";
			res += "-AC: " + getAC() + "\n";
			res += "-Touch: " + getTouch() + "\n";
			res += "-Sprovvista: " + getSprovvista() + "\n";
			res += "-Damage Reduction: " + getDamageReduction() + "\n";
			res += "-Spell Resist: " + getSpellResist() + "\n";

			// attacks
			// basicattackbonus
			res += "====BASIC ATK BONUS====\n";
			for (Integer i : getBasicAttackBonuses())
				res += i + "/";
			res = res.substring(0, res.length() - 1);
			res += "\n";

			res += "====WEAPONS====\n";
			for (String w : getWeapons()) {
				res += w + "\n";
			}

			res += "====EQUIPMENT====\n";
			for (String e : getEquipment())
				res += e + "\n";

			res += "====ABILITIES====\n";
			for (String a : getAbilities())
				res += a + "\n";

			res += "====FEATS====\n";
			for (String f : getFeats())
				res += f + "\n";

			res += "====SPEC.ABILITIES====\n";
			for (String f : getSpecialAbilities())
				res += f + "\n";

			res += "====KNOWN SPELLS====\n";
			for (String f : getSpells())
				res += f + "\n";

			res += "====SPELL SETS====\n";
			for (String f : getSpellSets())
				res += "-" + f + "\n";

			res += "====INVENTORY====\n";
			for (String f : getInventory())
				res += f + "\n";

			res += "====LANGUAGES====\n";
			for (String f : getLanguages())
				res += f + "\n";
			res += "====END====\n";
		} catch (Exception e) {
			res += "CHARACTER NOT READY\n";
		}
		return res;
	}

	private int getClassLevel(DNDCLASS s) {
		return classes.get(s);
	}

	private int getRunspeed() {
		return runspeed;
	}

	private int getGlobalLevel() {
		return level;
	}

	private Set<DNDCLASS> getClasses() {
		return classes.keySet();
	}

	private String getName() {
		return name;
	}

	private ArrayList<Integer> getTempHP() {
		return temphitpoints;
	}

	private int getSpellResist() {
		return spellresist;
	}

	private int getDamageReduction() {
		return damagereduction;
	}

	private ArrayList<Integer> getBasicAttackBonuses() {
		return basicattackbonus;
	}

}
