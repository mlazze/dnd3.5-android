package main;

import java.util.ArrayList;
import java.util.HashMap;

import main.Equipment.TYPE;

public class DnDCharacterManipulator extends DnDCharacter implements
		IDnDCharacterManipulator {

	private static final long serialVersionUID = 1L;

	public DnDCharacterManipulator(String name, DNDCLASS mainclass,
			int[] stats, int runspeed, int[] savingthrowsbases) {
		super(name, mainclass, stats, runspeed, savingthrowsbases);
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

	public void clearMisc() {
		miscAC = 0;
		miscattackroll = 0;
		mischitpointsmax = 0;
		miscinitiative = 0;
		miscmagicsavingthrows = new int[] { 0, 0, 0 };
		miscsavingthrows = new int[] { 0, 0, 0 };
	}

	public boolean clearTemp() {
		temphitpoints = new ArrayList<Integer>(0);
		tempstats = new int[6];
		tempsavingthrows = new int[3];
		tempstatuses = new ArrayList<String>(0);
		tempAC = 0;
		return true;
	}

	public void levelup(DNDCLASS improvedclass, int liferoll,
			int[] newattackrolls, STATS newstat, int newstatdelta) {
		clearTemp();
		recalculate();

		if (liferoll > improvedclass.getLifeDice())
			throw new DnDCharacter.InvalidCharacterException();

		liferolls.add(liferoll);
		level++;
		classes.put(improvedclass, classes.get(improvedclass) + 1);
		if (newattackrolls != null) {
			basicattackbonus = new ArrayList<Integer>(0);
			for (int i : newattackrolls)
				basicattackbonus.add(i);
		}
		if (newstat != null) {
			stats[newstat.ordinal()] += newstatdelta;
		}
		clearTemp();
		recalculate();

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

	public void setAbility(ABILITIES ability, boolean forget) {
		if (forget)
			abilities.remove(ability);
		else
			abilities.put(ability, 0);

	}

	public void setAbilitySkill(ABILITIES ability, int value) {
		abilities.put(ability, value);
	}

	public void setAttackBonus(int index, int value, boolean clear) {
		if (clear)
			basicattackbonus.remove(index);
		else if (basicattackbonus.size() > index)
			basicattackbonus.set(index, value);
		else
			basicattackbonus.add(value);

	}

	public void setChosenSpellsofIndex(Spell spell, int index, int quantity,
			boolean clear) {
		HashMap<Spell, Integer> temp = chosenspells.get(index);
		if (clear)
			temp.remove(spell);
		else
			temp.put(spell, quantity);

	}

	public void setDamageReductionDelta(int value) {
		damagereduction += value;
	}

	public void setEquipment(Equipment pieceofequipment, boolean clear) {
		if (clear)
			equipment.remove(pieceofequipment);
		else if (!equipment.contains(pieceofequipment))
			equipment.add(pieceofequipment);
	}

	public void setFeat(Feat feat, boolean clear) {
		if (clear)
			feats.remove(feat);
		else if (!feats.contains(feat))
			feats.add(feat);
	}

	public void setInventory(String itemname, int quantity, boolean clear) {
		if (clear)
			inventory.remove(itemname);
		else
			inventory.put(itemname, quantity);
	}

	public void setKnownLanguage(String language, boolean clear) {
		if (clear)
			knownlanguages.remove(language);
		else if (!knownlanguages.contains(language))
			knownlanguages.add(language);
	}

	public void setKnownSpell(Spell spell, boolean clear) {
		knownspells = new ArrayList<Spell>();
		if (clear)
			knownspells.remove(spell);
		else if (!knownspells.contains(spell))
			knownspells.add(spell);
	}

	public void setMiscACDelta(int value) {
		miscAC += value;
	}

	public void setMiscAttackRollDelta(int value) {
		miscattackroll += value;

	}

	public void setMiscHPMAXDelta(int value) {
		mischitpointsmax += value;
	}

	public void setMiscInitiativeDelta(int value) {
		miscinitiative += value;
	}

	public void setSavingThrowsDelta(SAVING s, int value, boolean magic) {
		if (!magic)
			miscsavingthrows[s.ordinal()] += value;
		else
			miscmagicsavingthrows[s.ordinal()] += value;
	}

	public void setSpecialAbility(String s, boolean clear) {
		if (clear)
			specialabilities.remove(s);
		else if (!specialabilities.contains(s))
			specialabilities.add(s);
	}

	public void setSpellResistDelta(int value) {
		spellresist += value;
	}

	public void setStat(STATS stat, int value) {
		stats[stat.ordinal()] = value;
	}

	public void setStatDelta(STATS stat, int value) {
		stats[stat.ordinal()] += value;
	}

	public void setTempACDelta(int value) {
		tempAC += value;
	}

	public void setTempHPDelta(int value) {
		temphitpoints.add(value);
	}

	public void setTempSavingDelta(SAVING saving, int value) {
		tempsavingthrows[saving.ordinal()] += value;
	}

	public void setTempStatDelta(STATS stat, int value) {
		tempstats[stat.ordinal()] += value;
	}

	public void setTempStatus(String s, boolean clear) {
		if (clear)
			tempstatuses.remove(s);
		else if (!tempstatuses.contains(s))
			tempstatuses.add(s);
	}

	public void setWeapon(Weapon w, boolean clear) {
		if (clear)
			weapons.remove(w);
		else if (!weapons.contains(w))
			weapons.add(w);
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
				res += "-" + s.toString() + ": " + getStat(s) + " | MOD: "
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
}
