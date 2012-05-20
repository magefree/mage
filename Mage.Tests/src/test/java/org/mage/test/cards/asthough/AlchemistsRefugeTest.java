package org.mage.test.cards.asthough;

import mage.Constants;
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
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Alchemist's Refuge");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.HAND, playerA, "Elite Vanguard");

        activateAbility(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{G}{U}, {tap}:");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");

        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
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
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Alchemist's Refuge");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.HAND, playerA, "Elite Vanguard");

        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");

        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // it shouldn't be possible, so no Elite Vanguard on the battlefield
        assertPermanentCount(playerA, "Elite Vanguard", 0);
    }

    /**
     * Tests that effect will be removed at the end of the turn
     */
    @Test
    public void testEffectOnlyForOneTurn() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Alchemist's Refuge");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.HAND, playerA, "Elite Vanguard");

        activateAbility(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{G}{U}, {tap}:");
        castSpell(4, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");

        setStopAt(4, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Elite Vanguard", 0);
    }

}
