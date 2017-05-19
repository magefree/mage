package mage.constants;


import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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
    ADVISOR("Advisor", SubTypeSet.CreatureType, false),
    AETHERBORN("Aetherborn", SubTypeSet.CreatureType, false),
    ALLY("Ally", SubTypeSet.CreatureType, false),
    ANGEL("Angel", SubTypeSet.CreatureType, false),
    ANTELOPE("Antelope", SubTypeSet.CreatureType, false),
    APE("Ape", SubTypeSet.CreatureType, false),
    ARCHER("Archer", SubTypeSet.CreatureType, false),
    ARCHON("Archon", SubTypeSet.CreatureType, false),
    ARTIFICER("Artificer", SubTypeSet.CreatureType, false),
    ASSASSIN("Assassin", SubTypeSet.CreatureType, false),
    ASSEMBLY_WORKER("Assembly-Worker", SubTypeSet.CreatureType, false),
    ATOG("Atog", SubTypeSet.CreatureType, false),
    AUROCHS("Aurochs", SubTypeSet.CreatureType, false),

    BADGER("Badger", SubTypeSet.CreatureType, false),
    BARBARIAN("Barbarian", SubTypeSet.CreatureType, false),
    BASILISK("Basilisk", SubTypeSet.CreatureType, false),
    BAT("Bat", SubTypeSet.CreatureType, false),
    BEAR("Bear", SubTypeSet.CreatureType, false),
    BEAST("Beast", SubTypeSet.CreatureType, false),
    BEEBLE("Beeble", SubTypeSet.CreatureType, false),
    BERSERKER("Berserker", SubTypeSet.CreatureType, false),
    BIRD("Bird", SubTypeSet.CreatureType, false),
    BLINKMOTH("Blinkmoth", SubTypeSet.CreatureType, false),
    BOARD("Boar", SubTypeSet.CreatureType, false),
    BRINGER("Bringer", SubTypeSet.CreatureType, false),
    BRUSHWAGG("Brushwagg", SubTypeSet.CreatureType, false),

    CAMARID("Camarid", SubTypeSet.CreatureType, false),
    CAMEL("Camel", SubTypeSet.CreatureType, false),
    CARIBOU("Caribou", SubTypeSet.CreatureType, false),
    CARRIER("Carrier", SubTypeSet.CreatureType, false),
    CAT("Cat", SubTypeSet.CreatureType, false),
    CENTAUR("Centaur", SubTypeSet.CreatureType, false),
    CEPHALID("Cephalid", SubTypeSet.CreatureType, false),
    CHIMERA("Chimera", SubTypeSet.CreatureType, false),
    CITIZEN("Citizen", SubTypeSet.CreatureType, false),
    CLERIC("Cleric", SubTypeSet.CreatureType, false),
    COCKATRICE("Cockatrice", SubTypeSet.CreatureType, false),
    CONSTRUCT("Construct", SubTypeSet.CreatureType, false),
    COWARD("Coward", SubTypeSet.CreatureType, false),
    CRAB("Crab", SubTypeSet.CreatureType, false),
    CROCODILE("Crocodile", SubTypeSet.CreatureType, false),
    CYCLOPS("Cyclops", SubTypeSet.CreatureType, false),

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
    DROID("Droid", SubTypeSet.CreatureType, true),
    DRYAD("Dryad", SubTypeSet.CreatureType, true),
    DWARF("Dwarf", SubTypeSet.CreatureType, false),

    EFREET("Efreet", SubTypeSet.CreatureType, false),
    ELDER("Elder", SubTypeSet.CreatureType, false),
    ELDRAZI("Eldrazi", SubTypeSet.CreatureType, false),
    ELEMENTAL("Elemental", SubTypeSet.CreatureType, false),
    ELEPHANT("Elephant", SubTypeSet.CreatureType, false),
    ELF("Elf", SubTypeSet.CreatureType, false),
    ELK("Elk", SubTypeSet.CreatureType, false),
    EYE("Eye", SubTypeSet.CreatureType, false),
    EWOK("Ewok", SubTypeSet.CreatureType, true),

    FAERIE("Faerie", SubTypeSet.CreatureType, false),
    FERRET("Ferret", SubTypeSet.CreatureType, false),
    FISH("Fish", SubTypeSet.CreatureType, false),
    FLAGBEARER("Flagbearer", SubTypeSet.CreatureType, false),
    FOX("Fox", SubTypeSet.CreatureType, false),
    FROG("Frog", SubTypeSet.CreatureType, false),
    FUNGUS("Fungus", SubTypeSet.CreatureType, false),

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
    HYDRA("Hydra", SubTypeSet.CreatureType, false),
    HYENA("Hyena", SubTypeSet.CreatureType, false),

    ILLUSION("Illusion", SubTypeSet.CreatureType, false),
    IMP("Imp", SubTypeSet.CreatureType, false),
    INCARNATION("Incarnation", SubTypeSet.CreatureType, false),
    INSECT("Insect", SubTypeSet.CreatureType, false),

    JEDI("Jedi", SubTypeSet.CreatureType, true),
    JELLYFISH("Jellyfish", SubTypeSet.CreatureType, true),
    JUGGERNAUT("Juggernaut", SubTypeSet.CreatureType, true),

    KAVU("Kavu", SubTypeSet.CreatureType, true),
    KIRIN("Kirin", SubTypeSet.CreatureType, true),
    KITHKIN("Kithkin", SubTypeSet.CreatureType, false),
    KNIGHT("Knight", SubTypeSet.CreatureType, false),
    KOBOLD("Kobold", SubTypeSet.CreatureType, false),
    KOR("Kor", SubTypeSet.CreatureType, false),
    KRAKEN("Kraken", SubTypeSet.CreatureType, false),

    LAMIA("Lamia", SubTypeSet.CreatureType, true),
    LAMMASU("Lammasu", SubTypeSet.CreatureType, true),
    LEECH("Leech", SubTypeSet.CreatureType, true),
    LEVIATHAN("Leviathan", SubTypeSet.CreatureType, false),
    LHURGOYF("Lhurgoyf", SubTypeSet.CreatureType, false),
    LICID("Licid", SubTypeSet.CreatureType, false),
    LIZARD("Lizard", SubTypeSet.CreatureType, false),

    MANTICORE("Manticore", SubTypeSet.CreatureType, false),
    MASTICORE("Masticore", SubTypeSet.CreatureType, false),
    MERCENARY("Mercenary", SubTypeSet.CreatureType, false),
    MERFOLK("Merfolk", SubTypeSet.CreatureType, false),
    METATHRAN("Metathran", SubTypeSet.CreatureType, false),
    MINION("Minion", SubTypeSet.CreatureType, false),
    MINOTAUR("Minotaur", SubTypeSet.CreatureType, false),
    MOLE("Mole", SubTypeSet.CreatureType, false),
    MONGER("Monger", SubTypeSet.CreatureType, false),
    MONGOOSE("Mongoose", SubTypeSet.CreatureType, false),
    MONK("Monk", SubTypeSet.CreatureType, false),
    MONKEY("Monkey", SubTypeSet.CreatureType, false),
    MOONFOLK("Moonfolk", SubTypeSet.CreatureType, false),
    MUTANT("Mutant", SubTypeSet.CreatureType, false),
    MYR("Myr", SubTypeSet.CreatureType, false),
    MYSTIC("Mystic", SubTypeSet.CreatureType, false),


    NAGA("Naga", SubTypeSet.CreatureType, false),
    NAUTILUS("Nautilus", SubTypeSet.CreatureType, false),
    NEPHILIM("Nephilim", SubTypeSet.CreatureType, false),
    NIGHTMARE("Nightmare", SubTypeSet.CreatureType, false),
    NIGHTSTALKER("Nightstalker", SubTypeSet.CreatureType, false),
    NINJA("Ninja", SubTypeSet.CreatureType, false),
    NOGGLE("Noggle", SubTypeSet.CreatureType, false),
    NOMAD("Nomad", SubTypeSet.CreatureType, false),
    NYMPH("Nymph", SubTypeSet.CreatureType, false),

    OCTOPUS("Octopus", SubTypeSet.CreatureType, false),
    OGRE("Ogre", SubTypeSet.CreatureType, false),
    OOZE("Ooze", SubTypeSet.CreatureType, false),
    ORB("Orb", SubTypeSet.CreatureType, false),
    ORC("Orc", SubTypeSet.CreatureType, false),
    ORGG("Orgg", SubTypeSet.CreatureType, false),
    OUPHE("Ouphe", SubTypeSet.CreatureType, false),
    OX("Ox", SubTypeSet.CreatureType, false),
    OYSTER("Oyster", SubTypeSet.CreatureType, false),

    PEGASUS("Pegasus", SubTypeSet.CreatureType, false),
    PENTAVITE("Pentavite", SubTypeSet.CreatureType, false),
    PEST("Pest", SubTypeSet.CreatureType, false),
    PHELDAGRIFF("Pheldagriff", SubTypeSet.CreatureType, false),
    PHOENIX("Phoenix", SubTypeSet.CreatureType, false),
    PILOT("Pilot", SubTypeSet.CreatureType, false),
    PINCHER("Pincher", SubTypeSet.CreatureType, false),
    PIRATE("Pirate", SubTypeSet.CreatureType, false),
    PLANT("Plant", SubTypeSet.CreatureType, false),
    PRAETOR("Praetor", SubTypeSet.CreatureType, false),
    PRISM("Prism", SubTypeSet.CreatureType, false),
    PROCESSOR("Processor", SubTypeSet.CreatureType, false),

    RABBIT("Rabbit", SubTypeSet.CreatureType, false),
    RAT("Rat", SubTypeSet.CreatureType, false),
    REBEL("Rebel", SubTypeSet.CreatureType, false),
    REFLECTION("Reflection", SubTypeSet.CreatureType, false),
    RHINO("Rhino", SubTypeSet.CreatureType, false),
    RIGGER("Rigger", SubTypeSet.CreatureType, false),
    ROGUE("Rogue", SubTypeSet.CreatureType, false),


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
    SPHINX("Sphix", SubTypeSet.CreatureType, false),
    SPIDER("Spider", SubTypeSet.CreatureType, false),
    SPIKE("Spike", SubTypeSet.CreatureType, false),
    SPIRIT("Spirit", SubTypeSet.CreatureType, false),
    SPLITTER("Splitter", SubTypeSet.CreatureType, false),
    SPONGE("Sponge", SubTypeSet.CreatureType, false),
    SQUID("Squid", SubTypeSet.CreatureType, false),
    SQUIRREL("Squirrel", SubTypeSet.CreatureType, false),
    STARFISH("Starfish", SubTypeSet.CreatureType, false),
    STARSHIP("Starship", SubTypeSet.CreatureType, true),
    SURRAKAR("Surrakar", SubTypeSet.CreatureType, false),
    SURVIVOR("Survivor", SubTypeSet.CreatureType, false),

    TETRAVITE("Tetravite", SubTypeSet.CreatureType, false),
    THALAKOS("Thalakos", SubTypeSet.CreatureType, false),
    THOPTER("Thopter", SubTypeSet.CreatureType, false),
    THRULL("Thrull", SubTypeSet.CreatureType, false),
    TREEFOLK("Treefolk", SubTypeSet.CreatureType, false),
    TRISKELAVITE("Triskelavite", SubTypeSet.CreatureType, false),
    TROLL("Troll", SubTypeSet.CreatureType, false),
    TURTLE("Turtle", SubTypeSet.CreatureType, false),

    TROOPER("Trooper", SubTypeSet.CreatureType, true),

    UNICORN("Unicorn", SubTypeSet.CreatureType, false),

    VAMPIRE("Vampire", SubTypeSet.CreatureType, false),
    VEDALKEN("Vedalken", SubTypeSet.CreatureType, false),
    VIASHINO("Viashino", SubTypeSet.CreatureType, false),
    VOLVER("Volver", SubTypeSet.CreatureType, false),
    WALL("Wall", SubTypeSet.CreatureType, false),
    WARRIOR("Warrior", SubTypeSet.CreatureType, false),
    WEIRD("Weird", SubTypeSet.CreatureType, false),
    WEREWOLF("Werewolf", SubTypeSet.CreatureType, false),
    WHALE("Whale", SubTypeSet.CreatureType, false),
    WIZARD("Wizard", SubTypeSet.CreatureType, false),
    WOLF("Wolf", SubTypeSet.CreatureType, false),
    WOLVERINE("Wolverine", SubTypeSet.CreatureType, false),
    WOMBAT("Wombat", SubTypeSet.CreatureType, false),
    WRAITH("Wraith", SubTypeSet.CreatureType, false),
    WURM("Wurm", SubTypeSet.CreatureType, false),

    YETI("Yeti", SubTypeSet.CreatureType, false),

    ZOMBIE("Zombie", SubTypeSet.CreatureType, false),
    ZUBERA("Zubera", SubTypeSet.CreatureType, false),

    AJANI("Ajani", SubTypeSet.PlaneswalkerType, false),
    CHANDRA("Chandra", SubTypeSet.PlaneswalkerType, false),
    GIDEON("Gideon", SubTypeSet.PlaneswalkerType, false),
    JACE("Jace", SubTypeSet.PlaneswalkerType, false),
    KARN("Karn", SubTypeSet.PlaneswalkerType, false),
    LILIANA("Liliana", SubTypeSet.PlaneswalkerType, false),
    NISSA("Nissa", SubTypeSet.PlaneswalkerType, false),
    TAMIYO("Tamiyo", SubTypeSet.PlaneswalkerType, false),
    TEZZERET("Tezzeret", SubTypeSet.PlaneswalkerType, false),
    UGIN("Ugin", SubTypeSet.PlaneswalkerType, false);

    private final SubTypeSet subTypeSet;

    public String getDescription() {
        return description;
    }

    private final String description;

    private final boolean customSet;

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

    public static Set<String> getCreatureTypes(boolean customSet) {

        return Arrays.stream(values()).filter(s -> s.customSet == customSet).filter(p -> p.getSubTypeSet() == SubTypeSet.CreatureType).map(SubType::getDescription).collect(Collectors.toSet());
    }

    public static Set<String> getBasicLands(boolean customSet) {
        return Arrays.stream(values()).filter(s -> s.customSet == customSet).filter(p -> p.getSubTypeSet() == SubTypeSet.BasicLandType).map(SubType::getDescription).collect(Collectors.toSet());
    }
}

