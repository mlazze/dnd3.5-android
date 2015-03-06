package core;

public enum DNDCLASS {
    BARBARIAN(12), MAGE(4), WARLOCK(4), BARD(6), ROGUE(6), CLERIC(8), DRUID(8), MONK(
            8), RANGER(8), WARRIOR(10), PALADIN(10), FBERSERKER(12), DICE4(4), DICE6(6), DICE8(8), DICE10(10), DICE12(12);

    private int lifedice;

    private DNDCLASS(int lifedice) {
        this.lifedice = lifedice;
    }

    public int getLifeDice() {
        return lifedice;
    }

    public String toString() {
        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }

    public String getLevelUpInfos() {
        switch (this) {
            case BARBARIAN:
                return "Pag 25 MdG";
            case MAGE:
                return "Pag 42 MdG";
            case WARLOCK:
                return "Pag 55 MdG";
            case BARD:
                return "Pag 27 MdG";
            case ROGUE:
                return "Pag 41 MdG";
            case CLERIC:
                return "Pag 31 MdG";
            case DRUID:
                return "Pag 35 MdG";
            case MONK:
                return "Pag 46 MdG";
            case RANGER:
                return "Pag 52 MdG";
            case WARRIOR:
                return "Pag 39 MdG";
            case PALADIN:
                return "Pag 49 MdG";
            case FBERSERKER:
                return "Pag 20 PierfettoComb";
            default:
                return "Unknown class";
        }
    }
}
