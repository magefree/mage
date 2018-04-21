/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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
    PLANE_TAZEEM("TazeemPlane"),
    PLANE_THE_DARK_BARONY("TheDarkBaronyPlane"),
    PLANE_THE_EON_FOG("TheEonFogPlane"),
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
