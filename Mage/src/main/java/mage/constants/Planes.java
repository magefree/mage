package mage.constants;

/**
 * @author spjspj
 */
public enum Planes {
    PLANE_ACADEMY_AT_TOLARIA_WEST("AcademyAtTolariaWestPlane", "Plane - Academy at Tolaria West"),
    PLANE_AGYREM("AgyremPlane", "Plane - Agyrem"),
    PLANE_AKOUM("AkoumPlane", "Plane - Akoum"),
    PLANE_ASTRAL_ARENA("AstralArenaPlane", "Plane - Astral Arena"),
    PLANE_BANT("BantPlane", "Plane - Bant"),
    PLANE_EDGE_OF_MALACOL("EdgeOfMalacolPlane", "Plane - Edge of Malacol"),
    PLANE_FEEDING_GROUNDS("FeedingGroundsPlane", "Plane - Feeding Grounds"),
    PLANE_FIELDS_OF_SUMMER("FieldsOfSummerPlane", "Plane - Fields of Summer"),
    PLANE_HEDRON_FIELDS_OF_AGADEEM("HedronFieldsOfAgadeemPlane", "Plane - Hedron Fields of Agadeem"),
    PLANE_LETHE_LAKE("LetheLakePlane", "Plane - Lethe Lake"),
    PLANE_NAYA("NayaPlane", "Plane - Naya"),
    PLANE_PANOPTICON("PanopticonPlane", "Plane - Panopticon"),
    PLANE_TAZEEM("TazeemPlane", "Plane - Tazeem"),
    PLANE_THE_DARK_BARONY("TheDarkBaronyPlane", "Plane - The Dark Barony"),
    PLANE_THE_EON_FOG("TheEonFogPlane", "Plane - The Eon Fog"),
    PLANE_THE_GREAT_FOREST("TheGreatForestPlane", "Plane - The Great Forest"),
    PLANE_THE_ZEPHYR_MAZE_FOG("TheZephyrMazePlane", "Plane - The Zephyr Maze"),
    PLANE_TRUGA_JUNGLE("TrugaJunglePlane", "Plane - Truga Jungle"),
    PLANE_TRAIL_OF_THE_MAGE_RINGS("TrailOfTheMageRingsPlane", "Plane - Trail of the Mage-Rings"),
    PLANE_TURRI_ISLAND("TurriIslandPlane", "Plane - Turri Island"),
    PLANE_UNDERCITY_REACHES("UndercityReachesPlane", "Plane - Undercity Reaches");

    private final String className;
    private final String fullName;

    Planes(String className, String fullName) {
        this.className = className;
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return className;
    }

    public String getClassName() {
        return className;
    }

    public String getFullName() {
        return fullName;
    }

    public static Planes fromFullName(String fullName) {
        for (Planes p : Planes.values()) {
            if (p.fullName.equals(fullName)) {
                return p;
            }
        }

        return null;
    }

    public static Planes fromClassName(String className) {
        for (Planes p : Planes.values()) {
            if (p.className.equals(className)) {
                return p;
            }
        }

        return null;
    }
}
