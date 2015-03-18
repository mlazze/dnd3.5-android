package core;

public enum ABILITIES {
    ACROBAZIA(DnDCharacter.STATS.DEX, "Acrobazia", false, true),
    ADDESTRARE_ANIMALI(DnDCharacter.STATS.CHA, "Addestrare animali", false, false),
    ARTIGIANATO1(DnDCharacter.STATS.INT, "Artigianato1", true, false),
    ARTIGIANATO2(DnDCharacter.STATS.INT, "Artigianato2", true, false),
    ARTIGIANATO3(DnDCharacter.STATS.INT, "Artigianato3", true, false),
    ARTISTA_DELLA_FUGA(DnDCharacter.STATS.DEX, "Artista della fuga", true, true),
    ASCOLTARE(DnDCharacter.STATS.WIS, "Ascoltare", false, false),
    CAMUFFARE(DnDCharacter.STATS.CHA, "Camuffare", true, false),
    CAVALCARE(DnDCharacter.STATS.DEX, "Cavalcare", true, false),
    CERCARE(DnDCharacter.STATS.INT, "Cercare", true, false),
    CONCENTRAZIONE(DnDCharacter.STATS.CON, "Concentrazione", true, false),
    CONOSCENZE1(DnDCharacter.STATS.INT, "Conoscenze1", false, false),
    CONOSCENZE2(DnDCharacter.STATS.INT, "Conoscenze2", false, false),
    CONOSCENZE3(DnDCharacter.STATS.INT, "Conoscenze3", false, false),
    CONOSCENZE4(DnDCharacter.STATS.INT, "Conoscenze4", false, false),
    CONOSCENZE5(DnDCharacter.STATS.INT, "Conoscenze5", false, false),
    DECIFRARE(DnDCharacter.STATS.INT, "Decifrare", false, false),
    DIPLOMAZIA(DnDCharacter.STATS.CHA, "Diplomazia", false, false),
    DISATTIVARE(DnDCharacter.STATS.INT, "Disattivare", false, false),
    EQUILIBRIO(DnDCharacter.STATS.DEX, "Equilibrio", true, true),
    FALSIFICARE(DnDCharacter.STATS.INT, "Falsificare", true, false),
    GUARIRE(DnDCharacter.STATS.WIS, "Guarire", true, false),
    INTIMIDIRE(DnDCharacter.STATS.CHA, "Intimidire", true, false),
    INTRATTENERE(DnDCharacter.STATS.CHA, "Intrattenere", false, false),
    MUOVERSI_SIL(DnDCharacter.STATS.DEX, "Muoversi sil", true, true),
    NASCONDERSI(DnDCharacter.STATS.DEX, "Nascondersi", true, true),
    NUOTARE(DnDCharacter.STATS.STR, "Nuotare", true, true),
    OSSERVARE(DnDCharacter.STATS.WIS, "Osservare", false, false),
    PERCEPIRE_INTENZ(DnDCharacter.STATS.WIS, "Percepire intenzioni", true, false),
    PROFESSIONE(DnDCharacter.STATS.WIS, "Professione", false, false),
    RACCOGLIERE_INFO(DnDCharacter.STATS.CHA, "Raccogliere informazioni", true, false),
    RAGGIRARE(DnDCharacter.STATS.CHA, "Raggirare", true, false),
    RAPIDITA_DI_MANO(DnDCharacter.STATS.DEX, "Rapidita di mano", false, true),
    SALTARE(DnDCharacter.STATS.STR, "Saltare", true, true),
    SAPIENZA_MAG(DnDCharacter.STATS.INT, "Sapienza magica", false, false),
    SCALARE(DnDCharacter.STATS.STR, "Scalare", true, true),
    SCASSINARE_SERR(DnDCharacter.STATS.DEX, "Scassinare serrature", false, false),
    SOPRAVVIVENZA(DnDCharacter.STATS.WIS, "Sopravvivenza", true, false),
    UTILIZZARE_CORDE(DnDCharacter.STATS.DEX, "Utilizzare corde", false, false),
    UTILIZZARE_MAGICI(DnDCharacter.STATS.CHA, "Utilizzare oggetti magici", false, false),
    VALUTARE(DnDCharacter.STATS.INT, "Valutare", true, false);

    private DnDCharacter.STATS stat;
    private String name;
    private boolean def;
    private boolean malus;

    private ABILITIES(DnDCharacter.STATS s, String n, boolean d, boolean malus) {
        stat = s;
        name = n;
        def = d;
        this.malus = malus;
    }

    public DnDCharacter.STATS getStat() {
        return stat;
    }

    public boolean isDef() {
        return def;
    }

    public boolean hasMalus() {
        return malus;
    }

    public String toString() {
        return name + " (" + stat.toString().substring(0,3) + ")";
    }
}