package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class TerrificTeamUpTest extends CardTestPlayerBase {

    /*
    Terrific Team-Up
    {3}{G}
    Instant
    This spell costs {2} less to cast if you control a permanent with mana value 4 or greater.
    One or two target creatures you control each get +1/+0 until end of turn. They each deal damage equal to their power to target creature an opponent controls.
    */
    private static final String terrificTeamUp = "Terrific Team-Up";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear
    
    2/2
    */
    private static final String bearCub = "Bear Cub";

    /*
    Sea Monster
    {4}{U}{U}
    Creature - Serpent
    Sea Monster can't attack unless defending player controls an Island.
    6/6
    */
    private static final String seaMonster = "Sea Monster";

    @Test
    public void testTerrificTeamUp() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, bearCub, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerB, seaMonster);
        addCard(Zone.HAND, playerA, terrificTeamUp);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, terrificTeamUp);
        addTarget(playerA, bearCub, 2);
        addTarget(playerA, seaMonster);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, seaMonster, 1);
        assertPowerToughness(playerA, bearCub, 3, 2, Filter.ComparisonScope.All);
    }
}