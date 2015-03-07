package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DnDCharacterManipulator extends DnDCharacter implements
        Serializable {

    public DnDCharacterManipulator(String name, String race, DNDCLASS mainclass,
                                   int[] stats, int runspeed, int[] savingthrowsbases) {
        super(name, race, mainclass, stats, runspeed, savingthrowsbases);
        recalculate();
    }

    public DnDCharacterManipulator(String name, String race, DNDCLASS mainclass, int[] stats,
                                   int runspeed, int[] savingthrowsbases, String classname) {
        super(name, race, mainclass, stats, runspeed, savingthrowsbases);
        classToNames.put(mainclass, classname);
        recalculate();
    }

    private void calculateAC() {
        int temp = 0;
        for (Equipment e : equipment) {
            if (e.type != Equipment.TYPE.SHIELD)
                temp += e.acbonus;
        }
        armorbonus = temp;

        temp = 0;
        for (Equipment e : equipment) {
            if (e.type == Equipment.TYPE.SHIELD)
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
        miscmagicsavingthrows = new int[]{0, 0, 0};
        miscsavingthrows = new int[]{0, 0, 0};
        recalculate();
    }

    public void clearTemp() {
        temphitpoints = new ArrayList<Integer>(0);
        tempstats = new int[6];
        tempsavingthrows = new int[3];
        tempstatuses = new ArrayList<String>(0);
        tempAC = 0;
        recalculate();
    }


    public String levelup(DNDCLASS improvedclass, int liferoll,
                          ArrayList<Integer> newattackrolls, STATS newstat, int newstatdelta, int[] newsavingthrows) {
        clearMisc();
        clearTemp();
        if (liferoll > improvedclass.getLifeDice())
            throw new DnDCharacter.InvalidCharacterException();

        liferolls.add(liferoll);
        level++;
        int oldclasslevel = classes.get(improvedclass) == null ? 0 : classes.get(improvedclass);
        classes.put(improvedclass, oldclasslevel + 1);
        if (newattackrolls != null) {
            basicattackbonus = new ArrayList<Integer>(0);
            for (int i : newattackrolls)
                basicattackbonus.add(i);
        }
        if (newstat != null) {
            stats[newstat.ordinal()] += newstatdelta;
        }
        if (newsavingthrows != null) {
            savingthrowsbases = newsavingthrows;
        }
        clearMisc();
        clearTemp();

        return improvedclass.getLevelUpInfos();
    }

    public String levelup(DNDCLASS improvedclass, String customclass, int liferoll,
                          ArrayList<Integer> newattackrolls, STATS newstat, int newstatdelta, int[] newsavingthrows) {
        String ret = levelup(improvedclass, liferoll, newattackrolls, newstat, newstatdelta, newsavingthrows);
        classToNames.put(improvedclass, customclass);
        return ret;
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
            try {
                throw e;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void setAbility(ABILITIES ability, boolean forget) {
        if (forget)
            abilities.remove(ability);
        else
            abilities.put(ability, 0);
        recalculate();

    }

    public void setAbilitySkill(ABILITIES ability, int value) {
        abilities.put(ability, value);
        recalculate();
    }

    public void setAttackBonus(int index, int value, boolean clear) {
        if (clear)
            basicattackbonus.remove(index);
        else if (basicattackbonus.size() > index)
            basicattackbonus.set(index, value);
        else
            basicattackbonus.add(value);
        recalculate();

    }

    public void setChosenSpellsofIndex(Spell spell, int index, int quantity,
                                       boolean clear) {
        HashMap<Spell, Integer> temp = chosenspells.get(index);
        if (clear)
            temp.remove(spell);
        else
            temp.put(spell, quantity);
        recalculate();

    }

    public void setDamageReductionDelta(int value) {
        damagereduction += value;
        recalculate();
    }

    public void setEquipment(Equipment pieceofequipment, boolean clear) {
        if (clear)
            equipment.remove(pieceofequipment);
        else if (!equipment.contains(pieceofequipment))
            equipment.add(pieceofequipment);
        recalculate();
    }

    public void setExpDelta(int value) {
        exp += value;
    }

    public void setFeat(Feat feat, boolean clear) {
        if (clear)
            feats.remove(feat);
        else if (!feats.contains(feat))
            feats.add(feat);
        recalculate();
    }

    public void setInventory(String itemname, int quantity, boolean clear) {
        if (clear)
            inventory.remove(itemname);
        else
            inventory.put(itemname, quantity);
        recalculate();
    }

    public void setKnownLanguage(String language, boolean clear) {
        if (clear)
            knownlanguages.remove(language);
        else if (!knownlanguages.contains(language))
            knownlanguages.add(language);
        recalculate();
    }

    public void setKnownSpell(Spell spell, boolean clear) {
        knownspells = new ArrayList<Spell>();
        if (clear)
            knownspells.remove(spell);
        else if (!knownspells.contains(spell))
            knownspells.add(spell);
        recalculate();
    }

    public void setMiscACDelta(int value) {
        miscAC += value;
        recalculate();
    }

    public void setMiscAttackRollDelta(int value) {
        miscattackroll += value;
        recalculate();

    }

    public void setMiscHPMAXDelta(int value) {
        mischitpointsmax += value;
        recalculate();
    }

    public void setMiscInitiativeDelta(int value) {
        miscinitiative += value;
        recalculate();
    }

    public void setSavingThrowsDelta(SAVING s, int value, boolean magic) {
        if (!magic)
            miscsavingthrows[s.ordinal()] += value;
        else
            miscmagicsavingthrows[s.ordinal()] += value;
        recalculate();
    }

    public void setSpecialAbility(String s, boolean clear) {
        if (clear)
            specialabilities.remove(s);
        else if (!specialabilities.contains(s))
            specialabilities.add(s);
        recalculate();
    }

    public void setSpellResistDelta(int value) {
        spellresist += value;
        recalculate();
    }

    public void setStat(STATS stat, int value) {
        stats[stat.ordinal()] = value;
        recalculate();
    }

    public void setStatDelta(STATS stat, int value) {
        stats[stat.ordinal()] += value;
        recalculate();
    }

    public void setTempACDelta(int value) {
        tempAC += value;
        recalculate();
    }

    public void setTempHPDelta(int value) {
        temphitpoints.add(value);
        recalculate();
    }

    public void setTempSavingDelta(SAVING saving, int value) {
        tempsavingthrows[saving.ordinal()] += value;
        recalculate();
    }

    public void setTempStatDelta(STATS stat, int value) {
        tempstats[stat.ordinal()] += value;
        recalculate();
    }

    public void setTempStatus(String s, boolean clear) {
        if (clear)
            tempstatuses.remove(s);
        else if (!tempstatuses.contains(s))
            tempstatuses.add(s);
        recalculate();
    }

    public void setWeapon(Weapon w, boolean clear) {
        if (clear)
            weapons.remove(w);
        else if (!weapons.contains(w))
            weapons.add(w);
        recalculate();
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
            res += "-Exp: " + getExp() + "\n";
            res += "-Run Speed: " + getRunspeed() + "\n";

            // stats
            res += "====STATS====\n";
            for (STATS s : STATS.values())
                res += "-" + s.toString() + ": " + getStat(s) + " | MOD: "
                        + getMod(s) + "\n";

            // hp
            res += "====HP====\n";

            res += "-MAX Hp: " + getTotalHP() + " | liferolls: (";
            for (int i : liferolls) {
                res += " " + i;
            }
            res += " )" + "\n";
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
