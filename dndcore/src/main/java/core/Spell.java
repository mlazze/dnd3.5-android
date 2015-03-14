package core;

import java.io.Serializable;

public class Spell implements Serializable, Comparable {
    public String name;
    public String descr;
    public int level;

    public Spell(String name, String descr, int level) {
        this.name = name;
        this.descr = descr;
        this.level = level;
    }

    public String toString() {
        return name + "(lv" + level + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spell spell = (Spell) o;

        if (!name.equals(spell.name)) return false;

        return true;
    }

    public int compareTo(Spell s) {
        if (this.level<s.level) return -1;
        if (this.level>s.level) return 1;
        return 0;
    }

    @Override
    public int compareTo(Object o) {
        if (o.getClass()==this.getClass())
            return compareTo((Spell) o);
        return 0;
    }
}
