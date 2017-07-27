package mage.constants;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import mage.util.SubTypeList;

public enum SubType {

    //205.3k Instants and sorceries share their lists of subtypes; these subtypes are called spell types.
    ARCANE("Arcane", SubTypeSet.SpellType, false),
    TRAP("Trap", SubTypeSet.SpellType, false),
    // 205.3i: Lands have their own unique set of subtypes; these subtypes are called land types.
    // Of that list, Forest, Island, Mountain, Plains, and Swamp are the basic land types.
    FOREST("Forest", SubTypeSet.BasicLandType, false),
    ISLAND("Island", SubTypeSet.BasicLandType, false),
    MOUNTAIN("Mountain", SubTypeSet.BasicLandType, false),
    PLAINS("Plains", SubTypeSet.BasicLandType, false),
    SWAMP("Swamp", SubTypeSet.BasicLandType, false),
    DESERT("Desert", SubTypeSet.NonBasicLandType, false),
    GATE("Gate", SubTypeSet.NonBasicLandType, false),
    LAIR("Lair", SubTypeSet.NonBasicLandType, false),
    LOCUS("Locus", SubTypeSet.NonBasicLandType, false),
    URZAS("Urza's", SubTypeSet.NonBasicLandType, false),
    MINE("Mine", SubTypeSet.NonBasicLandType, false),
    POWER_PLANT("Power-Plant", SubTypeSet.NonBasicLandType, false),
    TOWER("Tower", SubTypeSet.NonBasicLandType, false),
    // 205.3h Enchantments have their own unique set of subtypes; these subtypes are called enchantment types.
    AURA("Aura", SubTypeSet.EnchantmentType, false),
    CARTOUCHE("Cartouche", SubTypeSet.EnchantmentType, false),
    CURSE("Curse", SubTypeSet.EnchantmentType, false),
    SHRINE("Shrine", SubTypeSet.EnchantmentType, false),
    // 205.3g: Artifacts have their own unique set of subtypes; these subtypes are called artifact types.
    CLUE("Clue", SubTypeSet.ArtifactType, false),
    CONTRAPTION("Contraption", SubTypeSet.ArtifactType, false),
    EQUIPMENT("Equipment", SubTypeSet.ArtifactType, false),
    FORTIFICATION("Fortification", SubTypeSet.ArtifactType, false),
    VEHICLE("Vehicle", SubTypeSet.ArtifactType, false),
    // 205.3m : Creatures and tribals share their lists of subtypes; these subtypes are called creature types.
    // A
    ADVISOR("Advisor", SubTypeSet.CreatureType, false),
    AETHERBORN("Aetherborn", SubTypeSet.CreatureType, false),
    ALLY("Ally", SubTypeSet.CreatureType, false),
    ANGEL("Angel", SubTypeSet.CreatureType, false),
    ANTELOPE("Antelope", SubTypeSet.CreatureType, false),
    AQUALISH("Aqualish", SubTypeSet.CreatureType, true), // Star Wars
    APE("Ape", SubTypeSet.CreatureType, false),
    ARCONA("Arcona", SubTypeSet.CreatureType, true),
    ARCHER("Archer", SubTypeSet.CreatureType, false),
    ARCHON("Archon", SubTypeSet.CreatureType, false),
    ARTIFICER("Artificer", SubTypeSet.CreatureType, false),
    ARTIFICIER("Artificier", SubTypeSet.CreatureType, true),
    ASSASSIN("Assassin", SubTypeSet.CreatureType, false),
    ASSEMBLY_WORKER("Assembly-Worker", SubTypeSet.CreatureType, false),
    ATOG("Atog", SubTypeSet.CreatureType, false),
    ATAT("AT-AT", SubTypeSet.CreatureType, true),
    AUROCHS("Aurochs", SubTypeSet.CreatureType, false),
    AVATAR("Avatar", SubTypeSet.CreatureType, false),
    // B
    BADGER("Badger", SubTypeSet.CreatureType, false),
    BARBARIAN("Barbarian", SubTypeSet.CreatureType, false),
    BASILISK("Basilisk", SubTypeSet.CreatureType, false),
    BAT("Bat", SubTypeSet.CreatureType, false),
    BEAR("Bear", SubTypeSet.CreatureType, false),
    BEAST("Beast", SubTypeSet.CreatureType, false),
    BEEBLE("Beeble", SubTypeSet.CreatureType, false),
    BERSERKER("Berserker", SubTypeSet.CreatureType, false),
    BIRD("Bird", SubTypeSet.CreatureType, false),
    BITH("Bith", SubTypeSet.CreatureType, true), // Star Wars
    BLINKMOTH("Blinkmoth", SubTypeSet.CreatureType, false),
    BOAR("Boar", SubTypeSet.CreatureType, false),
    BRINGER("Bringer", SubTypeSet.CreatureType, false),
    BRUSHWAGG("Brushwagg", SubTypeSet.CreatureType, false),
    // C
    CALAMARI("Calamari", SubTypeSet.CreatureType, true), // Star Wars
    CAMARID("Camarid", SubTypeSet.CreatureType, false),
    CAMEL("Camel", SubTypeSet.CreatureType, false),
    CARIBOU("Caribou", SubTypeSet.CreatureType, false),
    CARRIER("Carrier", SubTypeSet.CreatureType, false),
    CAT("Cat", SubTypeSet.CreatureType, false),
    CENTAUR("Centaur", SubTypeSet.CreatureType, false),
    CEREAN("Cerean", SubTypeSet.CreatureType, true), // Star Wars
    CEPHALID("Cephalid", SubTypeSet.CreatureType, false),
    CHIMERA("Chimera", SubTypeSet.CreatureType, false),
    CHISS("Chiss", SubTypeSet.CreatureType, true),
    CITIZEN("Citizen", SubTypeSet.CreatureType, false),
    CLERIC("Cleric", SubTypeSet.CreatureType, false),
    COCKATRICE("Cockatrice", SubTypeSet.CreatureType, false),
    CONSTRUCT("Construct", SubTypeSet.CreatureType, false),
    COWARD("Coward", SubTypeSet.CreatureType, false),
    CRAB("Crab", SubTypeSet.CreatureType, false),
    CROCODILE("Crocodile", SubTypeSet.CreatureType, false),
    CYBORG("Cyborg", SubTypeSet.CreatureType, true), // Star Wars
    CYCLOPS("Cyclops", SubTypeSet.CreatureType, false),
    // D
    DATHOMIRIAN("Dathomirian", SubTypeSet.CreatureType, true), // Star Wars
    DAUTHI("Dauthi", SubTypeSet.CreatureType, false),
    DEMON("Demon", SubTypeSet.CreatureType, false),
    DESERTER("Deserter", SubTypeSet.CreatureType, false),
    DEVIL("Devil", SubTypeSet.CreatureType, false),
    DJINN("Djinn", SubTypeSet.CreatureType, false),
    DRAGON("Dragon", SubTypeSet.CreatureType, false),
    DRAKE("Drake", SubTypeSet.CreatureType, false),
    DREADNOUGHT("Dreadnought", SubTypeSet.CreatureType, false),
    DRONE("Drone", SubTypeSet.CreatureType, false),
    DRUID("Druid", SubTypeSet.CreatureType, false),
    DROID("Droid", SubTypeSet.CreatureType, true), // Star Wars
    DRYAD("Dryad", SubTypeSet.CreatureType, false),
    DWARF("Dwarf", SubTypeSet.CreatureType, false),
    // E
    EFREET("Efreet", SubTypeSet.CreatureType, false),
    ELDER("Elder", SubTypeSet.CreatureType, false),
    ELDRAZI("Eldrazi", SubTypeSet.CreatureType, false),
    ELEMENTAL("Elemental", SubTypeSet.CreatureType, false),
    ELEPHANT("Elephant", SubTypeSet.CreatureType, false),
    ELF("Elf", SubTypeSet.CreatureType, false),
    ELK("Elk", SubTypeSet.CreatureType, false),
    EYE("Eye", SubTypeSet.CreatureType, false),
    EWOK("Ewok", SubTypeSet.CreatureType, true), // Star Wars
    // F
    FAERIE("Faerie", SubTypeSet.CreatureType, false),
    FERRET("Ferret", SubTypeSet.CreatureType, false),
    FISH("Fish", SubTypeSet.CreatureType, false),
    FLAGBEARER("Flagbearer", SubTypeSet.CreatureType, false),
    FOX("Fox", SubTypeSet.CreatureType, false),
    FROG("Frog", SubTypeSet.CreatureType, false),
    FUNGUS("Fungus", SubTypeSet.CreatureType, false),
    // G
    GAMORREAN("Gamorrean", SubTypeSet.CreatureType, true), // Star Wars
    GAND("Gand", SubTypeSet.CreatureType, true), // Star Wars
    GARGOYLE("Gargoyle", SubTypeSet.CreatureType, false),
    GERM("Germ", SubTypeSet.CreatureType, false),
    GIANT("Giant", SubTypeSet.CreatureType, false),
    GNOME("Gnome", SubTypeSet.CreatureType, false),
    GOLEM("Golem", SubTypeSet.CreatureType, false),
    GOAT("Goat", SubTypeSet.CreatureType, false),
    GOBLIN("Goblin", SubTypeSet.CreatureType, false),
    GOD("God", SubTypeSet.CreatureType, false),
    GORGON("Gorgon", SubTypeSet.CreatureType, false),
    GRAVEBORN("Graveborn", SubTypeSet.CreatureType, false),
    GREMLIN("Gremlin", SubTypeSet.CreatureType, false),
    GRIFFIN("Griffin", SubTypeSet.CreatureType, false),
    GUNGAN("Gungan", SubTypeSet.CreatureType, true), // Star Wars
    // H
    HAG("Hag", SubTypeSet.CreatureType, false),
    HARPY("Harpy", SubTypeSet.CreatureType, false),
    HELLION("Hellion", SubTypeSet.CreatureType, false),
    HIPPO("Hippo", SubTypeSet.CreatureType, false),
    HIPPOGRIFF("Hippogriff", SubTypeSet.CreatureType, false),
    HOMARID("Homarid", SubTypeSet.CreatureType, false),
    HOMUNCULUS("Homunculus", SubTypeSet.CreatureType, false),
    HORROR("Horror", SubTypeSet.CreatureType, false),
    HORSE("Horse", SubTypeSet.CreatureType, false),
    HOUND("Hound", SubTypeSet.CreatureType, false),
    HUMAN("Human", SubTypeSet.CreatureType, false),
    HUNTER("Hunter", SubTypeSet.CreatureType, false),
    HUTT("Hutt", SubTypeSet.CreatureType, true), // Star Wars
    HYDRA("Hydra", SubTypeSet.CreatureType, false),
    HYENA("Hyena", SubTypeSet.CreatureType, false),
    // I
    ILLUSION("Illusion", SubTypeSet.CreatureType, false),
    IMP("Imp", SubTypeSet.CreatureType, false),
    INCARNATION("Incarnation", SubTypeSet.CreatureType, false),
    INSECT("Insect", SubTypeSet.CreatureType, false),
    ITHORIAN("Ithorian", SubTypeSet.CreatureType, true), // Star Wars
    // J
    JACKAL("Jackal", SubTypeSet.CreatureType, false),
    JAWA("Jawa", SubTypeSet.CreatureType, true),
    JEDI("Jedi", SubTypeSet.CreatureType, true), // Star Wars
    JELLYFISH("Jellyfish", SubTypeSet.CreatureType, false),
    JUGGERNAUT("Juggernaut", SubTypeSet.CreatureType, false),
    // K
    KALEESH("Kaleesh", SubTypeSet.CreatureType, true), // Star Wars
    KAVU("Kavu", SubTypeSet.CreatureType, false),
    KELDOR("KelDor", SubTypeSet.CreatureType, true),
    KIRIN("Kirin", SubTypeSet.CreatureType, false),
    KITHKIN("Kithkin", SubTypeSet.CreatureType, false),
    KNIGHT("Knight", SubTypeSet.CreatureType, false),
    KOBOLD("Kobold", SubTypeSet.CreatureType, false),
    KOORIVAR("Koorivar", SubTypeSet.CreatureType, true),
    KOR("Kor", SubTypeSet.CreatureType, false),
    KRAKEN("Kraken", SubTypeSet.CreatureType, false),
    // L
    LAMIA("Lamia", SubTypeSet.CreatureType, false),
    LAMMASU("Lammasu", SubTypeSet.CreatureType, false),
    LEECH("Leech", SubTypeSet.CreatureType, false),
    LEVIATHAN("Leviathan", SubTypeSet.CreatureType, false),
    LHURGOYF("Lhurgoyf", SubTypeSet.CreatureType, false),
    LICID("Licid", SubTypeSet.CreatureType, false),
    LIZARD("Lizard", SubTypeSet.CreatureType, false),
    // M
    MANTELLIAN("Mantellian", SubTypeSet.CreatureType, true), // Star Wars
    MANTICORE("Manticore", SubTypeSet.CreatureType, false),
    MASTICORE("Masticore", SubTypeSet.CreatureType, false),
    MERCENARY("Mercenary", SubTypeSet.CreatureType, false),
    MERFOLK("Merfolk", SubTypeSet.CreatureType, false),
    METATHRAN("Metathran", SubTypeSet.CreatureType, false),
    MINION("Minion", SubTypeSet.CreatureType, false),
    MINOTAUR("Minotaur", SubTypeSet.CreatureType, false),
    MIRIALAN("Mirialan", SubTypeSet.CreatureType, true), // Star Wars
    MOLE("Mole", SubTypeSet.CreatureType, false),
    MONGER("Monger", SubTypeSet.CreatureType, false),
    MONGOOSE("Mongoose", SubTypeSet.CreatureType, false),
    MONK("Monk", SubTypeSet.CreatureType, false),
    MONKEY("Monkey", SubTypeSet.CreatureType, false),
    MOONFOLK("Moonfolk", SubTypeSet.CreatureType, false),
    MUTANT("Mutant", SubTypeSet.CreatureType, false),
    MYR("Myr", SubTypeSet.CreatureType, false),
    MYSTIC("Mystic", SubTypeSet.CreatureType, false),
    // N
    NAGA("Naga", SubTypeSet.CreatureType, false),
    NAUTILUS("Nautilus", SubTypeSet.CreatureType, false),
    NAUTOLAN("Nautolan", SubTypeSet.CreatureType, true), // Star Wars
    NEIMOIDIAN("Neimoidian", SubTypeSet.CreatureType, true), // Star Wars
    NEPHILIM("Nephilim", SubTypeSet.CreatureType, false),
    NIGHTMARE("Nightmare", SubTypeSet.CreatureType, false),
    NIGHTSTALKER("Nightstalker", SubTypeSet.CreatureType, false),
    NINJA("Ninja", SubTypeSet.CreatureType, false),
    NOGGLE("Noggle", SubTypeSet.CreatureType, false),
    NOMAD("Nomad", SubTypeSet.CreatureType, false),
    NYMPH("Nymph", SubTypeSet.CreatureType, false),
    // O
    OCTOPUS("Octopus", SubTypeSet.CreatureType, false),
    OGRE("Ogre", SubTypeSet.CreatureType, false),
    OOZE("Ooze", SubTypeSet.CreatureType, false),
    ORB("Orb", SubTypeSet.CreatureType, false),
    ORC("Orc", SubTypeSet.CreatureType, false),
    ORGG("Orgg", SubTypeSet.CreatureType, false),
    ORTOLAN("Ortolan", SubTypeSet.CreatureType, true),
    OUPHE("Ouphe", SubTypeSet.CreatureType, false),
    OX("Ox", SubTypeSet.CreatureType, false),
    OYSTER("Oyster", SubTypeSet.CreatureType, false),
    // P
    PEGASUS("Pegasus", SubTypeSet.CreatureType, false),
    PENTAVITE("Pentavite", SubTypeSet.CreatureType, false),
    PEST("Pest", SubTypeSet.CreatureType, false),
    PHELDDAGRIF("Phelddagrif", SubTypeSet.CreatureType, false),
    PHOENIX("Phoenix", SubTypeSet.CreatureType, false),
    PILOT("Pilot", SubTypeSet.CreatureType, false),
    PINCHER("Pincher", SubTypeSet.CreatureType, false),
    PIRATE("Pirate", SubTypeSet.CreatureType, false),
    PLANT("Plant", SubTypeSet.CreatureType, false),
    PRAETOR("Praetor", SubTypeSet.CreatureType, false),
    PRISM("Prism", SubTypeSet.CreatureType, false),
    PROCESSOR("Processor", SubTypeSet.CreatureType, false),
    PUREBLOOD("Pureblood", SubTypeSet.CreatureType, true),
    // Q
    QUARREN("Quarren", SubTypeSet.CreatureType, true), // Star Wars
    // R
    RABBIT("Rabbit", SubTypeSet.CreatureType, false),
    RAT("Rat", SubTypeSet.CreatureType, false),
    REBEL("Rebel", SubTypeSet.CreatureType, false),
    REFLECTION("Reflection", SubTypeSet.CreatureType, false),
    RHINO("Rhino", SubTypeSet.CreatureType, false),
    RIGGER("Rigger", SubTypeSet.CreatureType, false),
    RODIAN("Rodian", SubTypeSet.CreatureType, true), // Star Wars
    ROGUE("Rogue", SubTypeSet.CreatureType, false),
    // S
    SABLE("Sable", SubTypeSet.CreatureType, false),
    SALAMANDER("Salamander", SubTypeSet.CreatureType, false),
    SAMURAI("Samurai", SubTypeSet.CreatureType, false),
    SAND("Sand", SubTypeSet.CreatureType, false),
    SAPROLING("Saproling", SubTypeSet.CreatureType, false),
    SATYR("Satyr", SubTypeSet.CreatureType, false),
    SCARECROW("Scarecrow", SubTypeSet.CreatureType, false),
    SCION("Scion", SubTypeSet.CreatureType, false),
    SCORPION("Scorpion", SubTypeSet.CreatureType, false),
    SCOUT("Scout", SubTypeSet.CreatureType, false),
    SERF("Serf", SubTypeSet.CreatureType, false),
    SERPENT("Serpent", SubTypeSet.CreatureType, false),
    SERVO("Servo", SubTypeSet.CreatureType, false),
    SHADE("Shade", SubTypeSet.CreatureType, false),
    SHAMAN("Shaman", SubTypeSet.CreatureType, false),
    SHAPESHIFTER("Shapeshifter", SubTypeSet.CreatureType, false),
    SHEEP("Sheep", SubTypeSet.CreatureType, false),
    SIREN("Siren", SubTypeSet.CreatureType, false),
    SITH("Sith", SubTypeSet.CreatureType, false),
    SKELETON("Skeleton", SubTypeSet.CreatureType, false),
    SLITH("Slith", SubTypeSet.CreatureType, false),
    SLIVER("Sliver", SubTypeSet.CreatureType, false),
    SLUG("Slug", SubTypeSet.CreatureType, false),
    SNAKE("Snake", SubTypeSet.CreatureType, false),
    SOLDIER("Soldier", SubTypeSet.CreatureType, false),
    SOLTARI("Soltari", SubTypeSet.CreatureType, false),
    SPAWN("Spawn", SubTypeSet.CreatureType, false),
    SPECTER("Specter", SubTypeSet.CreatureType, false),
    SPELLSHAPER("Spellshaper", SubTypeSet.CreatureType, false),
    SPHINX("Sphinx", SubTypeSet.CreatureType, false),
    SPIDER("Spider", SubTypeSet.CreatureType, false),
    SPIKE("Spike", SubTypeSet.CreatureType, false),
    SPIRIT("Spirit", SubTypeSet.CreatureType, false),
    SPLITTER("Splitter", SubTypeSet.CreatureType, false),
    SPONGE("Sponge", SubTypeSet.CreatureType, false),
    SQUID("Squid", SubTypeSet.CreatureType, false),
    SQUIRREL("Squirrel", SubTypeSet.CreatureType, false),
    STARFISH("Starfish", SubTypeSet.CreatureType, false),
    STARSHIP("Starship", SubTypeSet.CreatureType, true), // Star Wars
    SULLUSTAN("Sullustan", SubTypeSet.CreatureType, true), // Star Wars
    SURRAKAR("Surrakar", SubTypeSet.CreatureType, false),
    SURVIVOR("Survivor", SubTypeSet.CreatureType, false),
    // T
    TETRAVITE("Tetravite", SubTypeSet.CreatureType, false),
    THALAKOS("Thalakos", SubTypeSet.CreatureType, false),
    THOPTER("Thopter", SubTypeSet.CreatureType, false),
    TRANDOSHAN("Trandoshan", SubTypeSet.CreatureType, true), // Star Wars
    THRULL("Thrull", SubTypeSet.CreatureType, false),
    TREEFOLK("Treefolk", SubTypeSet.CreatureType, false),
    TRISKELAVITE("Triskelavite", SubTypeSet.CreatureType, false),
    TROLL("Troll", SubTypeSet.CreatureType, false),
    TURTLE("Turtle", SubTypeSet.CreatureType, false),
    TROOPER("Trooper", SubTypeSet.CreatureType, true), // Star Wars
    TWILEK("Twi'lek", SubTypeSet.CreatureType, true), // Star Wars

    // U
    UGNAUGHT("Ugnaught", SubTypeSet.CreatureType, true),
    UNICORN("Unicorn", SubTypeSet.CreatureType, false),
    //V
    VAMPIRE("Vampire", SubTypeSet.CreatureType, false),
    VEDALKEN("Vedalken", SubTypeSet.CreatureType, false),
    VIASHINO("Viashino", SubTypeSet.CreatureType, false),
    VOLVER("Volver", SubTypeSet.CreatureType, false),
    //W
    WALL("Wall", SubTypeSet.CreatureType, false),
    WARRIOR("Warrior", SubTypeSet.CreatureType, false),
    WEEQUAY("Weequay", SubTypeSet.CreatureType, true),
    WEIRD("Weird", SubTypeSet.CreatureType, false),
    WEREWOLF("Werewolf", SubTypeSet.CreatureType, false),
    WHALE("Whale", SubTypeSet.CreatureType, false),
    WIZARD("Wizard", SubTypeSet.CreatureType, false),
    WOLF("Wolf", SubTypeSet.CreatureType, false),
    WOLVERINE("Wolverine", SubTypeSet.CreatureType, false),
    WOMBAT("Wombat", SubTypeSet.CreatureType, false),
    WOOKIEE("Wookiee", SubTypeSet.CreatureType, true), // Star Wars
    WORM("Worm", SubTypeSet.CreatureType, false),
    WRAITH("Wraith", SubTypeSet.CreatureType, false),
    WURM("Wurm", SubTypeSet.CreatureType, false),
    // Y
    YETI("Yeti", SubTypeSet.CreatureType, false),
    // Z
    ZABRAK("Zabrak", SubTypeSet.CreatureType, true), // Star Wars
    ZOMBIE("Zombie", SubTypeSet.CreatureType, false),
    ZUBERA("Zubera", SubTypeSet.CreatureType, false),
    // Planeswalker
    AJANI("Ajani", SubTypeSet.PlaneswalkerType, false),
    ARLINN("Arlinn", SubTypeSet.PlaneswalkerType, false),
    ASHIOK("Ashiok", SubTypeSet.PlaneswalkerType, false),
    AURRA("Aurra", SubTypeSet.PlaneswalkerType, true), // Star Wars
    BOLAS("Bolas", SubTypeSet.PlaneswalkerType, false),
    CHANDRA("Chandra", SubTypeSet.PlaneswalkerType, false),
    DACK("Dack", SubTypeSet.PlaneswalkerType, false),
    DARETTI("Daretti", SubTypeSet.PlaneswalkerType, false),
    DOMRI("Domri", SubTypeSet.PlaneswalkerType, false),
    DOOKU("Dooku", SubTypeSet.PlaneswalkerType, true), // Star Wars
    DOVIN("Dovin", SubTypeSet.PlaneswalkerType, false),
    ELSPETH("Elspeth", SubTypeSet.PlaneswalkerType, false),
    FREYALISE("Freyalise", SubTypeSet.PlaneswalkerType, false),
    GARRUK("Garruk", SubTypeSet.PlaneswalkerType, false),
    GIDEON("Gideon", SubTypeSet.PlaneswalkerType, false),
    JACE("Jace", SubTypeSet.PlaneswalkerType, false),
    KARN("Karn", SubTypeSet.PlaneswalkerType, false),
    KAYA("Kaya", SubTypeSet.PlaneswalkerType, false),
    KIORA("Kiora", SubTypeSet.PlaneswalkerType, false),
    KOTH("Koth", SubTypeSet.PlaneswalkerType, false),
    LILIANA("Liliana", SubTypeSet.PlaneswalkerType, false),
    NAHIRI("Nahiri", SubTypeSet.PlaneswalkerType, false),
    NARSET("Narset", SubTypeSet.PlaneswalkerType, false),
    NISSA("Nissa", SubTypeSet.PlaneswalkerType, false),
    NIXILIS("Nixilis", SubTypeSet.PlaneswalkerType, false),
    OBI_WAN("Obi-Wan", SubTypeSet.PlaneswalkerType, true), // Star Wars
    RAL("Ral", SubTypeSet.PlaneswalkerType, false),
    SAHEELI("Saheeli", SubTypeSet.PlaneswalkerType, false),
    SAMUT("Samut", SubTypeSet.PlaneswalkerType, false),
    SARKHAN("Sarkhan", SubTypeSet.PlaneswalkerType, false),
    SIDIOUS("Sidious", SubTypeSet.PlaneswalkerType, true), // Star Wars
    SORIN("Sorin", SubTypeSet.PlaneswalkerType, false),
    TAMIYO("Tamiyo", SubTypeSet.PlaneswalkerType, false),
    TEFERI("Teferi", SubTypeSet.PlaneswalkerType, false),
    TEZZERET("Tezzeret", SubTypeSet.PlaneswalkerType, false),
    TIBALT("Tibalt", SubTypeSet.PlaneswalkerType, false),
    UGIN("Ugin", SubTypeSet.PlaneswalkerType, false),
    VENSER("Venser", SubTypeSet.PlaneswalkerType, false),
    VRASKA("Vraska", SubTypeSet.PlaneswalkerType, false),
    XENAGOS("Xenagos", SubTypeSet.PlaneswalkerType, false),
    YODA("Yoda", SubTypeSet.PlaneswalkerType, true);  // Star Wars

    private final SubTypeSet subTypeSet;

    public String getDescription() {
        return description;
    }

    private final String description;

    private final boolean customSet;

    @Override
    public String toString() {
        return description;
    }

    SubType(String description, SubTypeSet subTypeSet, boolean customSet) {
        this.description = description;
        this.subTypeSet = subTypeSet;
        this.customSet = customSet;
    }

    public static SubType byDescription(String subType) {
        for (SubType s : values()) {
            if (s.getDescription().equals(subType)) {
                return s;
            }
        }
        throw new IllegalArgumentException("no subtype for " + subType + " exists");
    }

    public SubTypeSet getSubTypeSet() {
        return subTypeSet;
    }

    public static SubTypeList getCreatureTypes(boolean customSet) {
        SubTypeList subTypes = new SubTypeList();
        for (SubType s : values()) {
            if (!s.customSet) {
                subTypes.add(s);
            }
        }
        return subTypes;
    }

    public static Set<String> getBasicLands(boolean customSet) {
        return Arrays.stream(values()).filter(s -> s.customSet == customSet).filter(p -> p.getSubTypeSet() == SubTypeSet.BasicLandType).map(SubType::getDescription).collect(Collectors.toSet());
    }

    public static SubTypeList getLandTypes(boolean customSet) {
        SubTypeList landTypes = new SubTypeList();
        for (SubType s : values()) {
            if (s.getSubTypeSet() == SubTypeSet.BasicLandType || s.getSubTypeSet() == SubTypeSet.NonBasicLandType) {
                landTypes.add(s);
            }
        }
        return landTypes;
    }
}
