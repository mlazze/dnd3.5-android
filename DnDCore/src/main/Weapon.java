package main;

public class Weapon {
	public String name;
	public Integer atkbonus;
	public String damagedices;
	public Integer critminrange;
	public Integer critmult;
	public Integer range;
	public String type;
	public String notes;
	public Integer ammo;
	public boolean ranged;
	Character.STATS stat; 
	public int damagemod;

	public Weapon(String name, boolean ranged, Character.STATS stat) {
		this.name = name;
		this.ranged=ranged;
		this.stat=stat;
	}

	public String toString() {
		String s = "Name: " + name;
		if (ammo!=null)
			s+=" (" + ammo + " ammo)";
		if (ranged)
			s+=" ranged";
			
		return s + ", atkbonus: " + atkbonus + ", critmin: " + critminrange
				+ ", critMult: " + critmult + ", range: " + range
				+ ", type: " + type + ", notes: " + notes;
	}
	
	public String getCritRange() {
		return critminrange + "-20";
	}
	
	public void consumeAmmo() throws NotEnoughAmmoException {
		if (ammo<=0)
			throw new NotEnoughAmmoException();
		ammo--;
	}
}
