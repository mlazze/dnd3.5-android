package test;

import main.DNDCLASS;
import main.DnDCharacter;
import main.DnDCharacter.SAVING;
import main.DnDCharacterManipulator;
import main.Equipment;
import main.IDnDCharacterManipulator;
import main.Weapon;
import main.DnDCharacter.STATS;
import main.Equipment.TYPE;

public class Test {
	public static void main(String[] args) {
//		creaStranger();
		creaCleric();
	}

	private static void creaCleric() {
		int[] stats = new int[] { 14,12,16,10,18,12 };
		int[] sav = new int[] { 4, 1, 4 };
		DnDCharacterManipulator cleric = new DnDCharacterManipulator(
				"Cleric", DNDCLASS.CLERIC, stats, 6, sav);
		cleric.levelup(DNDCLASS.CLERIC, 3, null, null, 0);
		cleric.levelup(DNDCLASS.CLERIC, 6, null, null, 0);
		cleric.levelup(DNDCLASS.CLERIC, 4, null, null, 0);
		cleric.levelup(DNDCLASS.CLERIC, 5, null, null, 0);
		int[] atkrolls = new int[] {3};
		cleric.levelup(DNDCLASS.CLERIC, 4, atkrolls, STATS.WIS, 1);
		System.out.println("=====START====="+cleric.getliferolls());
		System.out.println(cleric.toString());
		System.out.println("=====================");
		Weapon w = new Weapon("Mazza pes Folgore", false, STATS.STR, 1, 1.5, "1d8",
				20, 2);
		w.notes="+1d6 Elettr";
		cleric.setWeapon(w, false);
		Equipment cor = new Equipment("Armatura completa+1");
		cor.acbonus = 9;
		cor.maxdex = 1;
		cleric.setEquipment(cor, false);
		Equipment shield = new Equipment("Shield",TYPE.SHIELD);
		shield.acbonus = 3;
		shield.savepenalty=2;
		cleric.setEquipment(shield, false);
		Equipment neck = new Equipment("Amuleto Arm Nat");
		neck.naturalbonus = 1;
		cleric.setEquipment(neck, false);
		Equipment ring = new Equipment("Anello Protettivo +1");
		ring.deflectionbonus = 1;
		cleric.setEquipment(ring, false);
		System.out.println("=====START=====");
		System.out.println(cleric.toString());
		System.out.println("=====================");
	}

	public static void creaStranger() {
		int[] stats = new int[] { 18, 14, 18, 10, 14, 8 };
		int[] sav = new int[] { 4, 1, 1 };
		IDnDCharacterManipulator stranger = new DnDCharacterManipulator(
				"Stranger", DNDCLASS.BARBARIAN, stats, 12, sav);
		stranger.setAttackBonus(0, 5, false);
		System.out.println("=====START=====");
		System.out.println(stranger.toString());
		System.out.println("=====================");
		Weapon w = new Weapon("Spadone 2h", false, STATS.STR, 1.5, 1.5, "2d6",
				19, 2);
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
		System.out.println("=====INSERIMENTOARMI=====");
		System.out.println(stranger.toString());
		System.out.println("=====================");
		stranger.levelup(DNDCLASS.BARBARIAN, 8, null, null, 0);
		stranger.levelup(DNDCLASS.BARBARIAN, 11, null, null, 0);
		stranger.levelup(DNDCLASS.BARBARIAN, 8, null, STATS.STR, 1);
		stranger.levelup(DNDCLASS.BARBARIAN, 8, null, null, 0);
		stranger.clearTemp();
		stranger.clearMisc();
		String a = stranger.toString();
		System.out.println("=====LVLUP=====");
		System.out.println(stranger.toString());
		System.out.println("=====================");
		setIra(stranger);
		stranger.recalculate();
		System.out.println("=====IRA=====");
		System.out.println(stranger.toString());
		System.out.println("=====================");
		stranger.clearTemp();
		stranger.clearMisc();
		String b = stranger.toString();
		System.out.println("=====NOIRA=====");
		System.out.println(stranger.toString());
		System.out.println("=====================");
		System.out.println(a.equals(b));
	}

	private static void setIra(IDnDCharacterManipulator charac) {
		charac.setTempStatDelta(STATS.STR, 4);
		charac.setTempStatDelta(STATS.CON, 4);
		charac.setTempACDelta(-2);
		charac.setTempSavingDelta(SAVING.WILL, 2);
		charac.setTempStatus("Ira", false);
	}
}
