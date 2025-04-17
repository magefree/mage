package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class BecomesBlockedTest extends CardTestPlayerBase {

    private static final String trapRunner = "Trap Runner"; // 2/3
    private static final String blockAbility = "{T}: Target unblocked attacking creature becomes blocked";
    // {T}: Target unblocked attacking creature becomes blocked.
    // Activate only during combat after blockers are declared.
    // (This ability works on creatures that can’t be blocked.)

    private static final String somberwaldAlpha = "Somberwald Alpha"; // 3/2
    // Whenever a creature you control becomes blocked, it gets +1/+1 until end of turn.

    private static final String slitherBlade = "Slither Blade"; // 1/2 can't be blocked
    private static final String forestwalker = "Somberwald Dryad"; // 2/2 forestwalk


    @Test
    public void testCantBeBlockedBecomesBlocked() {
        addCard(Zone.BATTLEFIELD, playerA, slitherBlade);
        addCard(Zone.BATTLEFIELD, playerA, somberwaldAlpha);
        addCard(Zone.BATTLEFIELD, playerB, trapRunner);

        attack(1, playerA, slitherBlade, playerB);
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerB, blockAbility, slitherBlade);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertPowerToughness(playerA, slitherBlade, 2, 3);
    }

    @Test
    public void testForestwalkerBecomesBlockedNoForest() {
        addCard(Zone.BATTLEFIELD, playerA, forestwalker);
        addCard(Zone.BATTLEFIELD, playerA, somberwaldAlpha);
        addCard(Zone.BATTLEFIELD, playerB, trapRunner);

        attack(1, playerA, forestwalker, playerB);
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerB, blockAbility, forestwalker);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertPowerToughness(playerA, forestwalker, 3, 3);
    }

    @Test
    public void testForestwalkerBecomesBlockedWithForest() {
        addCard(Zone.BATTLEFIELD, playerA, forestwalker);
        addCard(Zone.BATTLEFIELD, playerA, somberwaldAlpha);
        addCard(Zone.BATTLEFIELD, playerB, trapRunner);
        addCard(Zone.BATTLEFIELD, playerB, "Forest");

        attack(1, playerA, forestwalker, playerB);
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerB, blockAbility, forestwalker);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20);
        assertPowerToughness(playerA, forestwalker, 3, 3);
    }

    @Test
    public void testTrampleBlocked() {
        String wurm = "Yavimaya Wurm"; // 6/4 trample

        addCard(Zone.BATTLEFIELD, playerA, wurm);
        addCard(Zone.BATTLEFIELD, playerA, somberwaldAlpha);
        addCard(Zone.BATTLEFIELD, playerB, trapRunner);
        addCard(Zone.BATTLEFIELD, playerB, "Forest");

        attack(1, playerA, wurm, playerB);
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerB, blockAbility, wurm);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 13);
        assertPowerToughness(playerA, wurm, 7, 5);
    }

}
