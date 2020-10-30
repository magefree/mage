package mage.counters;

import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.filter.predicate.Predicate;
import mage.game.Game;

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
    COIN("coin"),
    CORPSE("corpse"),
    CREDIT("credit"),
    CRYSTAL("crystal"),
    CUBE("cube"),
    CURRENCY("currency"),
    DEATH("death"),
    DEATHTOUCH("deathtouch"),
    DELAY("delay"),
    DEPLETION("depletion"),
    DESPAIR("despair"),
    DEVOTION("devotion"),
    DIVINITY("divinity"),
    DOOM("doom"),
    DOUBLE_STRIKE("double strike"),
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
    FETCH("fetch"),
    FILIBUSTER("filibuster"),
    FIRST_STRIKE("first strike"),
    FLOOD("flood"),
    FLYING("flying"),
    FORESHADOW("foreshadow"),
    FUNK("funk"),
    FURY("fury"),
    FUNGUS("fungus"),
    FUSE("fuse"),
    GEM("gem"),
    GLOBE("globe"),
    GLYPH("glyph"),
    GOLD("gold"),
    GROWTH("growth"),
    HATCHLING("hatchling"),
    HEALING("healing"),
    HEXPROOF("hexproof"),
    HIT("hit"),
    HOOFPRINT("hoofprint"),
    HOUR("hour"),
    HOURGLASS("hourglass"),
    HUNGER("hunger"),
    ICE("ice"),
    INCARNATION("incarnation"),
    INDESTRUCTIBLE("indestructible"),
    INFECTION("infection"),
    INTERVENTION("intervention"),
    ISOLATION("isolation"),
    JAVELIN("javelin"),
    KNOWLEDGE("knowledge"),
    KI("ki"),
    LANDMARK("landmark"),
    LEVEL("level"),
    LIFELINK("lifelink"),
    LORE("lore"),
    LUCK("luck"),
    LOYALTY("loyalty"),
    MANIFESTATION("manifestation"),
    MANNEQUIN("mannequin"),
    MATRIX("matrix"),
    MENACE("menace"),
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
    PIN("pin"),
    PLAGUE("plague"),
    PLOT("plot"),
    POLYP("polyp"),
    POISON("poison"),
    PRESSURE("pressure"),
    PREY("prey"),
    PUPA("pupa"),
    REACH("reach"),
    REPAIR("repair"),
    RUST("rust"),
    QUEST("quest"),
    SILVER("silver"),
    SCREAM("scream"),
    SHELL("shell"),
    SHIELD("shield"),
    SHRED("shred"),
    SLEEP("sleep"),
    SLIME("slime"),
    SLUMBER("slumber"),
    SOOT("soot"),
    SOUL("soul"),
    SPITE("spite"),
    SPORE("spore"),
    STORAGE("storage"),
    STRIFE("strife"),
    STUDY("study"),
    TASK("task"),
    THEFT("theft"),
    TIDE("tide"),
    TIME("time"),
    TOWER("tower"),
    TRAINING("training"),
    TRAMPLE("trample"),
    TRAP("trap"),
    TREASURE("treasure"),
    UNITY("unity"),
    VELOCITY("velocity"),
    VERSE("verse"),
    VIGILANCE("vigilance"),
    VITALITY("vitality"),
    VORTEX("vortex"),
    WAGE("wage"),
    WINCH("winch"),
    WIND("wind"),
    WISH("wish");

    private final String name;
    private final CounterPredicate predicate;

    CounterType(String name) {
        this.name = name;
        this.predicate = new CounterPredicate(this);
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
            case DEATHTOUCH:
                return new AbilityCounter(DeathtouchAbility.getInstance(), amount);
            case DOUBLE_STRIKE:
                return new AbilityCounter(DoubleStrikeAbility.getInstance(), amount);
            case FIRST_STRIKE:
                return new AbilityCounter(FirstStrikeAbility.getInstance(), amount);
            case FLYING:
                return new AbilityCounter(FlyingAbility.getInstance(), amount);
            case HEXPROOF:
                return new AbilityCounter(HexproofAbility.getInstance(), amount);
            case INDESTRUCTIBLE:
                return new AbilityCounter(IndestructibleAbility.getInstance(), amount);
            case LIFELINK:
                return new AbilityCounter(LifelinkAbility.getInstance(), amount);
            case MENACE:
                return new AbilityCounter(new MenaceAbility(), amount);
            case REACH:
                return new AbilityCounter(ReachAbility.getInstance(), amount);
            case TRAMPLE:
                return new AbilityCounter(TrampleAbility.getInstance(), amount);
            case VIGILANCE:
                return new AbilityCounter(VigilanceAbility.getInstance(), amount);
            default:
                return new Counter(name, amount);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public static CounterType findByName(String name) {
        for (CounterType counterType : values()) {
            if (counterType.getName().equals(name)) {
                return counterType;
            }
        }
        return null;
    }

    public CounterPredicate getPredicate() {
        return predicate;
    }

    private static class CounterPredicate implements Predicate<Card> {

        private final CounterType counter;

        private CounterPredicate(CounterType counter) {
            this.counter = counter;
        }

        @Override
        public boolean apply(Card input, Game game) {
            if (counter == null) {
                return !input.getCounters(game).keySet().isEmpty();
            } else {
                return input.getCounters(game).containsKey(counter);
            }
        }

        @Override
        public String toString() {
            return "CounterType(" + counter.getName() + ')';
        }
    }
}
