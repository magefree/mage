package mage.constants;

import mage.util.SubTypeList;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum SubType {

    //205.3k Instants and sorceries share their lists of subtypes; these subtypes are called spell types.
    ARCANE("Arcane", SubTypeSet.SpellType),
    TRAP("Trap", SubTypeSet.SpellType),
    // 205.3i: Lands have their own unique set of subtypes; these subtypes are called land types.
    // Of that list, Forest, Island, Mountain, Plains, and Swamp are the basic land types.
    FOREST("Forest", SubTypeSet.BasicLandType),
    ISLAND("Island", SubTypeSet.BasicLandType),
    MOUNTAIN("Mountain", SubTypeSet.BasicLandType),
    PLAINS("Plains", SubTypeSet.BasicLandType),
    SWAMP("Swamp", SubTypeSet.BasicLandType),
    DESERT("Desert", SubTypeSet.NonBasicLandType),
    GATE("Gate", SubTypeSet.NonBasicLandType),
    LAIR("Lair", SubTypeSet.NonBasicLandType),
    LOCUS("Locus", SubTypeSet.NonBasicLandType),
    URZAS("Urza's", SubTypeSet.NonBasicLandType),
    MINE("Mine", SubTypeSet.NonBasicLandType),
    POWER_PLANT("Power-Plant", SubTypeSet.NonBasicLandType),
    TOWER("Tower", SubTypeSet.NonBasicLandType),
    // 205.3h Enchantments have their own unique set of subtypes; these subtypes are called enchantment types.
    AURA("Aura", SubTypeSet.EnchantmentType),
    CARTOUCHE("Cartouche", SubTypeSet.EnchantmentType),
    CURSE("Curse", SubTypeSet.EnchantmentType),
    SAGA("Saga", SubTypeSet.EnchantmentType),
    SHRINE("Shrine", SubTypeSet.EnchantmentType),
    // 205.3g: Artifacts have their own unique set of subtypes; these subtypes are called artifact types.
    CLUE("Clue", SubTypeSet.ArtifactType),
    CONTRAPTION("Contraption", SubTypeSet.ArtifactType),
    EQUIPMENT("Equipment", SubTypeSet.ArtifactType),
    FORTIFICATION("Fortification", SubTypeSet.ArtifactType),
    TREASURE("Treasure", SubTypeSet.ArtifactType),
    VEHICLE("Vehicle", SubTypeSet.ArtifactType),
    // 205.3m : Creatures and tribals share their lists of subtypes; these subtypes are called creature types.
    // A
    ADVISOR("Advisor", SubTypeSet.CreatureType),
    AETHERBORN("Aetherborn", SubTypeSet.CreatureType),
    ALLY("Ally", SubTypeSet.CreatureType),
    ANGEL("Angel", SubTypeSet.CreatureType),
    ANTELOPE("Antelope", SubTypeSet.CreatureType),
    AQUALISH("Aqualish", SubTypeSet.CreatureType, true), // Star Wars
    APE("Ape", SubTypeSet.CreatureType),
    ARCONA("Arcona", SubTypeSet.CreatureType, true),
    ARCHER("Archer", SubTypeSet.CreatureType),
    ARCHON("Archon", SubTypeSet.CreatureType),
    ARTIFICER("Artificer", SubTypeSet.CreatureType),
    ARMY("Army", SubTypeSet.CreatureType),
    ARTIFICIER("Artificier", SubTypeSet.CreatureType, true),
    ASSASSIN("Assassin", SubTypeSet.CreatureType),
    ASSEMBLY_WORKER("Assembly-Worker", SubTypeSet.CreatureType),
    ATOG("Atog", SubTypeSet.CreatureType),
    ATAT("AT-AT", SubTypeSet.CreatureType, true),
    AUROCHS("Aurochs", SubTypeSet.CreatureType),
    AUTOBOT("Autobot", SubTypeSet.CreatureType, true), // H17, Grimlock
    AVATAR("Avatar", SubTypeSet.CreatureType),
    AZRA("Azra", SubTypeSet.CreatureType),
    // B
    BADGER("Badger", SubTypeSet.CreatureType),
    BARABEL("Barabel", SubTypeSet.CreatureType, true), // Star Wars
    BARBARIAN("Barbarian", SubTypeSet.CreatureType),
    BASILISK("Basilisk", SubTypeSet.CreatureType),
    BAT("Bat", SubTypeSet.CreatureType),
    BEAR("Bear", SubTypeSet.CreatureType),
    BEAST("Beast", SubTypeSet.CreatureType),
    BEEBLE("Beeble", SubTypeSet.CreatureType),
    BERSERKER("Berserker", SubTypeSet.CreatureType),
    BIRD("Bird", SubTypeSet.CreatureType),
    BITH("Bith", SubTypeSet.CreatureType, true), // Star Wars
    BLINKMOTH("Blinkmoth", SubTypeSet.CreatureType),
    BOAR("Boar", SubTypeSet.CreatureType),
    BRAINIAC("Brainiac", SubTypeSet.CreatureType, true), // Unstable
    BRINGER("Bringer", SubTypeSet.CreatureType),
    BRUSHWAGG("Brushwagg", SubTypeSet.CreatureType),
    // C
    CALAMARI("Calamari", SubTypeSet.CreatureType, true), // Star Wars
    CAMARID("Camarid", SubTypeSet.CreatureType),
    CAMEL("Camel", SubTypeSet.CreatureType),
    CARIBOU("Caribou", SubTypeSet.CreatureType),
    CARRIER("Carrier", SubTypeSet.CreatureType),
    CAT("Cat", SubTypeSet.CreatureType),
    CENTAUR("Centaur", SubTypeSet.CreatureType),
    CEREAN("Cerean", SubTypeSet.CreatureType, true), // Star Wars
    CEPHALID("Cephalid", SubTypeSet.CreatureType),
    CHICKEN("Chicken", SubTypeSet.CreatureType, true), // Unglued
    CHIMERA("Chimera", SubTypeSet.CreatureType),
    CHISS("Chiss", SubTypeSet.CreatureType, true),
    CITIZEN("Citizen", SubTypeSet.CreatureType),
    CLAMFOLK("Clamfolk", SubTypeSet.CreatureType, true), // Unglued
    CLERIC("Cleric", SubTypeSet.CreatureType),
    COCKATRICE("Cockatrice", SubTypeSet.CreatureType),
    CONSTRUCT("Construct", SubTypeSet.CreatureType),
    COW("Cow", SubTypeSet.CreatureType, true), // Unglued
    COWARD("Coward", SubTypeSet.CreatureType),
    CRAB("Crab", SubTypeSet.CreatureType),
    CROCODILE("Crocodile", SubTypeSet.CreatureType),
    CROLUTE("Crolute", SubTypeSet.CreatureType, true), // Star Wars
    CYBORG("Cyborg", SubTypeSet.CreatureType, true), // Star Wars
    CYCLOPS("Cyclops", SubTypeSet.CreatureType),
    // D
    DATHOMIRIAN("Dathomirian", SubTypeSet.CreatureType, true), // Star Wars
    DAUTHI("Dauthi", SubTypeSet.CreatureType),
    DEMON("Demon", SubTypeSet.CreatureType),
    DESERTER("Deserter", SubTypeSet.CreatureType),
    DEVIL("Devil", SubTypeSet.CreatureType),
    DINOSAUR("Dinosaur", SubTypeSet.CreatureType), // With Ixalan now being spoiled, need this to be selectable
    DJINN("Djinn", SubTypeSet.CreatureType),
    DRAGON("Dragon", SubTypeSet.CreatureType),
    DRAKE("Drake", SubTypeSet.CreatureType),
    DREADNOUGHT("Dreadnought", SubTypeSet.CreatureType),
    DRONE("Drone", SubTypeSet.CreatureType),
    DRUID("Druid", SubTypeSet.CreatureType),
    DROID("Droid", SubTypeSet.CreatureType, true), // Star Wars
    DRYAD("Dryad", SubTypeSet.CreatureType),
    DWARF("Dwarf", SubTypeSet.CreatureType),
    // E
    EFREET("Efreet", SubTypeSet.CreatureType),
    EGG("Egg", SubTypeSet.CreatureType),
    ELDER("Elder", SubTypeSet.CreatureType),
    ELDRAZI("Eldrazi", SubTypeSet.CreatureType),
    ELEMENTAL("Elemental", SubTypeSet.CreatureType),
    ELEPHANT("Elephant", SubTypeSet.CreatureType),
    ELF("Elf", SubTypeSet.CreatureType),
    ELVES("Elves", SubTypeSet.CreatureType, true), // Un-sets
    ELK("Elk", SubTypeSet.CreatureType),
    EYE("Eye", SubTypeSet.CreatureType),
    EWOK("Ewok", SubTypeSet.CreatureType, true), // Star Wars
    EXPANSION_SYMBOL("Expansion-Symbol", SubTypeSet.CreatureType, true), // Unhinged
    // F
    FAERIE("Faerie", SubTypeSet.CreatureType),
    FERRET("Ferret", SubTypeSet.CreatureType),
    FISH("Fish", SubTypeSet.CreatureType),
    FLAGBEARER("Flagbearer", SubTypeSet.CreatureType),
    FOX("Fox", SubTypeSet.CreatureType),
    FROG("Frog", SubTypeSet.CreatureType),
    FUNGUS("Fungus", SubTypeSet.CreatureType),
    // G
    GAMER("Gamer", SubTypeSet.CreatureType, true), // Un-sets
    GAMORREAN("Gamorrean", SubTypeSet.CreatureType, true), // Star Wars
    GAND("Gand", SubTypeSet.CreatureType, true), // Star Wars
    GARGOYLE("Gargoyle", SubTypeSet.CreatureType),
    GERM("Germ", SubTypeSet.CreatureType),
    GIANT("Giant", SubTypeSet.CreatureType),
    GNOME("Gnome", SubTypeSet.CreatureType),
    GOLEM("Golem", SubTypeSet.CreatureType),
    GOAT("Goat", SubTypeSet.CreatureType),
    GOBLIN("Goblin", SubTypeSet.CreatureType),
    GOD("God", SubTypeSet.CreatureType),
    GORGON("Gorgon", SubTypeSet.CreatureType),
    GRAVEBORN("Graveborn", SubTypeSet.CreatureType),
    GREMLIN("Gremlin", SubTypeSet.CreatureType),
    GRIFFIN("Griffin", SubTypeSet.CreatureType),
    GUNGAN("Gungan", SubTypeSet.CreatureType, true), // Star Wars
    // H
    HAG("Hag", SubTypeSet.CreatureType),
    HARPY("Harpy", SubTypeSet.CreatureType),
    HELLION("Hellion", SubTypeSet.CreatureType),
    HIPPO("Hippo", SubTypeSet.CreatureType),
    HIPPOGRIFF("Hippogriff", SubTypeSet.CreatureType),
    HOMARID("Homarid", SubTypeSet.CreatureType),
    HOMUNCULUS("Homunculus", SubTypeSet.CreatureType),
    HORROR("Horror", SubTypeSet.CreatureType),
    HORSE("Horse", SubTypeSet.CreatureType),
    HOUND("Hound", SubTypeSet.CreatureType),
    HUMAN("Human", SubTypeSet.CreatureType),
    HUNTER("Hunter", SubTypeSet.CreatureType),
    HUTT("Hutt", SubTypeSet.CreatureType, true), // Star Wars
    HYDRA("Hydra", SubTypeSet.CreatureType),
    HYENA("Hyena", SubTypeSet.CreatureType),
    // I
    ILLUSION("Illusion", SubTypeSet.CreatureType),
    IMP("Imp", SubTypeSet.CreatureType),
    INCARNATION("Incarnation", SubTypeSet.CreatureType),
    INSECT("Insect", SubTypeSet.CreatureType),
    ITHORIAN("Ithorian", SubTypeSet.CreatureType, true), // Star Wars
    // J
    JACKAL("Jackal", SubTypeSet.CreatureType),
    JAWA("Jawa", SubTypeSet.CreatureType, true),
    JAYA("Jaya", SubTypeSet.PlaneswalkerType),
    JEDI("Jedi", SubTypeSet.CreatureType, true), // Star Wars
    JELLYFISH("Jellyfish", SubTypeSet.CreatureType),
    JUGGERNAUT("Juggernaut", SubTypeSet.CreatureType),
    // K
    KALEESH("Kaleesh", SubTypeSet.CreatureType, true), // Star Wars
    KAVU("Kavu", SubTypeSet.CreatureType),
    KELDOR("KelDor", SubTypeSet.CreatureType, true),
    KILLBOT("Killbot", SubTypeSet.CreatureType, true), // Unstable
    KIRIN("Kirin", SubTypeSet.CreatureType),
    KITHKIN("Kithkin", SubTypeSet.CreatureType),
    KNIGHT("Knight", SubTypeSet.CreatureType),
    KOBOLD("Kobold", SubTypeSet.CreatureType),
    KOORIVAR("Koorivar", SubTypeSet.CreatureType, true),
    KOR("Kor", SubTypeSet.CreatureType),
    KRAKEN("Kraken", SubTypeSet.CreatureType),
    // L
    LADYOFPROPERETIQUETTE("Lady of Proper Etiquette", SubTypeSet.CreatureType, true), // Unglued
    LAMIA("Lamia", SubTypeSet.CreatureType),
    LAMMASU("Lammasu", SubTypeSet.CreatureType),
    LEECH("Leech", SubTypeSet.CreatureType),
    LEVIATHAN("Leviathan", SubTypeSet.CreatureType),
    LHURGOYF("Lhurgoyf", SubTypeSet.CreatureType),
    LICID("Licid", SubTypeSet.CreatureType),
    LIZARD("Lizard", SubTypeSet.CreatureType),
    LOBSTER("Lobster", SubTypeSet.CreatureType, true), // Unglued
    LUKE("Luke", SubTypeSet.PlaneswalkerType, true), // Star Wars
    // M
    MANTELLIAN("Mantellian", SubTypeSet.CreatureType, true), // Star Wars
    MANTICORE("Manticore", SubTypeSet.CreatureType),
    MASTICORE("Masticore", SubTypeSet.CreatureType),
    MERCENARY("Mercenary", SubTypeSet.CreatureType),
    MERFOLK("Merfolk", SubTypeSet.CreatureType),
    METATHRAN("Metathran", SubTypeSet.CreatureType),
    MINION("Minion", SubTypeSet.CreatureType),
    MINOTAUR("Minotaur", SubTypeSet.CreatureType),
    MIRIALAN("Mirialan", SubTypeSet.CreatureType, true), // Star Wars
    MOLE("Mole", SubTypeSet.CreatureType),
    MONGER("Monger", SubTypeSet.CreatureType),
    MONGOOSE("Mongoose", SubTypeSet.CreatureType),
    MONK("Monk", SubTypeSet.CreatureType),
    MONKEY("Monkey", SubTypeSet.CreatureType),
    MOONFOLK("Moonfolk", SubTypeSet.CreatureType),
    MUTANT("Mutant", SubTypeSet.CreatureType),
    MYR("Myr", SubTypeSet.CreatureType),
    MYSTIC("Mystic", SubTypeSet.CreatureType),
    // N
    NAGA("Naga", SubTypeSet.CreatureType),
    NAUTILUS("Nautilus", SubTypeSet.CreatureType),
    NAUTOLAN("Nautolan", SubTypeSet.CreatureType, true), // Star Wars
    NEIMOIDIAN("Neimoidian", SubTypeSet.CreatureType, true), // Star Wars
    NEPHILIM("Nephilim", SubTypeSet.CreatureType),
    NIGHTMARE("Nightmare", SubTypeSet.CreatureType),
    NIGHTSTALKER("Nightstalker", SubTypeSet.CreatureType),
    NINJA("Ninja", SubTypeSet.CreatureType),
    NOGGLE("Noggle", SubTypeSet.CreatureType),
    NOMAD("Nomad", SubTypeSet.CreatureType),
    NYMPH("Nymph", SubTypeSet.CreatureType),
    // O
    OCTOPUS("Octopus", SubTypeSet.CreatureType),
    OGRE("Ogre", SubTypeSet.CreatureType),
    OOZE("Ooze", SubTypeSet.CreatureType),
    ORB("Orb", SubTypeSet.CreatureType),
    ORC("Orc", SubTypeSet.CreatureType),
    ORGG("Orgg", SubTypeSet.CreatureType),
    ORTOLAN("Ortolan", SubTypeSet.CreatureType, true),
    OUPHE("Ouphe", SubTypeSet.CreatureType),
    OX("Ox", SubTypeSet.CreatureType),
    OYSTER("Oyster", SubTypeSet.CreatureType),
    // P
    PANGOLIN("Pangolin", SubTypeSet.CreatureType),
    PEGASUS("Pegasus", SubTypeSet.CreatureType),
    PENTAVITE("Pentavite", SubTypeSet.CreatureType),
    PEST("Pest", SubTypeSet.CreatureType),
    PHELDDAGRIF("Phelddagrif", SubTypeSet.CreatureType),
    PHOENIX("Phoenix", SubTypeSet.CreatureType),
    PILOT("Pilot", SubTypeSet.CreatureType),
    PINCHER("Pincher", SubTypeSet.CreatureType),
    PIRATE("Pirate", SubTypeSet.CreatureType),
    PLANT("Plant", SubTypeSet.CreatureType),
    PRAETOR("Praetor", SubTypeSet.CreatureType),
    PRISM("Prism", SubTypeSet.CreatureType),
    PROCESSOR("Processor", SubTypeSet.CreatureType),
    PUREBLOOD("Pureblood", SubTypeSet.CreatureType, true),
    // Q
    QUARREN("Quarren", SubTypeSet.CreatureType, true), // Star Wars
    // R
    RABBIT("Rabbit", SubTypeSet.CreatureType),
    RAIDER("Raider", SubTypeSet.CreatureType, true), // Star Wars
    RAT("Rat", SubTypeSet.CreatureType),
    REBEL("Rebel", SubTypeSet.CreatureType),
    REFLECTION("Reflection", SubTypeSet.CreatureType),
    RHINO("Rhino", SubTypeSet.CreatureType),
    RIGGER("Rigger", SubTypeSet.CreatureType),
    RODIAN("Rodian", SubTypeSet.CreatureType, true), // Star Wars
    ROGUE("Rogue", SubTypeSet.CreatureType),
    // S
    SABLE("Sable", SubTypeSet.CreatureType),
    SALAMANDER("Salamander", SubTypeSet.CreatureType),
    SAMURAI("Samurai", SubTypeSet.CreatureType),
    SAND("Sand", SubTypeSet.CreatureType),
    SAPROLING("Saproling", SubTypeSet.CreatureType),
    SATYR("Satyr", SubTypeSet.CreatureType),
    SCARECROW("Scarecrow", SubTypeSet.CreatureType),
    SCIENTIST("Scientist", SubTypeSet.CreatureType, true), // Unstable
    SCION("Scion", SubTypeSet.CreatureType),
    SCORPION("Scorpion", SubTypeSet.CreatureType),
    SCOUT("Scout", SubTypeSet.CreatureType),
    SCULPTURE("Sculpture", SubTypeSet.CreatureType),
    SERF("Serf", SubTypeSet.CreatureType),
    SERPENT("Serpent", SubTypeSet.CreatureType),
    SERVO("Servo", SubTypeSet.CreatureType),
    SHADE("Shade", SubTypeSet.CreatureType),
    SHAMAN("Shaman", SubTypeSet.CreatureType),
    SHAPESHIFTER("Shapeshifter", SubTypeSet.CreatureType),
    SHEEP("Sheep", SubTypeSet.CreatureType),
    SIREN("Siren", SubTypeSet.CreatureType),
    SITH("Sith", SubTypeSet.CreatureType),
    SKELETON("Skeleton", SubTypeSet.CreatureType),
    SLITH("Slith", SubTypeSet.CreatureType),
    SLIVER("Sliver", SubTypeSet.CreatureType),
    SLUG("Slug", SubTypeSet.CreatureType),
    SNAKE("Snake", SubTypeSet.CreatureType),
    SOLDIER("Soldier", SubTypeSet.CreatureType),
    SOLTARI("Soltari", SubTypeSet.CreatureType),
    SPAWN("Spawn", SubTypeSet.CreatureType),
    SPECTER("Specter", SubTypeSet.CreatureType),
    SPELLSHAPER("Spellshaper", SubTypeSet.CreatureType),
    SPHINX("Sphinx", SubTypeSet.CreatureType),
    SPIDER("Spider", SubTypeSet.CreatureType),
    SPIKE("Spike", SubTypeSet.CreatureType),
    SPIRIT("Spirit", SubTypeSet.CreatureType),
    SPLINTER("Splinter", SubTypeSet.CreatureType),
    SPLITTER("Splitter", SubTypeSet.CreatureType),
    SPONGE("Sponge", SubTypeSet.CreatureType),
    SQUID("Squid", SubTypeSet.CreatureType),
    SQUIRREL("Squirrel", SubTypeSet.CreatureType),
    SNOKE("Snoke", SubTypeSet.PlaneswalkerType, true), // Star Wars
    STARFISH("Starfish", SubTypeSet.CreatureType),
    STARSHIP("Starship", SubTypeSet.CreatureType, true), // Star Wars
    SULLUSTAN("Sullustan", SubTypeSet.CreatureType, true), // Star Wars
    SURRAKAR("Surrakar", SubTypeSet.CreatureType),
    SURVIVOR("Survivor", SubTypeSet.CreatureType),
    // T
    TETRAVITE("Tetravite", SubTypeSet.CreatureType),
    THALAKOS("Thalakos", SubTypeSet.CreatureType),
    THOPTER("Thopter", SubTypeSet.CreatureType),
    TRANDOSHAN("Trandoshan", SubTypeSet.CreatureType, true), // Star Wars
    THRULL("Thrull", SubTypeSet.CreatureType),
    TREEFOLK("Treefolk", SubTypeSet.CreatureType),
    TRISKELAVITE("Triskelavite", SubTypeSet.CreatureType),
    TROLL("Troll", SubTypeSet.CreatureType),
    TURTLE("Turtle", SubTypeSet.CreatureType),
    TUSKEN("Tusken", SubTypeSet.CreatureType, true), // Star Wars
    TROOPER("Trooper", SubTypeSet.CreatureType, true), // Star Wars
    TRILOBITE("Trilobite", SubTypeSet.CreatureType),
    TWILEK("Twi'lek", SubTypeSet.CreatureType, true), // Star Wars
    // U
    UGNAUGHT("Ugnaught", SubTypeSet.CreatureType, true),
    UNICORN("Unicorn", SubTypeSet.CreatureType),
    // V
    VAMPIRE("Vampire", SubTypeSet.CreatureType),
    VEDALKEN("Vedalken", SubTypeSet.CreatureType),
    VIASHINO("Viashino", SubTypeSet.CreatureType),
    VILLAIN("Villain", SubTypeSet.CreatureType, true), // Unstable
    VOLVER("Volver", SubTypeSet.CreatureType),
    // W
    WALL("Wall", SubTypeSet.CreatureType),
    WARRIOR("Warrior", SubTypeSet.CreatureType),
    WEEQUAY("Weequay", SubTypeSet.CreatureType, true),
    WEIRD("Weird", SubTypeSet.CreatureType),
    WEREWOLF("Werewolf", SubTypeSet.CreatureType),
    WHALE("Whale", SubTypeSet.CreatureType),
    WIZARD("Wizard", SubTypeSet.CreatureType),
    WOLF("Wolf", SubTypeSet.CreatureType),
    WOLVERINE("Wolverine", SubTypeSet.CreatureType),
    WOMBAT("Wombat", SubTypeSet.CreatureType),
    WOOKIEE("Wookiee", SubTypeSet.CreatureType, true), // Star Wars
    WORM("Worm", SubTypeSet.CreatureType),
    WRAITH("Wraith", SubTypeSet.CreatureType),
    WURM("Wurm", SubTypeSet.CreatureType),
    // Y
    YETI("Yeti", SubTypeSet.CreatureType),
    // Z
    ZABRAK("Zabrak", SubTypeSet.CreatureType, true), // Star Wars
    ZOMBIE("Zombie", SubTypeSet.CreatureType),
    ZUBERA("Zubera", SubTypeSet.CreatureType),
    // Planeswalker
    AJANI("Ajani", SubTypeSet.PlaneswalkerType),
    AMINATOU("Aminatou", SubTypeSet.PlaneswalkerType),
    ANGRATH("Angrath", SubTypeSet.PlaneswalkerType),
    ARLINN("Arlinn", SubTypeSet.PlaneswalkerType),
    ASHIOK("Ashiok", SubTypeSet.PlaneswalkerType),
    AURRA("Aurra", SubTypeSet.PlaneswalkerType, true), // Star Wars
    BOLAS("Bolas", SubTypeSet.PlaneswalkerType),
    CHANDRA("Chandra", SubTypeSet.PlaneswalkerType),
    DACK("Dack", SubTypeSet.PlaneswalkerType),
    DARETTI("Daretti", SubTypeSet.PlaneswalkerType),
    DAVRIEL("Davriel", SubTypeSet.PlaneswalkerType),
    DOMRI("Domri", SubTypeSet.PlaneswalkerType),
    DOOKU("Dooku", SubTypeSet.PlaneswalkerType, true), // Star Wars
    DOVIN("Dovin", SubTypeSet.PlaneswalkerType),
    ELSPETH("Elspeth", SubTypeSet.PlaneswalkerType),
    ESTRID("Estrid", SubTypeSet.PlaneswalkerType),
    FREYALISE("Freyalise", SubTypeSet.PlaneswalkerType),
    GARRUK("Garruk", SubTypeSet.PlaneswalkerType),
    GIDEON("Gideon", SubTypeSet.PlaneswalkerType),
    HUATLI("Huatli", SubTypeSet.PlaneswalkerType),
    JACE("Jace", SubTypeSet.PlaneswalkerType),
    KARN("Karn", SubTypeSet.PlaneswalkerType),
    KASMINA("Kasmina", SubTypeSet.PlaneswalkerType),
    KAYA("Kaya", SubTypeSet.PlaneswalkerType),
    KIORA("Kiora", SubTypeSet.PlaneswalkerType),
    KOTH("Koth", SubTypeSet.PlaneswalkerType),
    LILIANA("Liliana", SubTypeSet.PlaneswalkerType),
    NAHIRI("Nahiri", SubTypeSet.PlaneswalkerType),
    NARSET("Narset", SubTypeSet.PlaneswalkerType),
    NISSA("Nissa", SubTypeSet.PlaneswalkerType),
    NIXILIS("Nixilis", SubTypeSet.PlaneswalkerType),
    OBI_WAN("Obi-Wan", SubTypeSet.PlaneswalkerType, true), // Star Wars
    RAL("Ral", SubTypeSet.PlaneswalkerType),
    ROWAN("Rowan", SubTypeSet.PlaneswalkerType),
    SAHEELI("Saheeli", SubTypeSet.PlaneswalkerType),
    SAMUT("Samut", SubTypeSet.PlaneswalkerType),
    SARKHAN("Sarkhan", SubTypeSet.PlaneswalkerType),
    SERRA("Serra", SubTypeSet.PlaneswalkerType),
    SIDIOUS("Sidious", SubTypeSet.PlaneswalkerType, true), // Star Wars
    SORIN("Sorin", SubTypeSet.PlaneswalkerType),
    TAMIYO("Tamiyo", SubTypeSet.PlaneswalkerType),
    TEFERI("Teferi", SubTypeSet.PlaneswalkerType),
    TEYO("Teyo", SubTypeSet.PlaneswalkerType),
    TEZZERET("Tezzeret", SubTypeSet.PlaneswalkerType),
    TIBALT("Tibalt", SubTypeSet.PlaneswalkerType),
    UGIN("Ugin", SubTypeSet.PlaneswalkerType),
    URZA("Urza", SubTypeSet.PlaneswalkerType, true), // Unstable
    VENSER("Venser", SubTypeSet.PlaneswalkerType),
    VIVIEN("Vivien", SubTypeSet.PlaneswalkerType),
    VRASKA("Vraska", SubTypeSet.PlaneswalkerType),
    WILL("Will", SubTypeSet.PlaneswalkerType),
    WINDGRACE("Windgrace", SubTypeSet.PlaneswalkerType),
    WRENN("Wrenn", SubTypeSet.PlaneswalkerType),
    XENAGOS("Xenagos", SubTypeSet.PlaneswalkerType),
    YANGGU("Yanggu", SubTypeSet.PlaneswalkerType),
    YANLING("Yanling", SubTypeSet.PlaneswalkerType),
    YODA("Yoda", SubTypeSet.PlaneswalkerType, true);  // Star Wars

    private final SubTypeSet subTypeSet;

    SubType(String description, SubTypeSet subTypeSet) {
        this(description, subTypeSet, false);
    }

    SubType(String description, SubTypeSet subTypeSet, boolean customSet) {
        this.description = description;
        this.subTypeSet = subTypeSet;
        this.customSet = customSet;
    }

    public String getDescription() {
        return description;
    }

    private final String description;

    private final boolean customSet;

    @Override
    public String toString() {
        return description;
    }

    public static SubType fromString(String value) {
        for (SubType st : SubType.values()) {
            if (st.toString().equals(value)) {
                return st;
            }
        }

        throw new IllegalArgumentException("Can''t find subtype enum value: " + value);
    }

    public static SubType byDescription(String subType) {
        for (SubType s : values()) {
            if (s.getDescription().equals(subType)) {
                return s;
            }
        }
        org.apache.log4j.Logger.getLogger(SubType.class).error("no subtype for " + subType + " exists");
        return null;
    }

    public SubTypeSet getSubTypeSet() {
        return subTypeSet;
    }

    public static Set<SubType> getArtifactTypes() {
        Set<SubType> subTypes = EnumSet.noneOf(SubType.class);
        for (SubType subType : values()) {
            if (subType.getSubTypeSet() == SubTypeSet.ArtifactType) {
                subTypes.add(subType);
            }
        }
        return subTypes;
    }

    public static Set<SubType> getPlaneswalkerTypes() {
        Set<SubType> subTypes = EnumSet.noneOf(SubType.class);
        for (SubType subType : values()) {
            if (subType.getSubTypeSet() == SubTypeSet.PlaneswalkerType) {
                subTypes.add(subType);
            }
        }
        return subTypes;
    }

    public static Set<SubType> getCreatureTypes() {
        Set<SubType> subTypes = EnumSet.noneOf(SubType.class);
        for (SubType subType : values()) {
            if (subType.getSubTypeSet() == SubTypeSet.CreatureType) {
                subTypes.add(subType);
            }
        }
        return subTypes;
    }

    public static Set<SubType> getBasicLands() {
        return Arrays.stream(values())
                .filter(p -> p.getSubTypeSet() == SubTypeSet.BasicLandType)
                .collect(Collectors.toSet());
    }

    public static SubTypeList getLandTypes() {
        SubTypeList landTypes = new SubTypeList();
        for (SubType subType : values()) {
            if (subType.getSubTypeSet() == SubTypeSet.BasicLandType || subType.getSubTypeSet() == SubTypeSet.NonBasicLandType) {
                landTypes.add(subType);
            }
        }
        return landTypes;
    }
}
