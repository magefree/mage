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

    ACORN("acorn"),
    AEGIS("aegis"),
    AGE("age"),
    AIM("aim"),
    ARROW("arrow"),
    ARROWHEAD("arrowhead"),
    AWAKENING("awakening"),
    BLAZE("blaze"),
    BLOOD("blood"),
    BLOODLINE("bloodline"),
    BOOK("book"),
    BOUNTY("bounty"),
    BRIBERY("bribery"),
    BRICK("brick"),
    CAGE("cage"),
    CARRION("carrion"),
    CHARGE("charge"),
    CHIP("chip"),
    COIN("coin"),
    COLLECTION("collection"),
    COMPONENT("component"),
    CONTESTED("contested"),
    CORPSE("corpse"),
    CORRUPTION("corruption"),
    CREDIT("credit"),
    CROAK("croak"),
    CRYSTAL("crystal"),
    CUBE("cube"),
    CURRENCY("currency"),
    DEATH("death"),
    DEATHTOUCH("deathtouch"),
    DELAY("delay"),
    DEPLETION("depletion"),
    DESCENT("descent"),
    DESPAIR("despair"),
    DEVOTION("devotion"),
    DIVINITY("divinity"),
    DOOM("doom"),
    DOUBLE_STRIKE("double strike"),
    DREAM("dream"),
    ECHO("echo"),
    EGG("egg"),
    ELIXIR("elixir"),
    EMBER("ember"),
    ENERGY("energy"),
    ENLIGHTENED("enlightened"),
    EON("eon"),
    EXPERIENCE("experience"),
    EYEBALL("eyeball"),
    FADE("fade"),
    FATE("fate"),
    FEATHER("feather"),
    FETCH("fetch"),
    FILIBUSTER("filibuster"),
    FIRST_STRIKE("first strike"),
    FLAME("flame"),
    FLOOD("flood"),
    FLYING("flying"),
    FORESHADOW("foreshadow"),
    FUNK("funk"),
    FURY("fury"),
    FUNGUS("fungus"),
    FUSE("fuse"),
    GEM("gem"),
    GHOSTFORM("ghostform"),
    GLOBE("globe"),
    GLYPH("glyph"),
    GOLD("gold"),
    GROWTH("growth"),
    HARMONY("harmony"),
    HATCHLING("hatchling"),
    HEALING("healing"),
    HEXPROOF("hexproof"),
    HIT("hit"),
    HOOFPRINT("hoofprint"),
    HONE("hone"),
    HOUR("hour", "an"),
    HOURGLASS("hourglass", "an"),
    HUNGER("hunger"),
    ICE("ice"),
    INCARNATION("incarnation"),
    INDESTRUCTIBLE("indestructible"),
    INFECTION("infection"),
    INTERVENTION("intervention"),
    INVITATION("invitation"),
    ISOLATION("isolation"),
    JAVELIN("javelin"),
    JUDGMENT("judgment"),
    KNOWLEDGE("knowledge"),
    KI("ki"),
    KICK("kick"),
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
    M0M1(new BoostCounter(-0, -1).name),
    M0M2(new BoostCounter(-0, -2).name),
    M1M1(new BoostCounter(-1, -1).name),
    M1M0(new BoostCounter(-1, -0).name),
    M2M1(new BoostCounter(-2, -1).name),
    M2M2(new BoostCounter(-2, -2).name),
    MINE("mine"),
    MINING("mining"),
    MIRE("mire"),
    MUSIC("music"),
    MUSTER("muster"),
    NET("net"),
    NIGHT("night"),
    OMEN("omen"),
    ORE("ore"),
    P0P1(new BoostCounter(0, 1).name),
    P1P0(new BoostCounter(1, 0).name),
    P1P1(new BoostCounter(1, 1).name),
    P1P2(new BoostCounter(1, 2).name),
    P2P2(new BoostCounter(2, 2).name),
    PAGE("page"),
    PAIN("pain"),
    PALLIATION("palliation"),
    PARALYZATION("paralyzation"),
    PETAL("petal"),
    PETRIFICATION("petrification"),
    PHYLACTERY("phylactery"),
    PIN("pin"),
    PLAGUE("plague"),
    PLOT("plot"),
    POLYP("polyp"),
    POINT("point"),
    POISON("poison"),
    PRESSURE("pressure"),
    PREY("prey"),
    PUPA("pupa"),
    REACH("reach"),
    REPAIR("repair"),
    RITUAL("ritual"),
    ROPE("rope"),
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
    STASH("stash"),
    STORAGE("storage"),
    STRIFE("strife"),
    STUDY("study"),
    SUSPECT("suspect"),
    TASK("task"),
    THEFT("theft"),
    TIDE("tide"),
    TIME("time"),
    TOWER("tower"),
    TRAINING("training"),
    TRAMPLE("trample"),
    TRAP("trap"),
    TREASURE("treasure"),
    UNITY("unity", "a"),
    VALOR("valor"),
    VELOCITY("velocity"),
    VERSE("verse"),
    VIGILANCE("vigilance"),
    VITALITY("vitality"),
    VOID("void"),
    VORTEX("vortex"),
    VOW("vow"),
    VOYAGE("voyage"),
    WAGE("wage"),
    WINCH("winch"),
    WIND("wind"),
    WISH("wish");

    private final String name;
    private final String article;
    private final CounterPredicate predicate;

    CounterType(String name) {
        this(name, "aeiou".contains("" + name.charAt(0)) ? "an" : "a");
    }

    CounterType(String name, String article) {
        this.name = name;
        this.article = article;
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

    public String getArticle() {
        return article;
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
            case M0M1:
                return new BoostCounter(0, -1, amount);
            case M0M2:
                return new BoostCounter(0, -2, amount);
            case M1M0:
                return new BoostCounter(-1, 0, amount);
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

    public static String findArticle(String name) {
        CounterType counterType = findByName(name);
        return counterType == null ? "a" : counterType.article;
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
