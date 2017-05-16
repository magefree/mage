package mage.constants;


import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum SubType {
    ARCANE("Arcane", SubTypeSet.SpellType),
    TRAP("Trap", SubTypeSet.SpellType),

    FOREST("Forest", SubTypeSet.BasicLandType),
    ISLAND("Island", SubTypeSet.BasicLandType),
    MOUNTAIN("Mountain", SubTypeSet.BasicLandType),
    PLAINS("Plains", SubTypeSet.BasicLandType),
    SWAMP("Swamp", SubTypeSet.BasicLandType),
    DESERT("Desert", SubTypeSet.NonBasicLandType),
    GATE("Gate", SubTypeSet.NonBasicLandType),
    LOCUS("Locus", SubTypeSet.NonBasicLandType),
    URZAS("Urza's", SubTypeSet.NonBasicLandType),
    MINE("Mine", SubTypeSet.NonBasicLandType),
    POWER_PLANT("Power-Plant", SubTypeSet.NonBasicLandType),
    TOWER("Tower", SubTypeSet.NonBasicLandType),

    AURA("Aura", SubTypeSet.EchanemtType),
    CARTOUCHE("Cartouche", SubTypeSet.EchanemtType),
    SHRINE("Shrine", SubTypeSet.EchanemtType),

    EQUIPMENT("Equipment", SubTypeSet.ArtifactType),
    FORTIFICATION("Fortification", SubTypeSet.ArtifactType),
    VEHICLE("Vehicle", SubTypeSet.ArtifactType),

    ALLY("Ally", SubTypeSet.CreatureType),
    ARTIFICER("Artificer", SubTypeSet.CreatureType),
    ASSASSIN("Assassin", SubTypeSet.CreatureType),
    BARBARION("Barbarian", SubTypeSet.CreatureType),
    BEAST("Beast", SubTypeSet.CreatureType),
    BERSERKER("Berserker", SubTypeSet.CreatureType),
    BIRD("Bird", SubTypeSet.CreatureType),
    CAT("Cat", SubTypeSet.CreatureType),
    CEPHALID("Cephalid", SubTypeSet.CreatureType),
    CLERIC("Cleric", SubTypeSet.CreatureType),
    DEMON("Demon", SubTypeSet.CreatureType),
    DRAGON("Dragon", SubTypeSet.CreatureType),
    DROID("Droid", SubTypeSet.CreatureType),
    DWARF("Dwarf", SubTypeSet.CreatureType),
    ELDRAZI("Eldrazi", SubTypeSet.CreatureType),
    ELEMENTAL("Elemental", SubTypeSet.CreatureType),
    ELF("Elf", SubTypeSet.CreatureType),
    FAERIE("Faerie", SubTypeSet.CreatureType),
    FOX("Fox", SubTypeSet.CreatureType),
    FUNGUS("Fungus", SubTypeSet.CreatureType),
    GIANT("Giant", SubTypeSet.CreatureType),
    GOAT("Goat", SubTypeSet.CreatureType),
    GOBLIN("Goblin", SubTypeSet.CreatureType),
    GORGON("Gorgon", SubTypeSet.CreatureType),
    HUMAN("Human", SubTypeSet.CreatureType),
    KITHKIN("Kithkin", SubTypeSet.CreatureType),
    KNIGHT("Knight", SubTypeSet.CreatureType),
    KOBOLD("Kobold", SubTypeSet.CreatureType),
    GOLEM("Golem", SubTypeSet.CreatureType),
    KAVU("Kavu", SubTypeSet.CreatureType),
    MERCENARY("Mercenary", SubTypeSet.CreatureType),
    MERFOLK("Merfolk", SubTypeSet.CreatureType),
    MINION("Minion", SubTypeSet.CreatureType),
    MYR("Myr", SubTypeSet.CreatureType),
    NINJA("Ninja",SubTypeSet.CreatureType),
    OGRE("Ogre", SubTypeSet.CreatureType),
    ORC("Orc", SubTypeSet.CreatureType),
    PENTAVITE("Pentavite", SubTypeSet.CreatureType),
    PRISM("Prism", SubTypeSet.CreatureType),
    RAT("Rat", SubTypeSet.CreatureType),
    REBEL("Rebel", SubTypeSet.CreatureType),
    SAPROLING("Saproling", SubTypeSet.CreatureType),
    SCION("Scion", SubTypeSet.CreatureType),
    SERVO("Servo", SubTypeSet.CreatureType),
    SHAMAN("Shaman", SubTypeSet.CreatureType),
    SKELETON("Skeleton", SubTypeSet.CreatureType),
    SLIVER("Sliver", SubTypeSet.CreatureType),
    SNAKE("Snake", SubTypeSet.CreatureType),
    SOLDIER("Soldier", SubTypeSet.CreatureType),
    SPIRIT("Spirit", SubTypeSet.CreatureType),
    SQUIRREL("Squirrel", SubTypeSet.CreatureType),
    THOPTER("Thopter", SubTypeSet.CreatureType),
    THRULL("Thrull", SubTypeSet.CreatureType),
    TREEFOLK("Treefolk", SubTypeSet.CreatureType),
    TROOPER("Trooper", SubTypeSet.CreatureType),
    VAMPIRE("Vampire", SubTypeSet.CreatureType),
    WALL("Wall", SubTypeSet.CreatureType),
    WARRIOR("Warrior", SubTypeSet.CreatureType),
    WEREWOLF("Werewolf", SubTypeSet.CreatureType),
    WIZARD("Wizard", SubTypeSet.CreatureType),
    WOLF("Wolf", SubTypeSet.CreatureType),
    ZOMBIE("Zombie", SubTypeSet.CreatureType);

    private final SubTypeSet subTypeSet;

    public String getDescription() {
        return description;
    }

    private final String description;

    SubType(String description, SubTypeSet subTypeSet) {
        this.description = description;
        this.subTypeSet = subTypeSet;
    }

    public static SubType byDescription(String subtype) {
        for (SubType s : values()) {
            if (s.getDescription().equals(subtype)) {
                return s;
            }
        }
        throw new IllegalArgumentException("no subtype for " + subtype + " exists");
    }

    public SubTypeSet getSubTypeSet() {
        return subTypeSet;
    }

    public static Set<String> getCreatureTypes() {

       return Arrays.stream(values()).filter(p -> p.getSubTypeSet() == SubTypeSet.CreatureType).map(SubType::getDescription).collect(Collectors.toSet());
    }
}

