package core;

import java.io.Serializable;

public class Spell implements Serializable, Comparable {
    public String name;
    public String descr;
    public int level;

    public Spell(String name, String descr, int level) {
        if (name == null)
            throw new IllegalArgumentException();
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

        return name.equals(spell.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + level;
        return result;
    }

    public int compareTo(Spell s) {
        if (this.level < s.level) return -1;
        if (this.level > s.level) return 1;
        return this.name.compareTo(s.name);
    }

    @Override
    public int compareTo(Object o) {
        if (o.getClass() == this.getClass())
            return compareTo((Spell) o);
        return 0;
    }
}
