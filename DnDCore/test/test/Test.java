package test;

import main.DNDCLASS;
import main.DnDCharacter;
import main.DnDCharacter.SAVING;
import main.Equipment;
import main.Weapon;
import main.DnDCharacter.STATS;

public class Test {
	public static void main(String[] args) {
		int[] stats = new int[] { 19, 14, 18, 10, 14, 8 };
		int[] savbase = new int[] { 4, 1, 1 };
		DnDCharacter pino = new DnDCharacter("Pino", DNDCLASS.BARBARIAN, stats,
				12, savbase);
		pino.liferolls.add(8);
		pino.liferolls.add(11);
		pino.liferolls.add(8);
		pino.liferolls.add(8);
		pino.level = 5;
		pino.classes.put(DNDCLASS.BARBARIAN, 5);

		pino.weapons.add(new Weapon("Spadone 2h", false, STATS.STR, 1.5, 1.5,
				"2d6", 19, 2));
		pino.basicattackbonus.clear();
		pino.basicattackbonus.add(5);
		pino.recalculate();
		System.out.println(pino.toString());
		System.out.println("=====================");
		
		setIra(pino);
		pino.recalculate();
		System.out.println(pino.toString());

		System.out.println("=====================");
		
		pino.resettemp();
		pino.recalculate();
		System.out.println(pino.toString());
		
		Equipment cor = new Equipment("Corazza di pelle di rinoceronte +2");
		cor.acbonus=7;
		cor.maxdex=3;
		cor.savepenalty=-4;
		cor.specialproperties="+2d6 in carica";
		pino.equipment.add(cor);
		Equipment neck = new Equipment("Amuleto Arm Nat");
		neck.naturalbonus=1;
		pino.equipment.add(neck);
		Equipment ring = new Equipment("Anello Protettivo +1");
		ring.deflectionbonus=1;
		pino.equipment.add(ring);
		pino.recalculate();
		System.out.println(pino.toString());
		setIra(pino);
		System.out.println(pino.toString());
		
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		pino.level=6;
		pino.classes.put(DNDCLASS.BARBARIAN, 6);
		pino.liferolls.add(6);
		pino.basicattackbonus.set(0, 6);
		pino.basicattackbonus.add(1);
		pino.resettemp();
		pino.recalculate();
		System.out.println(pino.toString());
		System.out.println("=====================");
		System.out.println("=====================");
		System.out.println("=====================");
		setIra(pino);
		pino.recalculate();
		System.out.println(pino.toString());
		
	}

	private static void setIra(DnDCharacter pino) {
		pino.tempstats[STATS.STR.ordinal()] += 4;
		pino.tempstats[STATS.CON.ordinal()] += 4;
		pino.tempsavingthrows[SAVING.WILL.ordinal()] += 2;
		pino.tempAC += -2;
		pino.tempstatuses.add("Ira");
	}

}
