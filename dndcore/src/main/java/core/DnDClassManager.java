package core;

import java.util.ArrayList;
import java.util.HashMap;
public class DnDClassManager {

    private HashMap<String, Integer> classToDice;
    private HashMap<String, Integer> classToLevel;

    public DnDClassManager() {
        classToDice = new HashMap<>(DNDCLASS.values().length);

        for (DNDCLASS c : DNDCLASS.values()) {
            addNewClass(c.toString(), c.getLifeDice());
        }

        classToLevel = new HashMap<>(1);
    }

    /**
     * Adds classname to the known classes with the given lifedice. If classname is already known, it replaces its lifedice.
     *
     * @param className the String representing the class name
     * @param lifedice  the lifedice of the class
     * @throws core.DnDClassManager.InvalidDnDClassException if lifedice < 0
     */
    public void addNewClass(String className, int lifedice) {
        if (lifedice < 0)
            throw new InvalidDnDClassException();

        classToDice.put(formatClassAsInput(className), lifedice);
    }

    /**
     * Adds a level to className. If className has no level, it becomes 1.
     *
     * @param className the name of the class to be leveled
     * @throws core.DnDClassManager.UnknownDnDClassException if className wasn't added with addNewClass;
     */
    public void levelUpClass(String className) {
        if (!classToDice.containsKey(formatClassAsInput(className)))
            throw new UnknownDnDClassException();

        Integer oldvalue = classToLevel.get(formatClassAsInput(className));

        if (oldvalue == null)
            oldvalue = 0;

        oldvalue++;

        classToLevel.put(formatClassAsInput(className), oldvalue);
    }

    /**
     * Returns an HashMap containing Classes and relative levels
     *
     * @return an HashMap containing Classes and relative levels
     */
    public HashMap<String, Integer> getLevelledClasses() {
        HashMap<String, Integer> res = new HashMap<>(classToLevel.size());
        for (String s : classToLevel.keySet()) {
            res.put(formatClassAsOutput(s), classToLevel.get(s));
        }

        return res;
    }

    /**
     * Returns an HashMap containing known Classes and relative lifedices
     *
     * @return an HashMap containing known Classes and relative lifedices
     */
    public HashMap<String, Integer> getKnownClasses() {
        HashMap<String, Integer> res = new HashMap<>(classToDice.size());
        for (String s : classToDice.keySet()) {
            res.put(formatClassAsOutput(s), classToDice.get(s));
        }

        return res;
    }

    public static ArrayList<String> getDefaultClasses() {
        ArrayList<String> res = new ArrayList<>(1);
        for (DNDCLASS s : DNDCLASS.values()) {
            res.add(formatClassAsOutput(s.toString()));
        }

        return res;
    }

    /**
     * returns lifedice of given class
     *
     * @param className name of the class
     * @return lifedice of the given class
     * @throws core.DnDClassManager.UnknownDnDClassException if className is unknown
     */
    public int getLifeDice(String className) {
        if (!classToDice.containsKey(formatClassAsInput(className)))
            throw new UnknownDnDClassException();
        return classToDice.get(formatClassAsInput(className));
    }

    /**
     * returns level of given class
     *
     * @param className name of the class
     * @return level of the given class, 0 if not levelled
     * @throws core.DnDClassManager.UnknownDnDClassException if className is unknown
     */
    public int getLevel(String className) {
        if (!classToDice.containsKey(formatClassAsInput(className)))
            throw new UnknownDnDClassException();
        return classToLevel.get(formatClassAsInput(className)) == null ? 0 : classToLevel.get(formatClassAsInput(className));
    }

    private static String formatClassAsOutput(String className) {
        String res = "";
        for (String s : className.split(" "))
            res += s.substring(0, 1).toUpperCase() + s.substring(1, s.length()).toLowerCase() + " ";

        return res.substring(0,res.length()-1);
    }

    private static String formatClassAsInput(String className) {
        return className.toLowerCase();
    }

    public class InvalidDnDClassException extends RuntimeException {
    }

    public class UnknownDnDClassException extends RuntimeException {

    }

    public enum DNDCLASS {
        BARBARIAN(12), MAGE(4), WARLOCK(4), BARD(6), ROGUE(6), CLERIC(8), DRUID(8), MONK(
                8), RANGER(8), WARRIOR(10), PALADIN(10);
//        FBERSERKER(12), DICE4(4), DICE6(6), DICE8(8), DICE10(10), DICE12(12);

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
                default:
                    return "Unknown class";
            }
        }
    }

}
