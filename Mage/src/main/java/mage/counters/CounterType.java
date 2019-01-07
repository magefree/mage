package mage.counters;

/**
 * Enum for counters, names and instances.
 *
 * @author nantuko
 */
public enum CounterType {

    AGE("age"),
    AIM("aim"),
    ARROW("arrow"),
    ARROWHEAD("arrowhead"),
    AWAKENING("awakening"),
    BLAZE("blaze"),
    BLOOD("blood"),
    BOUNTY("bounty"),
    BRIBERY("bribery"),
    BRICK("brick"),
    CAGE("cage"),
    CARRION("carrion"),
    CHARGE("charge"),
    CHIP("chip"),
    CORPSE("corpse"),
    CREDIT("credit"),
    CRYSTAL("crystal"),
    CUBE("cube"),
    CURRENCY("currency"),
    DEATH("death"),
    DELAY("delay"),
    DEPLETION("depletion"),
    DESPAIR("despair"),
    DEVOTION("devotion"),
    DIVINITY("divinity"),
    DOOM("doom"),
    DREAM("dream"),
    ECHO("echo"),
    EGG("egg"),
    ELIXIR("elixir"),
    ENERGY("energy"),
    EON("eon"),
    EXPERIENCE("experience"),
    EYEBALL("eyeball"),
    FADE("fade"),
    FATE("fate"),
    FEATHER("feather"),
    FILIBUSTER("filibuster"),
    FLOOD("flood"),
    FUNK("funk"),
    FURY("fury"),
    FUNGUS("fungus"),
    FUSE("fuse"),
    GEM("gem"),
    GLOBE("globe"),
    GOLD("gold"),
    GROWTH("growth"),
    HATCHLING("hatchling"),
    HEALING("healing"),
    HIT("hit"),
    HOOFPRINT("hoofprint"),
    HOUR("hour"),
    HOURGLASS("hourglass"),
    HUNGER("hunger"),
    ICE("ice"),
    INFECTION("infection"),
    INTERVENTION("intervention"),
    ISOLATION("isolation"),
    JAVELIN("javelin"),
    KI("ki"),
    LANDMARK("landmark"),
    LEVEL("level"),
    LORE("lore"),
    LUCK("luck"),
    LOYALTY("loyalty"),
    MANIFESTATION("manifestation"),
    MANNEQUIN("mannequin"),
    M1M1(new BoostCounter(-1, -1).name),
    M2M1(new BoostCounter(-2, -1).name),
    M2M2(new BoostCounter(-2, -2).name),
    MINE("mine"),
    MINING("mining"),
    MIRE("mire"),
    MUSIC("music"),
    MUSTER("muster"),
    NET("net"),
    OMEN("omen"),
    ORE("ore"),
    P0P1(new BoostCounter(0, 1).name),
    P1P0(new BoostCounter(1, 0).name),
    P1P1(new BoostCounter(1, 1).name),
    P1P2(new BoostCounter(1, 2).name),
    P2P2(new BoostCounter(2, 2).name),
    PAGE("page"),
    PAIN("pain"),
    PARALYZATION("paralyzation"),
    PETAL("petal"),
    PETRIFICATION("petrification"),
    PHYLACTERY("phylactery"),
    PLAGUE("plague"),
    PLOT("plot"),
    POLYP("polyp"),
    POISON("poison"),
    PRESSURE("pressure"),
    PREY("prey"),
    REPAIR("repair"),
    RUST("rust"),
    QUEST("quest"),
    SILVER("silver"),
    SCREAM("scream"),
    SHELL("shell"),
    SHIELD("shield"),
    SHRED("shred"),
    SLIME("slime"),
    SLUMBER("slumber"),
    SOOT("soot"),
    SPITE("spite"),
    SPORE("spore"),
    STORAGE("storage"),
    STRIFE("strife"),
    STUDY("study"),
    THEFT("theft"),
    TIDE("tide"),
    TIME("time"),
    TOWER("tower"),
    TRAINING("training"),
    TRAP("trap"),
    TREASURE("treasure"),
    UNITY("unity"),
    VELOCITY("velocity"),
    VERSE("verse"),
    VITALITY("vitality"),
    WIND("wind"),
    WISH("wish");

    private final String name;

    CounterType(String name) {
        this.name = name;
    }

    /**
     * Get counter string name.
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Create instance of counter type with amount equal to 1.
     *
     * @return
     */
    public Counter createInstance() {
        return createInstance(1);
    }

    /**
     * Create instance of counter type with defined amount of counters of the
     * given type.
     *
     * @param amount amount of counters of the given type.
     * @return
     */
    public Counter createInstance(int amount) {
        switch (this) {
            case P0P1:
                return new BoostCounter(0, 1, amount);
            case P1P0:
                return new BoostCounter(1, 0, amount);
            case P1P1:
                return new BoostCounter(1, 1, amount);
            case P1P2:
                return new BoostCounter(1, 2, amount);
            case P2P2:
                return new BoostCounter(2, 2, amount);
            case M1M1:
                return new BoostCounter(-1, -1, amount);
            case M2M1:
                return new BoostCounter(-2, -1, amount);
            case M2M2:
                return new BoostCounter(-2, -2, amount);
            default:
                return new Counter(name, amount);
        }
    }

    public static CounterType findByName(String name) {
        for (CounterType counterType : values()) {
            if (counterType.getName().equals(name)) {
                return counterType;
            }
        }
        return null;
    }
}
