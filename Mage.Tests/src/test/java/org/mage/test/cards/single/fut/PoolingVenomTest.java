package org.mage.test.cards.single.fut;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class PoolingVenomTest extends CardTestPlayerBase {

    private static final String venom = "Pooling Venom";
    // Whenever enchanted land becomes tapped, its controller loses 2 life.

    private static final String island = "Island";
    private static final String swamp = "Swamp";
    private static final String addU = "{T}: Add {U}";
    private static final String addB = "{T}: Add {B}";

    @Test
    public void testOppsLand() {
        addCard(Zone.BATTLEFIELD, playerB, island);
        addCard(Zone.BATTLEFIELD, playerA, swamp, 2);
        addCard(Zone.HAND, playerA, venom);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, venom, island);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, addU);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, venom, 1);
        assertTapped(island, true);
        assertLife(playerA, 20);
        assertLife(playerB, 18);

    }

    @Test
    public void testOwnLand() {
        addCard(Zone.BATTLEFIELD, playerA, island);
        addCard(Zone.BATTLEFIELD, playerA, swamp, 2);
        addCard(Zone.HAND, playerA, venom);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, addB);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, addB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, venom, island);

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, addU);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, venom, 1);
        assertTapped(island, true);
        assertLife(playerA, 18);
        assertLife(playerB, 20);
    }
}
