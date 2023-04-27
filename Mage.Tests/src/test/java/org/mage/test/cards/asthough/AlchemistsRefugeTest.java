package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Alchemist's Refuge:
 *   {G}{U}, {tap}: You may cast nonland cards this turn as though they had flash.
 *
 * @author noxx
 */
public class AlchemistsRefugeTest extends CardTestPlayerBase {

    @Test
    public void testCastAsThoughHasFlash() {
        addCard(Zone.BATTLEFIELD, playerA, "Alchemist's Refuge");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        // Elite Vanguard 2/1
        addCard(Zone.HAND, playerA, "Elite Vanguard");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}{U}, {T}:");
        castSpell(2, PhaseStep.BEGIN_COMBAT, playerA, "Elite Vanguard");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
    }

    /**
     * Tests playing the same card but without Alchemist's Refuge effect
     */
    @Test
    public void testNoCastPossibleOnOpponentsTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Alchemist's Refuge");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Elite Vanguard");

        checkPlayableAbility("flash on B's turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Elite", false);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    /**
     * Tests that effect will be removed at the end of the turn
     */
    @Test
    public void testEffectOnlyForOneTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Alchemist's Refuge");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Elite Vanguard");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{G}{U}, {T}:");
        checkPlayableAbility("flash on B's turn", 4, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Elite", false);

        setStopAt(4, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}
