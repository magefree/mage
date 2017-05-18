package mage.constants;


import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum SubType {
    ARCANE("Arcane", SubTypeSet.SpellType, false),
    TRAP("Trap", SubTypeSet.SpellType, false),

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

    AURA("Aura", SubTypeSet.EnchantmentType, false),
    CARTOUCHE("Cartouche", SubTypeSet.EnchantmentType, false),
    CURSE("Curse", SubTypeSet.EnchantmentType, false),
    SHRINE("Shrine", SubTypeSet.EnchantmentType, false),

    CLUE("Clue", SubTypeSet.ArtifactType, false),
    EQUIPMENT("Equipment", SubTypeSet.ArtifactType, false),
    FORTIFICATION("Fortification", SubTypeSet.ArtifactType, false),
    VEHICLE("Vehicle", SubTypeSet.ArtifactType, false),

    ALLY("Ally", SubTypeSet.CreatureType, false),
    ANGEL("Angel", SubTypeSet.CreatureType, false),
    ARTIFICER("Artificer", SubTypeSet.CreatureType, false),
    ASSEMBLY_WORKER("Assembly-Worker", SubTypeSet.CreatureType, false),
    ASSASSIN("Assassin", SubTypeSet.CreatureType, false),
    ATOG("Atog", SubTypeSet.CreatureType, false),
    AUROCHS("Aurochs", SubTypeSet.CreatureType, false),
    BARBARIAN("Barbarian", SubTypeSet.CreatureType, false),
    BAT("Bat", SubTypeSet.CreatureType, false),
    BEAST("Beast", SubTypeSet.CreatureType, false),
    BERSERKER("Berserker", SubTypeSet.CreatureType, false),
    BIRD("Bird", SubTypeSet.CreatureType, false),
    BLINKMOTH("Blinkmoth", SubTypeSet.CreatureType, false),
    CARIBOU("Caribou", SubTypeSet.CreatureType, false),
    CAT("Cat", SubTypeSet.CreatureType, false),
    CEPHALID("Cephalid", SubTypeSet.CreatureType, false),
    CENTAUR("Centaur" ,SubTypeSet.CreatureType, false),
    CLERIC("Cleric", SubTypeSet.CreatureType, false),
    DEMON("Demon", SubTypeSet.CreatureType, false),
    DRAGON("Dragon", SubTypeSet.CreatureType, false),
    DRUID("Druid", SubTypeSet.CreatureType, false),
    DROID("Droid", SubTypeSet.CreatureType, true),
    DWARF("Dwarf", SubTypeSet.CreatureType, false),
    ELDRAZI("Eldrazi", SubTypeSet.CreatureType, false),
    ELEMENTAL("Elemental", SubTypeSet.CreatureType, false),
    ELEPHANT("Elephant", SubTypeSet.CreatureType, false),
    ELF("Elf", SubTypeSet.CreatureType, false),
    FAERIE("Faerie", SubTypeSet.CreatureType, false),
    FLAGBEARER("Flagbearer", SubTypeSet.CreatureType, false),
    FOX("Fox", SubTypeSet.CreatureType, false),
    FUNGUS("Fungus", SubTypeSet.CreatureType, false),
    GIANT("Giant", SubTypeSet.CreatureType, false),
    GOAT("Goat", SubTypeSet.CreatureType, false),
    GOBLIN("Goblin", SubTypeSet.CreatureType, false),
    GORGON("Gorgon", SubTypeSet.CreatureType, false),
    GRIFFIN("Griffin", SubTypeSet.CreatureType, false),
    HOMUNCULUS("Homunculus", SubTypeSet.CreatureType, false),
    HORROR("Horror", SubTypeSet.CreatureType, false),
    HUMAN("Human", SubTypeSet.CreatureType, false),
    HUNTER("Hunter", SubTypeSet.CreatureType, false),
    INSECT("Insect", SubTypeSet.CreatureType, false),
    JEDI("Jedi", SubTypeSet.CreatureType, true),
    KITHKIN("Kithkin", SubTypeSet.CreatureType, false),
    KNIGHT("Knight", SubTypeSet.CreatureType, false),
    KOBOLD("Kobold", SubTypeSet.CreatureType, false),
    KOR("Kor", SubTypeSet.CreatureType, false),
    GOLEM("Golem", SubTypeSet.CreatureType, false),
    KAVU("Kavu", SubTypeSet.CreatureType, false),
    MERCENARY("Mercenary", SubTypeSet.CreatureType, false),
    MERFOLK("Merfolk", SubTypeSet.CreatureType, false),
    MINION("Minion", SubTypeSet.CreatureType, false),
    MINOTAUR("Minotaur", SubTypeSet.CreatureType, false),
    MOONFOLK("Moonfolk", SubTypeSet.CreatureType, false),
    MYR("Myr", SubTypeSet.CreatureType, false),
    NINJA("Ninja", SubTypeSet.CreatureType, false),
    OGRE("Ogre", SubTypeSet.CreatureType, false),
    ORC("Orc", SubTypeSet.CreatureType, false),
    PENTAVITE("Pentavite", SubTypeSet.CreatureType, false),
    PLANT("Plant", SubTypeSet.CreatureType, false),
    PRISM("Prism", SubTypeSet.CreatureType, false),
    RAT("Rat", SubTypeSet.CreatureType, false),
    REBEL("Rebel", SubTypeSet.CreatureType, false),
    ROGUE("Rogue", SubTypeSet.CreatureType, false),
    SAPROLING("Saproling", SubTypeSet.CreatureType, false),
    SAMURAI("Samurai", SubTypeSet.CreatureType, false),
    SCARECROW("Scarecrow", SubTypeSet.CreatureType, false),
    SCION("Scion", SubTypeSet.CreatureType, false),
    SERF("Serf", SubTypeSet.CreatureType, false),
    SERVO("Servo", SubTypeSet.CreatureType, false),
    SHAMAN("Shaman", SubTypeSet.CreatureType, false),
    SKELETON("Skeleton", SubTypeSet.CreatureType, false),
    SLIVER("Sliver", SubTypeSet.CreatureType, false),
    SNAKE("Snake", SubTypeSet.CreatureType, false),
    SOLDIER("Soldier", SubTypeSet.CreatureType, false),
    SPIDER("Spider", SubTypeSet.CreatureType, false),
    SPIRIT("Spirit", SubTypeSet.CreatureType, false),
    SQUIRREL("Squirrel", SubTypeSet.CreatureType, false),
    STARSHIP("Starship", SubTypeSet.CreatureType, true),
    THOPTER("Thopter", SubTypeSet.CreatureType, false),
    THRULL("Thrull", SubTypeSet.CreatureType, false),
    TREEFOLK("Treefolk", SubTypeSet.CreatureType, false),
    TROOPER("Trooper", SubTypeSet.CreatureType, true),
    VAMPIRE("Vampire", SubTypeSet.CreatureType, false),
    WALL("Wall", SubTypeSet.CreatureType, false),
    WARRIOR("Warrior", SubTypeSet.CreatureType, false),
    WEREWOLF("Werewolf", SubTypeSet.CreatureType, false),
    WIZARD("Wizard", SubTypeSet.CreatureType, false),
    WOLF("Wolf", SubTypeSet.CreatureType, false),
    ZOMBIE("Zombie", SubTypeSet.CreatureType, false),

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
        return Arrays.stream(values()).filter(s->s.customSet == customSet).filter(p->p.getSubTypeSet() == SubTypeSet.BasicLandType).map(SubType::getDescription).collect(Collectors.toSet());
    }
}

