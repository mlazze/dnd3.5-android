package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class DnDCharacter implements Serializable {
    private static final long serialVersionUID = 1L;
    // main stats
    protected int[] stats;
    // savingthrows base stats
    protected int[] savingthrowsbases;
    // infos
    protected int level;
    protected String name;
    protected int runspeed;
    protected int exp;
    protected HashMap<DNDCLASS, Integer> classes;
    protected ArrayList<Integer> liferolls;
    protected ArrayList<String> knownlanguages;
    protected ArrayList<Integer> basicattackbonus;
    protected int armorbonus;
    protected int shieldbonus;
    protected int naturalarmor;
    protected int deflectionarmor;
    protected int spellresist;
    protected int damagereduction;
    protected int tempAC;
    // abilities
    protected HashMap<ABILITIES, Integer> abilities;
    protected ArrayList<String> specialabilities;
    protected ArrayList<Feat> feats;
    protected ArrayList<Spell> knownspells;
    protected ArrayList<HashMap<Spell, Integer>> chosenspells;
    // temporaries
    protected ArrayList<Integer> temphitpoints;
    protected int[] tempstats;
    protected int[] tempsavingthrows;
    protected ArrayList<String> tempstatuses;
    // equipment & inventory
    protected ArrayList<Equipment> equipment;
    protected ArrayList<Weapon> weapons;
    protected HashMap<String, Integer> inventory;
    // other
    protected int mischitpointsmax;
    protected int miscAC;
    protected int miscinitiative;
    protected int[] miscsavingthrows;
    protected int[] miscmagicsavingthrows;
    protected int miscattackroll;
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

    public ArrayList<String> getAbilities() {
        ArrayList<String> res = new ArrayList<String>(0);
        for (ABILITIES a : abilities.keySet())
            res.add(a.toString() + " mod" + getAbilityMod(a));
        Collections.sort(res);
        return res;
    }

    public int getAbilityMod(ABILITIES a) {
        if (abilities == null)
            throw new InvalidCharacterException();
        int malus = 0;
        if (a.hasMalus())
            for (Equipment e : equipment)
                if (e.savepenalty > malus)
                    malus = e.savepenalty;

        if (a == ABILITIES.NUOTARE)
            malus *= 2;

        return abilities.get(a) + getMod(a.getStat()) - malus;
    }

    public int getAC() {
        return 10 + armorbonus + shieldbonus + getMod(STATS.DEX) + naturalarmor
                + deflectionarmor + miscAC + tempAC;
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

    public ArrayList<Integer> getBasicAttackBonuses() {
        return basicattackbonus;
    }

    public Set<DNDCLASS> getClasses() {
        return classes.keySet();
    }

    public int getClassLevel(DNDCLASS s) {
        return classes.get(s);
    }

    public int getcurrentHP() {
        int res = getTotalHP();
        for (int i : temphitpoints)
            res += i;
        return res;
    }

    public String getDamageCrit(Weapon w, String misc) {
        if (w.ammo == null)
            return "Not enough ammo";
        String res = "(";
        res += w.damagedices + " + ";
        if (misc != null)
            res += misc + " + ";
        res += (getMod(w.stat) * w.damagemod) + ")";
        return res + "x" + w.critmult + " (" + w.notes + ")";
    }

    public String getDamageNonCrit(Weapon w, String misc) {
        if (w.ranged && w.ammo == null)
            throw new NotEnoughAmmoException();
        String res = "";
        res += w.damagedices + " + ";
        if (misc != null)
            res += misc + " + ";
        return res + (getMod(w.stat) * w.damagemod) + " (" + w.notes + ")";
    }

    public int getDamageReduction() {
        return damagereduction;
    }

    public ArrayList<String> getEquipment() {
        if (equipment == null)
            throw new InvalidCharacterException();
        ArrayList<String> res = new ArrayList<String>(0);
        for (Equipment a : equipment)
            res.add(a.toString());
        return res;
    }

    public int getExp() {
        return exp;
    }

    public ArrayList<String> getFeats() {
        if (feats == null)
            throw new InvalidCharacterException();
        ArrayList<String> res = new ArrayList<String>(0);
        for (Feat a : feats)
            res.add(a.toString());
        return res;
    }

    public int getGlobalLevel() {
        return level;
    }

    public int getInititative() {
        return getMod(STATS.DEX) + miscinitiative;
    }

    public ArrayList<String> getInventory() {
        if (inventory == null)
            throw new InvalidCharacterException();
        ArrayList<String> res = new ArrayList<String>(0);
        for (String a : inventory.keySet())
            res.add(a + " mod" + inventory.get(a));
        return res;
    }

    public ArrayList<String> getLanguages() {
        if (knownlanguages == null)
            throw new InvalidCharacterException();
        return knownlanguages;
    }

    // METHODS
    public int getMod(STATS stat) {
        int result = (getStat(stat) - 10) / 2;

        if (stat == STATS.DEX) {
            int max = 999;

            for (Equipment e : equipment)
                if (e.maxdex < max)
                    max = e.maxdex;
            result = result > max ? max : result;
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public int getRunspeed() {
        return runspeed;
    }

    public ArrayList<String> getSpecialAbilities()
            throws InvalidCharacterException {
        if (specialabilities == null)
            throw new InvalidCharacterException();
        return specialabilities;
    }

    public int getSpellResist() {
        return spellresist;
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

    public int getSprovvista() {
        return getAC() - getMod(STATS.DEX);
    }

    public int getStat(STATS stat) {
        if (stats[stat.ordinal()] <= 0)
            throw new InvalidCharacterException();
        return stats[stat.ordinal()] + tempstats[stat.ordinal()];
    }

    public ArrayList<String> getStatuses() {
        if (tempstatuses == null)
            throw new InvalidCharacterException();
        return tempstatuses;
    }

    public ArrayList<Integer> getTempHP() {
        return temphitpoints;
    }

    public int getThrow(SAVING s) {
        int index = s.ordinal();
        if (savingthrowsbases[index] < 0)
            throw new InvalidCharacterException();

        return savingthrowsbases[index] + getMod(STATS.values()[s.getVal()])
                + miscmagicsavingthrows[index] + miscsavingthrows[index]
                + tempsavingthrows[index];
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

    public int getTouch() {
        return getAC() - armorbonus - shieldbonus - naturalarmor;
        // return 10 + getMod(STATS.DEX) + deflectionarmor + miscAC;
    }

    public ArrayList<String> getWeapons() {
        if (weapons == null)
            throw new InvalidCharacterException();
        ArrayList<String> res = new ArrayList<String>(0);
        String temp;
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

    private void setDefaultAbilities() {
        for (ABILITIES a : ABILITIES.values()) {
            if (a.isDef()) {
                abilities.put(a, 0);
            }
        }
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

    protected void variousChecks() {
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

    public static enum STATS {
        STR, DEX, CON, INT, WIS, CHA;

        public String toString() {
            switch (this) {
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
    }

    public static class InvalidCharacterException extends RuntimeException {

    }

    public static class NotEnoughAmmoException extends RuntimeException {
    }

}
