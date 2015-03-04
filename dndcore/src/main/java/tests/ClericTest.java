package core.tests;

import core.DNDCLASS;
import core.DnDCharacter.STATS;
import core.DnDCharacterManipulator;
import core.Equipment;
import core.Equipment.TYPE;
import core.IDnDCharacterManipulator;
import core.Weapon;

public class ClericTest {
	public static void main(String[] args) {
		creaCleric();
	}

	private static void creaCleric() {
		int[] stats = new int[] { 14, 12, 16, 10, 18, 12 };
		int[] sav = new int[] { 4, 1, 4 };
		IDnDCharacterManipulator cleric = new DnDCharacterManipulator("Cleric",
				DNDCLASS.CLERIC, stats, 6, sav);
		cleric.levelup(DNDCLASS.CLERIC, 3, null, null, 0, null);
		cleric.levelup(DNDCLASS.CLERIC, 6, null, null, 0, null);
		cleric.levelup(DNDCLASS.CLERIC, 4, null, null, 0, null);
		cleric.levelup(DNDCLASS.CLERIC, 5, null, null, 0, null);
		int[] atkrolls = new int[] { 4 };
		sav = new int[] { 5, 2, 5 };
		String s = cleric.levelup(DNDCLASS.CLERIC, 4, atkrolls, STATS.WIS, 1,
				sav);
		Weapon w = new Weapon("Mazza pes Folgore", false, STATS.STR, 1, 1.5,
				"1d8", 20, 2);
		w.notes = "+1d6 Elettr";
		cleric.setWeapon(w, false);
		w = new Weapon("Balestra Pes", true, STATS.DEX, 1, 30,
				"1d10", 19, 2);
		w.ammo=1;
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
	}

}
