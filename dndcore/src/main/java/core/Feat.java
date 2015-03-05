package core;

import java.io.Serializable;

public class Feat implements Serializable {
    public String name;
    public String descr;

    public Feat(String name, String descr) {
        this.name = name;
        this.descr = descr;
    }

    public String toString() {
        if (descr == null)
            return name + ": no descr";
        return name + ": " + descr;
    }
}
