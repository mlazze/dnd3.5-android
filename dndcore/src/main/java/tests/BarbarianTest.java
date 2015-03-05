package tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import core.DNDCLASS;
import core.DnDCharacter.SAVING;
import core.DnDCharacterManipulator;
import core.Equipment;
import core.Weapon;
import core.DnDCharacter.STATS;
import core.Equipment.TYPE;

public class BarbarianTest {
    public static void main(String[] args) {
        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
        DnDCharacterManipulator stranger = null;
        try {
            System.out.println("C Cleric\nS Stranger");
            switch (b.readLine().charAt(0)) {
                case 'C':
                    stranger = creaCleric();
                    break;
                default:
                    stranger = creaStranger();
                    break;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        String s = null;
        menu:
        try {
            do {
                if (s != null) if (s.startsWith("i")) {
//                    String sa;
//                    int value;
                    showincreasemenuitems();
//                    sa = b.readLine();
//                    value = Integer.parseInt(b.readLine());
//                    switch (sa) {
//                        case "STR":
//                            stranger.setTempStatDelta(STATS.STR, value);
//                        case "DEX":
//                            stranger.setTempStatDelta(STATS.DEX, value);
//                        case "CON":
//                            stranger.setTempStatDelta(STATS.CON, value);
//                        case "WIS":
//                            stranger.setTempStatDelta(STATS.WIS, value);
//                        case "INT":
//                            stranger.setTempStatDelta(STATS.INT, value);
//                        case "CHA":
//                            stranger.setTempStatDelta(STATS.CHA, value);
//                        case "L":
//                            stranger.setTempHPDelta(value);
//                        case "REF":
//                            stranger.setTempSavingDelta(SAVING.REFLEX, value);
//                        case "FOR":
//                            stranger.setTempSavingDelta(SAVING.FORTITUDE, value);
//                        case "WIL":
//                            stranger.setTempSavingDelta(SAVING.WILL, value);
//                    }
                    print(stranger);

                } else if (s.startsWith("quit")) {
                    break menu;
                } else if (s.startsWith("clear")) {
                    assert stranger != null;
                    stranger.clearTemp();
                    stranger.clearMisc();
                } else if (s.startsWith("status")) {
                    String sa;
                    System.out.println("Write status");
                    sa = b.readLine();
                    if (sa.startsWith("IRA")) {
                        setIra(stranger);
                    } else if (sa.startsWith("clear")) {
                        assert stranger != null;
                        stranger.setTempStatus(sa.replace("clear", ""), true);
                    } else {
                        assert stranger != null;
                        stranger.setTempStatus(sa, false);
                    }
                    print(stranger);
                }
                showmenuitems();
            } while ((s = b.readLine()) != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void print(DnDCharacterManipulator stranger) {
        System.out.println("=========");
        System.out.println(stranger);

    }

    private static int getIntParam() throws NumberFormatException, IOException {
        return Integer.parseInt(getStrParam());
    }

    private static String getStrParam() throws IOException {
        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
        return b.readLine();
    }

    private static void showincreasemenuitems() {
        System.out.println("L for life");
        System.out.println("STAT for stat");
        System.out.println("SAV for saving");
    }

    private static void showmenuitems() {
        System.out.println("-quit: Quit to exit");
        System.out.println("-i: Increase/Decrease");
        System.out.println("-status: add/rm status");

    }

    private static DnDCharacterManipulator creaStranger() {
        int[] stats = new int[]{18, 14, 18, 10, 14, 8};
        int[] sav = new int[]{4, 1, 1};
        DnDCharacterManipulator stranger = new DnDCharacterManipulator(
                "Stranger", DNDCLASS.BARBARIAN, stats, 12, sav);
        stranger.setAttackBonus(0, 5, false);
        Weapon w = new Weapon("Spadone 2h", false, STATS.STR, 1.5, 1.5, "2d6",
                19, 2);
        stranger.setWeapon(w, false);
        w = new Weapon("Giavellotto", true, STATS.STR, 1, 30, "1d6", 20, 2);
        w.ammo = 1;
        stranger.setWeapon(w, false);
        Equipment cor = new Equipment("Corazza di pelle di rinoceronte +2");
        cor.acbonus = 7;
        cor.maxdex = 3;
        cor.savepenalty = -4;
        cor.specialproperties = "+2d6 in carica";
        stranger.setEquipment(cor, false);
        Equipment neck = new Equipment("Amuleto Arm Nat");
        neck.naturalbonus = 1;
        stranger.setEquipment(neck, false);
        Equipment ring = new Equipment("Anello Protettivo +1");
        ring.deflectionbonus = 1;
        stranger.setEquipment(ring, false);
        stranger.levelup(DNDCLASS.BARBARIAN, 8, null, null, 0, null);
        stranger.levelup(DNDCLASS.BARBARIAN, 11, null, null, 0, null);
        stranger.levelup(DNDCLASS.BARBARIAN, 8, null, STATS.STR, 1, null);
        stranger.levelup(DNDCLASS.BARBARIAN, 8, null, null, 0, null);
        sav = new int[]{5, 2, 2};
        String s = stranger.levelup(DNDCLASS.BARBARIAN, 6, null, null, 0, sav);
        stranger.clearTemp();
        stranger.clearMisc();
        System.out.println("=====LVLUP=====");
        System.out.println(s);
        System.out.println(stranger.toString());
        System.out.println("=====================");
        return stranger;
    }

    private static DnDCharacterManipulator creaCleric() {
        int[] stats = new int[]{14, 12, 16, 10, 18, 12};
        int[] sav = new int[]{4, 1, 4};
        DnDCharacterManipulator cleric = new DnDCharacterManipulator("Cleric",
                DNDCLASS.CLERIC, stats, 6, sav);
        cleric.levelup(DNDCLASS.CLERIC, 3, null, null, 0, null);
        cleric.levelup(DNDCLASS.CLERIC, 6, null, null, 0, null);
        cleric.levelup(DNDCLASS.CLERIC, 4, null, null, 0, null);
        cleric.levelup(DNDCLASS.CLERIC, 5, null, null, 0, null);
        int[] atkrolls = new int[]{4};
        sav = new int[]{5, 2, 5};
        String s = cleric.levelup(DNDCLASS.CLERIC, 4, atkrolls, STATS.WIS, 1,
                sav);
        Weapon w = new Weapon("Mazza pes Folgore", false, STATS.STR, 1, 1.5,
                "1d8", 20, 2);
        w.notes = "+1d6 Elettr";
        cleric.setWeapon(w, false);
        w = new Weapon("Balestra Pes", true, STATS.DEX, 1, 30, "1d10", 19, 2);
        w.ammo = 1;
        cleric.setWeapon(w, false);
        Equipment cor = new Equipment("Armatura completa+1");
        cor.acbonus = 9;
        cor.maxdex = 1;
        cleric.setEquipment(cor, false);
        Equipment shield = new Equipment("Shield", TYPE.SHIELD);
        shield.acbonus = 3;
        shield.savepenalty = 2;
        cleric.setEquipment(shield, false);
        Equipment neck = new Equipment("Amuleto Arm Nat");
        neck.naturalbonus = 1;
        cleric.setEquipment(neck, false);
        Equipment ring = new Equipment("Anello Protettivo +1");
        ring.deflectionbonus = 1;
        cleric.setEquipment(ring, false);
        System.out.println("=====START=====");
        System.out.println(s);
        System.out.println(cleric.toString());
        System.out.println("=====================");
        return cleric;
    }

    private static void setIra(DnDCharacterManipulator charac) {
        charac.setTempStatDelta(STATS.STR, 4);
        charac.setTempStatDelta(STATS.CON, 4);
        charac.setTempACDelta(-2);
        charac.setTempSavingDelta(SAVING.WILL, 2);
        charac.setTempStatus("Ira", false);
    }
}
