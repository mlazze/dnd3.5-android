package main;

public class Equipment {
	public String name;
	public String type;
	public Integer acbonus;
	public Integer maxdex;
	public Integer savepenalty;
	public Integer spellfail;
	public Integer speed;
	public Double weight;
	public String specialproperties;

	public Equipment(String name) {
		this.name = name;
	}

	public String toString() {
		return "Name: " + name + ", type: " + type + ", acbonus: " + name
				+ ", MaxDex: " + maxdex + ", savepen: " + savepenalty
				+ ", spellfail: " + spellfail + ", speed: " + speed + ", weight: " + weight
				+ ", SpecialProp: " + specialproperties;
	}
}
