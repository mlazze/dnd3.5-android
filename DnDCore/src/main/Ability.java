package main;

public class Ability {
	public String name;
	public Character.STATS stat;
	
	public Ability(String name, Character.STATS mainstat) {
		this.name=name;
		this.stat=mainstat;
	}
	
	public String toString() {
		return name + "(" + stat + ")";
	}
}
