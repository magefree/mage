package org.mage.test.cards.single.m14;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ChandraPyromasterTest extends CardTestPlayerBase {

    @Test
    public void testAbility2CastCardFromExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Pyromaster");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Mizzium Mortars");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "0: Exile the top card of your library. You may play it this turn.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mizzium Mortars", "Silvercoat Lion");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Chandra, Pyromaster", 1);
        assertGraveyardCount(playerA, "Mizzium Mortars", 1);

        assertPermanentCount(playerB, "Silvercoat Lion", 0);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

    }

    @Test
    public void testAbility2AncestralVision() {
        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Pyromaster");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Ancestral Vision");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "0: Exile the top card of your library. You may play it this turn.");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Chandra, Pyromaster", 1);
        assertGraveyardCount(playerA, "Ancestral Vision", 0);

        assertExileCount(playerA, "Ancestral Vision", 1);
    }

    @Test
    public void testAbility2CastCardFromExileWithOverload() {

        addCard(Zone.BATTLEFIELD, playerA, "Chandra, Pyromaster");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Mizzium Mortars");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "0: Exile the top card of your library. You may play it this turn.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mizzium Mortars with overload");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Chandra, Pyromaster", 1);
        assertGraveyardCount(playerA, "Mizzium Mortars", 1);

        assertPermanentCount(playerB, "Silvercoat Lion", 0);
        assertGraveyardCount(playerB, "Silvercoat Lion", 2);
    }
}
