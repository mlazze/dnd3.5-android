package core;

import java.io.Serializable;

public class Equipment implements Serializable {
    public String name = "none";
    public TYPE type;
    public int acbonus;
    public int maxdex = 999;
    public int savepenalty;
    public int spellfail;
    public int speed;
    public double weight;
    public String specialproperties = "none";
    public int deflectionbonus;
    public int naturalbonus;

    public Equipment(String name) {
        this.name = name;
        type = TYPE.ARMOR;
    }

    public Equipment(String name, TYPE t) {
        this.name = name;
        type = t;
    }

    public String toString() {
        String res = "";
        res += TYPE.toString(type)+ ": " + "Name: " + name;
        if (acbonus != 0)
            res += ", AC bonus: " + acbonus;
        if (naturalbonus != 0)
            res += ", Natural Armor: " + naturalbonus;
        if (deflectionbonus != 0)
            res += ", Deviation: " + deflectionbonus;
        if (maxdex!=999)
            res+=", Max Dex: " + maxdex;
        if (savepenalty != 0)
            res += ", Penalty: " + savepenalty;
        if (spellfail != 0)
            res += ", Spellfail: " + spellfail;
        if (speed != 0)
            res += ", Max Speed: " + speed;
        if (weight != 0)
            res += ", Weight: " + weight;
        if (specialproperties != null && !specialproperties.equals(""))
            res += ", Special Properties: " + specialproperties;
        return res;
    }

    public static enum TYPE {
        SHIELD, ARMOR;

        private static String toString(TYPE t) {
            switch (t) {
                case SHIELD:
                    return "Shield";
                case ARMOR:
                    return "Armor";
                default:
                    return null;
            }
        }
    }
}
