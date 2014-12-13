package main;

import main.DnDCharacter.STATS;

public enum ABILITIES {
	ACROBAZIA(STATS.DEX, "Acrobazia", false ),
	ADDESTRARE_ANIMALI(STATS.CHA, "Addestrare animali", false ),
	ARTIGIANATO1(STATS.INT, "Artigianato1",true),
	ARTIGIANATO2(STATS.INT, "Artigianato2",true),
	ARTIGIANATO3(STATS.INT, "Artigianato3",true),
	ARTISTA_DELLA_FUGA(STATS.DEX, "Artista della fuga",true),
	ASCOLTARE(STATS.WIS, "Ascoltare", false ),
	CAMUFFARE(STATS.CHA, "Camuffare",true),
	CAVALCARE(STATS.DEX, "Cavalcare",true),
	CERCARE(STATS.INT, "Cercare",true),
	CONCENTRAZIONE(STATS.CON, "Concentrazione",true),
	CONOSCENZE1(STATS.INT, "Conoscenze1", false ),
	CONOSCENZE2(STATS.INT, "Conoscenze2", false ),
	CONOSCENZE3(STATS.INT, "Conoscenze3", false ),
	CONOSCENZE4(STATS.INT, "Conoscenze4", false ),
	CONOSCENZE5(STATS.INT, "Conoscenze5", false ),
	DECIFRARE(STATS.INT, "Decifrare", false ),
	DIPLOMAZIA(STATS.CHA, "Diplomazia", false ),
	DISATTIVARE(STATS.INT, "Disattivare", false ),
	EQUILIBRIO(STATS.DEX, "Equilibrio",true),
	FALSIFICARE(STATS.INT, "Falsificare",true),
	GUARIRE(STATS.WIS, "Guarire",true),
	INTIMIDIRE(STATS.CHA, "Intimidire",true),
	INTRATTENERE(STATS.CHA, "Intrattenere", false ),
	MUOVERSI_SIL(STATS.DEX, "Muoversi sil",true),
	NASCONDERSI(STATS.DEX, "Nascondersi",true),
	NUOTARE(STATS.STR, "Nuotare",true),
	OSSERVARE(STATS.WIS, "Osservare", false ),
	PERCEPIRE_INTENZ(STATS.WIS, "Percepire intenzioni",true),
	PROFESSIONE(STATS.WIS,"Professione", false ),
	RACCOGLIERE_INFO(STATS.CHA, "Raccogliere informazioni",true),
	RAGGIRARE(STATS.CHA, "Raggirare",true),
	RAPIDITA_DI_MANO(STATS.DEX, "Rapidita di mano", false ),
	SALTARE(STATS.STR, "Saltare",true),
	SAPIENZA_MAG(STATS.INT, "Sapienza magica", false ),
	SCALARE(STATS.STR, "Scalare",true),
	SCASSINARE_SERR(STATS.DEX, "Scassinare serrature", false ),
	SOPRAVVIVENZA(STATS.WIS, "Sopravvivenza",true),
	UTILIZZARE_CORDE(STATS.DEX, "Utilizzare corde", false ),
	UTILIZZARE_MAGICI(STATS.CHA, "Utilizzare oggetti magici", false ),
	VALUTARE(STATS.INT, "Valutare",true);
	
	private STATS stat;
	private String name;
	private boolean def;
	
	private ABILITIES(STATS s, String n, boolean d) {
		stat=s;
		name=n;
		def=d;
	}
	
	public STATS getStat() {
		return stat;
	}
	
	public boolean isDef() {
		return def;
	}
	
	public String toString() {
		return name + "(" + stat + ")";
	}
}