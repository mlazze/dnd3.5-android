package main;

public class Weapon {
	public String name ="none";
	public String damagedices="none";
	public int critminrange;
	public double critmult;
	public double range;
	public String type="none";
	public String notes="none";
	public Integer ammo;
	public boolean ranged;
	DnDCharacter.STATS stat; 
	public double damagemod;

	public Weapon(String name, boolean ranged, DnDCharacter.STATS stat, double damagemod, double range, String damagedices, int critminrange, double critmult) {
		this.name = name;
		this.ranged=ranged;
		this.stat=stat;
		this.damagemod = damagemod;
		this.range = range;
		this.damagedices = damagedices;
		this.critminrange = critminrange;
		this.critmult = critmult;
	}

	public String toString() {
		String s = "Name: " + name;
		if (ammo!=null)
			s+=" (" + ammo + " ammo)";
		if (ranged)
			s+=" ranged";
			
		return s + ", critmin: " + critminrange
				+ ", critMult: " + critmult + ", range: " + range
				+ ", type: " + type + ", notes: " + notes;
	}
	
	public String getCritRange() {
		return critminrange + "-20";
	}
	
	public void consumeAmmo() throws DnDCharacter.NotEnoughAmmoException {
		if (ammo<=0)
			throw new DnDCharacter.NotEnoughAmmoException();
		ammo--;
	}
}
