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
        return "Name: " + name + ", type: " + TYPE.toString(type)
                + ", acbonus: " + acbonus + " (natural:" + naturalbonus
                + ", deviation:" + deflectionbonus + "), MaxDex: " + maxdex
                + ", savepen: " + savepenalty + ", spellfail: " + spellfail
                + ", speed: " + speed + ", weight: " + weight
                + ", SpecialProp: " + specialproperties;
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
