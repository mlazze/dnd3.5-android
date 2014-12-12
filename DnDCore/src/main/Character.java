package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Character implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum STATS {
		STR, DEX, CON, INT, WIS, CHA
	}

	public static String statToString(STATS s) {
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
		REFLEX(1), FORTITUDE(2), WILL(4);
		private final int val;

		private SAVING(int v) {
			val = v;
		}

		public int getVal() {
			return val;
		}
	}

	// main stats
	private int[] stats;

	// savingthrows base stats
	private int[] savingthrowsbases;

	// infos
	private int level;
	private String name;
	private int runspeed;
	private HashMap<String,Integer> classes;
	private ArrayList<Integer> liferolls;
	private ArrayList<String> knownlanguages;
	private ArrayList<Integer> basicattackbonus;
	private int armorbonus;
	private int shieldbonus;
	private int naturalarmor;
	private int deflectionarmor;
	private int spellresist;
	private int damagereduction;

	// abilities
	private HashMap<Ability, Integer> abilities;
	private ArrayList<String> specialabilities;
	private ArrayList<Feat> feats;
	private ArrayList<Spell> knownspells;
	private ArrayList<HashMap<Spell, Integer>> chosenspells;

	// temporaries
	private ArrayList<Integer> temphitpoints;
	private int[] tempstats;
	private int[] tempsavingthrows;
	private ArrayList<String> tempstatuses;

	// equipment & inventory
	private ArrayList<Equipment> equipment;
	private ArrayList<Weapon> weapons;
	private HashMap<String, Integer> inventory;

	// other
	private int mischitpointsmax;
	private int miscAC;
	private int miscinitiative;
	private int[] miscsavingthrows;
	private int[] miscmagicsavingthrows;
	private int miscattackroll;

	// COSTR
	public Character(String name, String mainclass, int[] stats) {
		this.name = name;
		this.level = 1;
		liferolls = new ArrayList<Integer>(1);
		//implement classes api
		liferolls.add(12);
		this.runspeed = 12;
		classes=new HashMap<String, Integer>();
		classes.put(mainclass, 1);
		knownlanguages=new ArrayList<String>(1);
		knownlanguages.add("Common");
		basicattackbonus = new ArrayList<Integer>(1);
		basicattackbonus.add(1);
		this.stats = stats;
		this.savingthrowsbases = new int[] {0,0,0};
		abilities = new HashMap<Ability, Integer>(0);
		setDefaultAbilities();
		specialabilities = new ArrayList<String>(0);
		feats = new ArrayList<Feat>(0);
		knownspells = new ArrayList<Spell>(0);
		chosenspells = new ArrayList<HashMap<Spell,Integer>>(1);
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
		return;		
	}
	
	private boolean recalculate() {
		return false;
	}
	
	private boolean reset() {
		temphitpoints = new ArrayList<Integer>(0);
		tempstats = new int[6];
		tempsavingthrows = new int[3];
		tempstatuses = new ArrayList<String>(0);
		return false;
	}

	// METHODS
	public int getMod(STATS stat) throws InvalidCharacterException {
		if (stats[stat.ordinal()] <= 0)
			throw new InvalidCharacterException();
		int s = stats[stat.ordinal()] + tempstats[stat.ordinal()];
		return (s - 10) / 2;
	}

	public int getTotalHP() throws InvalidCharacterException {
		if (level <= 0)
			throw new InvalidCharacterException();
		if (liferolls.size() != level)
			throw new InvalidCharacterException();

		int res = 0;
		for (int i : liferolls) {
			res += i;
		}

		return res + getMod(STATS.CON) + mischitpointsmax;
	}

	public int getAC() throws InvalidCharacterException {
		return 10 + armorbonus + shieldbonus + getMod(STATS.DEX) + naturalarmor
				+ deflectionarmor + miscAC;
	}

	public int getTouch() throws InvalidCharacterException {
		return 10 + getMod(STATS.DEX) + deflectionarmor + miscAC;
	}

	public int getSprovvista() throws InvalidCharacterException {
		return 10 + armorbonus + shieldbonus + naturalarmor + deflectionarmor
				+ miscAC;
	}

	public int getcurrentHP() throws InvalidCharacterException {
		int res = getTotalHP();
		for (int i : temphitpoints)
			res += i;
		return res;
	}

	public int getInititative() throws InvalidCharacterException {
		return getMod(STATS.DEX) + miscinitiative;
	}

	public int getThrow(SAVING s) throws InvalidCharacterException {
		int index = s.ordinal();
		if (savingthrowsbases[index] <= 0)
			throw new InvalidCharacterException();

		return savingthrowsbases[index] + getMod(STATS.values()[index])
				+ miscmagicsavingthrows[index] + miscsavingthrows[index]
				+ tempsavingthrows[index];
	}

	public int getAttackBonus(Weapon w, int mods)
			throws InvalidCharacterException {
		return getAttackBonus(w, 0, mods);
	}

	public int getAttackBonus(Weapon w, int index, int mods)
			throws InvalidCharacterException {
		if (basicattackbonus.size() <= 0)
			throw new InvalidCharacterException();
		int baseattack = basicattackbonus.get(index);
		int mod = getMod(w.stat);
		int miscmod = miscattackroll;
		return baseattack + mod + miscmod + mods;
	}

	public ArrayList<Integer> getAttackBonuses(Weapon w, int mods)
			throws InvalidCharacterException {
		ArrayList<Integer> res = new ArrayList<Integer>(0);
		for (int i = 0; i < basicattackbonus.size(); i++)
			res.add(getAttackBonus(w, i, mods));
		return res;
	}

	public String getDamageNonCrit(Weapon w, String misc)
			throws InvalidCharacterException {
		if (w.ammo == null)
			return "Not enough ammo";
		String res = "";
		res += w.damagedices + " + ";
		if (misc != null)
			res += misc + " + ";
		return res + (getMod(w.stat) * w.damagemod);
	}

	public String getDamageCrit(Weapon w, String misc)
			throws InvalidCharacterException {
		if (w.ammo == null)
			return "Not enough ammo";
		String res = "(";
		res += w.damagedices + " + ";
		if (misc != null)
			res += misc + " + ";
		res += (getMod(w.stat) * w.damagemod) + ")";
		return res + "x" + w.critmult;
	}

	public int getAbilityMod(Ability a) throws InvalidCharacterException {
		if (abilities == null)
			throw new InvalidCharacterException();
		return abilities.get(a) + getMod(a.stat);
	}

	public ArrayList<String> getAbilities() throws InvalidCharacterException {
		ArrayList<String> res = new ArrayList<String>(0);
		for (Ability a : abilities.keySet())
			res.add(a.toString() + " mod" + getAbilityMod(a));
		return res;
	}

	public ArrayList<String> getEquipment() throws InvalidCharacterException {
		if (equipment == null)
			throw new InvalidCharacterException();
		ArrayList<String> res = new ArrayList<String>(0);
		for (Equipment a : equipment)
			res.add(a.toString());
		return res;
	}

	public ArrayList<String> getWeapons() throws InvalidCharacterException {
		if (weapons == null)
			throw new InvalidCharacterException();
		ArrayList<String> res = new ArrayList<String>(0);
		String temp = "";
		for (Weapon w : weapons) {
			temp = "";
			temp += w.toString() + "\n";
			temp += "ATK ROLLS: ";
			for (Integer i : getAttackBonuses(w, 0))
				temp += i + "/";
			temp = temp.substring(0, temp.length() - 1);
			res.add(w.toString());
		}
		return res;
	}

	public ArrayList<String> getFeats() throws InvalidCharacterException {
		if (feats == null)
			throw new InvalidCharacterException();
		ArrayList<String> res = new ArrayList<String>(0);
		for (Feat a : feats)
			res.add(a.toString());
		return res;
	}

	public ArrayList<String> getSpells() throws InvalidCharacterException {
		if (knownspells == null)
			throw new InvalidCharacterException();
		ArrayList<String> res = new ArrayList<String>(0);
		for (Spell a : knownspells)
			res.add(a.toString());
		return res;
	}

	public ArrayList<String> getSpellSets() throws InvalidCharacterException {
		if (chosenspells == null)
			throw new InvalidCharacterException();
		ArrayList<String> res = new ArrayList<String>(0);
		String temp;
		for (HashMap<Spell, Integer> h : chosenspells) {
			temp = "";
			for (Spell s : h.keySet())
				temp = s.toString() + " x" + h.get(s);
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

	public ArrayList<String> getLanguages() throws InvalidCharacterException {
		if (knownlanguages == null)
			throw new InvalidCharacterException();
		return knownlanguages;
	}

	public ArrayList<String> getInventory() throws InvalidCharacterException {
		if (inventory == null)
			throw new InvalidCharacterException();
		ArrayList<String> res = new ArrayList<String>(0);
		for (String a : inventory.keySet())
			res.add(a.toString() + " mod" + inventory.get(a));
		return res;
	}

	public ArrayList<String> getStatuses() throws InvalidCharacterException {
		if (tempstatuses == null)
			throw new InvalidCharacterException();
		return tempstatuses;
	}
	
	public boolean levelup(String newclass) {
		//clone
		return false;
	}

	public String toString() {
		String res = "";
		try {
			res += "====INFOS====\n";
			// infos
			res += "-Nome: " + name + "\n";
			for (String s : classes.keySet())
				res += "-Class: " + s + " level + " + classes.get(s) + "\n";
			res += "-Level: " + level + "\n";
			res += "-Run Speed: " + runspeed + "\n";

			// stats
			res += "====STATS====\n";
			for (STATS s : STATS.values())
				res += "-" + statToString(s) + ": " + stats[s.ordinal()]
						+ " | MOD: " + getMod(s) + "\n";

			// hp
			res += "====HP====\n";

			res += "-MAX Hp: " + getTotalHP() + "\n";
			res += "-Curr Hp: " + getcurrentHP() + "\n";
			res += "=HP LOG=\n";
			for (Integer i : liferolls) {
				if (i >= 0)
					res += "+";
				res += i + " ";
			}
			res += "\n";

			// statuses
			res += "====STATUSES====\n";
			for (String f : getStatuses())
				res += f + "\n\n";

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
			res += "-Damage Reduction: " + damagereduction + "\n";
			res += "-Spell Resist: " + spellresist + "\n";

			// attacks
			// basicattackbonus
			res += "====BASIC ATK BONUS====\n";
			for (Integer i : basicattackbonus)
				res += i + "/";
			res = res.substring(0, res.length() - 1);
			res += "\n";

			res += "====WEAPONS====\n";
			for (String w : getWeapons()) {
				res += w + "\n\n";
			}

			res += "====EQUIPMENT====\n";
			for (String e : getEquipment())
				res += e + "\n\n";

			res += "====ABILITIES====\n";
			for (String a : getAbilities())
				res += a + "\n\n";

			res += "====FEATS====\n";
			for (String f : getFeats())
				res += f + "\n\n";

			res += "====SPEC.ABILITIES====\n";
			for (String f : getSpecialAbilities())
				res += f + "\n\n";

			res += "====KNOWN SPELLS====\n";
			for (String f : getSpells())
				res += f + "\n\n";

			res += "====SPELL SETS====\n";
			for (String f : getSpellSets())
				res += "-" + f + "\n\n";
			res += "\n\n";

			res += "====INVENTORY====\n";
			for (String f : getInventory())
				res += f + "\n\n";

			res += "====LANGUAGES====\n";
			for (String f : getLanguages())
				res += f + "\n\n";
			res += "====END====\n";
		} catch (Exception e) {
			res += "CHARACTER NOT READY\n";
		}
		return res;
	}

}
