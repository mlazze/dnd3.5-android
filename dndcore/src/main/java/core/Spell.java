package core;

public class Spell {
	public String name;
	public String descr;
	public int level;
	
	public Spell(String name, String descr, int level) {
		this.name=name;
		this.descr=descr;
		this.level=level;
	}
	
	public String toString() {
		if (descr==null)
			return name + ": no descr";
		return name + "(lv" + level + "): " + descr;
	}
}