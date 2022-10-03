package mage.constants;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.*;
import java.util.stream.Collectors;

public enum SubType {

    //205.3k Instants and sorceries share their lists of subtypes; these subtypes are called spell types.
    ADVENTURE("Adventure", SubTypeSet.SpellType),
    ARCANE("Arcane", SubTypeSet.SpellType),
    LESSON("Lesson", SubTypeSet.SpellType),
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
    BACKGROUND("Background", SubTypeSet.EnchantmentType),
    CARTOUCHE("Cartouche", SubTypeSet.EnchantmentType),
    CLASS("Class", SubTypeSet.EnchantmentType),
    CURSE("Curse", SubTypeSet.EnchantmentType),
    RUNE("Rune", SubTypeSet.EnchantmentType),
    SAGA("Saga", SubTypeSet.EnchantmentType),
    SHARD("Shard", SubTypeSet.EnchantmentType),
    SHRINE("Shrine", SubTypeSet.EnchantmentType),
    // 205.3g: Artifacts have their own unique set of subtypes; these subtypes are called artifact types.
    BLOOD("Blood", SubTypeSet.ArtifactType),
    CLUE("Clue", SubTypeSet.ArtifactType),
    CONTRAPTION("Contraption", SubTypeSet.ArtifactType),
    EQUIPMENT("Equipment", SubTypeSet.ArtifactType),
    FOOD("Food", SubTypeSet.ArtifactType),
    FORTIFICATION("Fortification", SubTypeSet.ArtifactType),
    GOLD("Gold", SubTypeSet.ArtifactType),
    POWERSTONE("Powerstone", SubTypeSet.ArtifactType),
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
    ASTARTES("Astartes", SubTypeSet.CreatureType),
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
    BARD("Bard", SubTypeSet.CreatureType),
    BASILISK("Basilisk", SubTypeSet.CreatureType),
    BAT("Bat", SubTypeSet.CreatureType),
    BEAR("Bear", SubTypeSet.CreatureType),
    BEAST("Beast", SubTypeSet.CreatureType),
    BEEBLE("Beeble", SubTypeSet.CreatureType),
    BEHOLDER("Beholder", SubTypeSet.CreatureType),
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
    CHIMERA("Chimera", SubTypeSet.CreatureType),
    CHISS("Chiss", SubTypeSet.CreatureType, true),
    CITIZEN("Citizen", SubTypeSet.CreatureType),
    CLAMFOLK("Clamfolk", SubTypeSet.CreatureType, true), // Unglued
    CLERIC("Cleric", SubTypeSet.CreatureType),
    CLOWN("Clown", SubTypeSet.CreatureType),
    COCKATRICE("Cockatrice", SubTypeSet.CreatureType),
    CONSTRUCT("Construct", SubTypeSet.CreatureType),
    COW("Cow", SubTypeSet.CreatureType, true), // Unglued
    COWARD("Coward", SubTypeSet.CreatureType),
    CRAB("Crab", SubTypeSet.CreatureType),
    CROCODILE("Crocodile", SubTypeSet.CreatureType),
    CROLUTE("Crolute", SubTypeSet.CreatureType, true), // Star Wars
    CUSTODES("Custodes", SubTypeSet.CreatureType),
    CYBORG("Cyborg", SubTypeSet.CreatureType, true), // Star Wars
    CYCLOPS("Cyclops", SubTypeSet.CreatureType),
    // D
    DATHOMIRIAN("Dathomirian", SubTypeSet.CreatureType, true), // Star Wars
    DAUTHI("Dauthi", SubTypeSet.CreatureType),
    DEMIGOD("Demigod", SubTypeSet.CreatureType),
    DEMON("Demon", SubTypeSet.CreatureType),
    DESERTER("Deserter", SubTypeSet.CreatureType),
    DEVIL("Devil", SubTypeSet.CreatureType),
    DINOSAUR("Dinosaur", SubTypeSet.CreatureType), // With Ixalan now being spoiled, need this to be selectable
    DJINN("Djinn", SubTypeSet.CreatureType),
    DOG("Dog", SubTypeSet.CreatureType),
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
    EMPLOYEE("Employee", SubTypeSet.CreatureType),
    EYE("Eye", SubTypeSet.CreatureType),
    EWOK("Ewok", SubTypeSet.CreatureType, true), // Star Wars
    EXPANSION_SYMBOL("Expansion-Symbol", SubTypeSet.CreatureType, true), // Unhinged
    // F
    FAERIE("Faerie", SubTypeSet.CreatureType),
    FERRET("Ferret", SubTypeSet.CreatureType),
    FISH("Fish", SubTypeSet.CreatureType),
    FLAGBEARER("Flagbearer", SubTypeSet.CreatureType),
    FOX("Fox", SubTypeSet.CreatureType),
    FRACTAL("Fractal", SubTypeSet.CreatureType),
    FROG("Frog", SubTypeSet.CreatureType),
    FUNGUS("Fungus", SubTypeSet.CreatureType),
    // G
    GAMER("Gamer", SubTypeSet.CreatureType, true), // Un-sets
    GAMORREAN("Gamorrean", SubTypeSet.CreatureType, true), // Star Wars
    GAND("Gand", SubTypeSet.CreatureType, true), // Star Wars
    GARGOYLE("Gargoyle", SubTypeSet.CreatureType),
    GERM("Germ", SubTypeSet.CreatureType),
    GIANT("Giant", SubTypeSet.CreatureType),
    GITH("Gith", SubTypeSet.CreatureType),
    GNOME("Gnome", SubTypeSet.CreatureType),
    GNOLL("Gnoll", SubTypeSet.CreatureType),
    GOLEM("Golem", SubTypeSet.CreatureType),
    GOAT("Goat", SubTypeSet.CreatureType),
    GOBLIN("Goblin", SubTypeSet.CreatureType),
    GOD("God", SubTypeSet.CreatureType),
    GORGON("Gorgon", SubTypeSet.CreatureType),
    GRAVEBORN("Graveborn", SubTypeSet.CreatureType),
    GREMLIN("Gremlin", SubTypeSet.CreatureType),
    GRIFFIN("Griffin", SubTypeSet.CreatureType),
    GUEST("Guest", SubTypeSet.CreatureType),
    GUNGAN("Gungan", SubTypeSet.CreatureType, true), // Star Wars
    // H
    HAG("Hag", SubTypeSet.CreatureType),
    HALFLING("Halfling", SubTypeSet.CreatureType),
    HAMSTER("Hamster", SubTypeSet.CreatureType),
    HARPY("Harpy", SubTypeSet.CreatureType),
    HELLION("Hellion", SubTypeSet.CreatureType),
    HIPPO("Hippo", SubTypeSet.CreatureType),
    HIPPOGRIFF("Hippogriff", SubTypeSet.CreatureType),
    HOMARID("Homarid", SubTypeSet.CreatureType),
    HOMUNCULUS("Homunculus", SubTypeSet.CreatureType),
    HORROR("Horror", SubTypeSet.CreatureType),
    HORSE("Horse", SubTypeSet.CreatureType),
    HUMAN("Human", SubTypeSet.CreatureType),
    HUNTER("Hunter", SubTypeSet.CreatureType),
    HUTT("Hutt", SubTypeSet.CreatureType, true), // Star Wars
    HYDRA("Hydra", SubTypeSet.CreatureType),
    HYENA("Hyena", SubTypeSet.CreatureType),
    // I
    ILLUSION("Illusion", SubTypeSet.CreatureType),
    IMP("Imp", SubTypeSet.CreatureType),
    INCARNATION("Incarnation", SubTypeSet.CreatureType),
    INKLING("Inkling", SubTypeSet.CreatureType),
    INQUISITOR("Inquisitor", SubTypeSet.CreatureType),
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
    MOUSE("Mouse", SubTypeSet.CreatureType),
    MUTANT("Mutant", SubTypeSet.CreatureType),
    MYR("Myr", SubTypeSet.CreatureType),
    MYSTIC("Mystic", SubTypeSet.CreatureType),
    // N
    NAGA("Naga", SubTypeSet.CreatureType),
    NAUTILUS("Nautilus", SubTypeSet.CreatureType),
    NAUTOLAN("Nautolan", SubTypeSet.CreatureType, true), // Star Wars
    NECRON("Necron", SubTypeSet.CreatureType),
    NEIMOIDIAN("Neimoidian", SubTypeSet.CreatureType, true), // Star Wars
    NEPHILIM("Nephilim", SubTypeSet.CreatureType),
    NIGHTMARE("Nightmare", SubTypeSet.CreatureType),
    NIGHTSTALKER("Nightstalker", SubTypeSet.CreatureType),
    NINJA("Ninja", SubTypeSet.CreatureType),
    NOBLE("Noble", SubTypeSet.CreatureType),
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
    OTTER("Otter", SubTypeSet.CreatureType),
    OUPHE("Ouphe", SubTypeSet.CreatureType),
    OX("Ox", SubTypeSet.CreatureType),
    OYSTER("Oyster", SubTypeSet.CreatureType),
    // P
    PANGOLIN("Pangolin", SubTypeSet.CreatureType),
    PEASANT("Peasant", SubTypeSet.CreatureType),
    PEGASUS("Pegasus", SubTypeSet.CreatureType),
    PENTAVITE("Pentavite", SubTypeSet.CreatureType),
    PEST("Pest", SubTypeSet.CreatureType),
    PHELDDAGRIF("Phelddagrif", SubTypeSet.CreatureType),
    PHOENIX("Phoenix", SubTypeSet.CreatureType),
    PHYREXIAN("Phyrexian", SubTypeSet.CreatureType),
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
    RACCOON("Raccoon", SubTypeSet.CreatureType),
    RAIDER("Raider", SubTypeSet.CreatureType, true), // Star Wars
    RANGER("Ranger", SubTypeSet.CreatureType),
    RAT("Rat", SubTypeSet.CreatureType),
    REBEL("Rebel", SubTypeSet.CreatureType),
    REFLECTION("Reflection", SubTypeSet.CreatureType),
    RHINO("Rhino", SubTypeSet.CreatureType),
    RIGGER("Rigger", SubTypeSet.CreatureType),
    ROBOT("Robot", SubTypeSet.CreatureType),
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
    SHARK("Shark", SubTypeSet.CreatureType),
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
    TENTACLE("Tentacle", SubTypeSet.CreatureType),
    TETRAVITE("Tetravite", SubTypeSet.CreatureType),
    THALAKOS("Thalakos", SubTypeSet.CreatureType),
    THOPTER("Thopter", SubTypeSet.CreatureType),
    TIEFLING("Tiefling", SubTypeSet.CreatureType),
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
    TYRANID("Tyranid", SubTypeSet.CreatureType),
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
    WALRUS("Walrus", SubTypeSet.CreatureType),
    WARLOCK("Warlock", SubTypeSet.CreatureType),
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
    BAHAMUT("Bahamut", SubTypeSet.PlaneswalkerType),
    BASRI("Basri", SubTypeSet.PlaneswalkerType),
    BOLAS("Bolas", SubTypeSet.PlaneswalkerType),
    CALIX("Calix", SubTypeSet.PlaneswalkerType),
    CHANDRA("Chandra", SubTypeSet.PlaneswalkerType),
    DACK("Dack", SubTypeSet.PlaneswalkerType),
    DAKKON("Dakkon", SubTypeSet.PlaneswalkerType),
    DARETTI("Daretti", SubTypeSet.PlaneswalkerType),
    DAVRIEL("Davriel", SubTypeSet.PlaneswalkerType),
    DIHADA("Dihada", SubTypeSet.PlaneswalkerType),
    DOMRI("Domri", SubTypeSet.PlaneswalkerType),
    DOOKU("Dooku", SubTypeSet.PlaneswalkerType, true), // Star Wars
    DOVIN("Dovin", SubTypeSet.PlaneswalkerType),
    ELLYWICK("Ellywick", SubTypeSet.PlaneswalkerType),
    ELMINSTER("Elminster", SubTypeSet.PlaneswalkerType),
    ELSPETH("Elspeth", SubTypeSet.PlaneswalkerType),
    ESTRID("Estrid", SubTypeSet.PlaneswalkerType),
    FREYALISE("Freyalise", SubTypeSet.PlaneswalkerType),
    GARRUK("Garruk", SubTypeSet.PlaneswalkerType),
    GIDEON("Gideon", SubTypeSet.PlaneswalkerType),
    GRIST("Grist", SubTypeSet.PlaneswalkerType),
    HUATLI("Huatli", SubTypeSet.PlaneswalkerType),
    JACE("Jace", SubTypeSet.PlaneswalkerType),
    JARED("Jared", SubTypeSet.PlaneswalkerType),
    JESKA("Jeska", SubTypeSet.PlaneswalkerType),
    KAITO("Kaito", SubTypeSet.PlaneswalkerType),
    KARN("Karn", SubTypeSet.PlaneswalkerType),
    KASMINA("Kasmina", SubTypeSet.PlaneswalkerType),
    KAYA("Kaya", SubTypeSet.PlaneswalkerType),
    KIORA("Kiora", SubTypeSet.PlaneswalkerType),
    KOTH("Koth", SubTypeSet.PlaneswalkerType),
    LILIANA("Liliana", SubTypeSet.PlaneswalkerType),
    LUKKA("Lukka", SubTypeSet.PlaneswalkerType),
    LOLTH("Lolth", SubTypeSet.PlaneswalkerType),
    MINSC("Minsc", SubTypeSet.PlaneswalkerType),
    MORDENKAINEN("Mordenkainen", SubTypeSet.PlaneswalkerType),
    NAHIRI("Nahiri", SubTypeSet.PlaneswalkerType),
    NARSET("Narset", SubTypeSet.PlaneswalkerType),
    NIKO("Niko", SubTypeSet.PlaneswalkerType),
    NISSA("Nissa", SubTypeSet.PlaneswalkerType),
    NIXILIS("Nixilis", SubTypeSet.PlaneswalkerType),
    OBI_WAN("Obi-Wan", SubTypeSet.PlaneswalkerType, true), // Star Wars
    OKO("Oko", SubTypeSet.PlaneswalkerType),
    RAL("Ral", SubTypeSet.PlaneswalkerType),
    ROWAN("Rowan", SubTypeSet.PlaneswalkerType),
    SAHEELI("Saheeli", SubTypeSet.PlaneswalkerType),
    SAMUT("Samut", SubTypeSet.PlaneswalkerType),
    SARKHAN("Sarkhan", SubTypeSet.PlaneswalkerType),
    SERRA("Serra", SubTypeSet.PlaneswalkerType),
    SIDIOUS("Sidious", SubTypeSet.PlaneswalkerType, true), // Star Wars
    SIVITRI("Sivitri", SubTypeSet.PlaneswalkerType),
    SORIN("Sorin", SubTypeSet.PlaneswalkerType),
    SZAT("Szat", SubTypeSet.PlaneswalkerType),
    TAMIYO("Tamiyo", SubTypeSet.PlaneswalkerType),
    TASHA("Tasha", SubTypeSet.PlaneswalkerType),
    TEFERI("Teferi", SubTypeSet.PlaneswalkerType),
    TEYO("Teyo", SubTypeSet.PlaneswalkerType),
    TEZZERET("Tezzeret", SubTypeSet.PlaneswalkerType),
    TIBALT("Tibalt", SubTypeSet.PlaneswalkerType),
    TYVAR("Tyvar", SubTypeSet.PlaneswalkerType),
    UGIN("Ugin", SubTypeSet.PlaneswalkerType),
    URZA("Urza", SubTypeSet.PlaneswalkerType),
    VENSER("Venser", SubTypeSet.PlaneswalkerType),
    VIVIEN("Vivien", SubTypeSet.PlaneswalkerType),
    VRASKA("Vraska", SubTypeSet.PlaneswalkerType),
    WILL("Will", SubTypeSet.PlaneswalkerType),
    WINDGRACE("Windgrace", SubTypeSet.PlaneswalkerType),
    WRENN("Wrenn", SubTypeSet.PlaneswalkerType),
    XENAGOS("Xenagos", SubTypeSet.PlaneswalkerType),
    YANGGU("Yanggu", SubTypeSet.PlaneswalkerType),
    YANLING("Yanling", SubTypeSet.PlaneswalkerType),
    YODA("Yoda", SubTypeSet.PlaneswalkerType, true),  // Star Wars,
    ZARIEL("Zariel", SubTypeSet.PlaneswalkerType);

    public static class SubTypePredicate implements Predicate<MageObject> {

        private final SubType subtype;

        private SubTypePredicate(SubType subtype) {
            this.subtype = subtype;
        }


        @Override
        public boolean apply(MageObject input, Game game) {
            return input.hasSubtype(subtype, game);
        }

        @Override
        public String toString() {
            return "Subtype(" + subtype + ')';
        }
    }

    private static final Set<SubType> landTypes = new HashSet<>();
    private static final Map<SubTypeSet, Set<SubType>> subTypeSetMap = new HashMap<>();

    static {
        for (SubTypeSet subTypeSet : SubTypeSet.values()) {
            subTypeSetMap.put(
                    subTypeSet,
                    Arrays.stream(values())
                            .filter(subType -> subType.getSubTypeSet() == subTypeSet)
                            .collect(Collectors.toSet())
            );
        }
        landTypes.addAll(subTypeSetMap.get(SubTypeSet.BasicLandType));
        landTypes.addAll(subTypeSetMap.get(SubTypeSet.NonBasicLandType));
    }

    private final SubTypeSet subTypeSet;
    private final String description;
    private final boolean customSet;
    private final SubTypePredicate predicate;

    SubType(String description, SubTypeSet subTypeSet) {
        this(description, subTypeSet, false);
    }

    SubType(String description, SubTypeSet subTypeSet, boolean customSet) {
        this.description = description;
        this.subTypeSet = subTypeSet;
        this.customSet = customSet;
        this.predicate = new SubTypePredicate(this);
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

    public SubTypePredicate getPredicate() {
        return predicate;
    }


    public String getIndefiniteArticle() {
        if (isVowel(description.charAt(0))) {
            return "an";
        } else {
            return "a";
        }
    }

    private boolean isVowel(char c) {
        return "AEIOUaeiou".indexOf(c) != -1;
    }

    public boolean isCustomSet() {
        return customSet;
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

    public boolean canGain(MageObject mageObject) {
        return canGain(null, mageObject);
    }

    public boolean canGain(Game game, MageObject mageObject) {
        switch (subTypeSet) {
            case CreatureType:
                return mageObject.isCreature(game) || mageObject.isTribal(game);
            case BasicLandType:
            case NonBasicLandType:
                return mageObject.isLand(game);
            case EnchantmentType:
                return mageObject.isEnchantment(game);
            case ArtifactType:
                return mageObject.isArtifact(game);
            case PlaneswalkerType:
                return mageObject.isPlaneswalker(game);
            case SpellType:
                return mageObject.isInstantOrSorcery(game);
        }
        return false;
    }

    public static Set<SubType> getArtifactTypes() {
        return subTypeSetMap.get(SubTypeSet.ArtifactType);
    }

    public static Set<SubType> getEnchantmentTypes() {
        return subTypeSetMap.get(SubTypeSet.EnchantmentType);
    }

    public static Set<SubType> getPlaneswalkerTypes() {
        return subTypeSetMap.get(SubTypeSet.PlaneswalkerType);

    }

    public static Set<SubType> getCreatureTypes() {
        return subTypeSetMap.get(SubTypeSet.CreatureType);

    }

    public static Set<SubType> getBasicLands() {
        return subTypeSetMap.get(SubTypeSet.BasicLandType);
    }

    public static Set<SubType> getLandTypes() {
        return landTypes;
    }

    public static Set<SubType> getBySubTypeSet(SubTypeSet subTypeSet) {
        return subTypeSetMap.get(subTypeSet);
    }
}
