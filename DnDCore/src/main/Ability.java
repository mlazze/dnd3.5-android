package main;

public class Ability {
	public String name;
	public DnDCharacter.STATS stat;
	
	public Ability(String name, DnDCharacter.STATS mainstat) {
		this.name=name;
		this.stat=mainstat;
	}
	
	public String toString() {
		return name + "(" + stat + ")";
	}
}
