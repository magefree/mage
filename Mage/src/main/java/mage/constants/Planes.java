
package mage.constants;

/**
 *
 * @author spjspj
 */
public enum Planes {
    PLANE_ACADEMY_AT_TOLARIA_WEST("AcademyAtTolariaWestPlane"),
    PLANE_AGYREM("AgyremPlane"),
    PLANE_AKOUM("AkoumPlane"),
    PLANE_ASTRAL_ARENA("AstralArenaPlane"),
    PLANE_BANT("BantPlane"),
    PLANE_EDGE_OF_MALACOL("EdgeOfMalacolPlane"),
    PLANE_FEEDING_GROUNDS("FeedingGroundsPlane"),
    PLANE_FIELDS_OF_SUMMER("FieldsOfSummerPlane"),
    PLANE_HEDRON_FIELDS_OF_AGADEEM("HedronFieldsOfAgadeemPlane"),
    PLANE_LETHE_LAKE("LetheLakePlane"),
    PLANE_NAYA("NayaPlane"),
    PLANE_PANOPTICON("PanopticonPlane"),
    PLANE_TAZEEM("TazeemPlane"),
    PLANE_THE_DARK_BARONY("TheDarkBaronyPlane"),
    PLANE_THE_EON_FOG("TheEonFogPlane"),
    PLANE_THE_GREAT_FOREST("TheGreatForestPlane"),
    PLANE_THE_ZEPHYR_MAZE_FOG("TheZephyrMazePlane"),
    PLANE_TRUGA_JUNGLE("TrugaJunglePlane"),
    PLANE_TRAIL_OF_THE_MAGE_RINGS("TrailOfTheMageRingsPlane"),
    PLANE_TURRI_ISLAND("TurriIslandPlane"),
    PLANE_UNDERCITY_REACHES("UndercityReachesPlane");
    
    private final String text;

    Planes(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
