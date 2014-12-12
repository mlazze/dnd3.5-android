package main;

public enum DNDCLASS {
	BARBARIAN(12), MAGE(4), WARLOCK(4), BARD(6), ROGUE(6), CLERIC(8), DRUID(8), MONK(8), RANGER(8), WARRIOR(10), PALADIN(10);
	
	private int lifedice;
	
	private DNDCLASS(int lifedice) {
		this.lifedice = lifedice;
	}
	
	public static int getLifeDice(DNDCLASS c) {
		return c.lifedice;
	}
}
