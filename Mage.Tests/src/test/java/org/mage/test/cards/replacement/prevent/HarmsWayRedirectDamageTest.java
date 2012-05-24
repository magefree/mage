package org.mage.test.cards.replacement.prevent;

import mage.Constants;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Harm's Way:
 *   The next 2 damage that a source of your choice would deal to you and/or permanents you control this turn is dealt to target creature or player instead.
 *
 * @author noxx
 */
public class HarmsWayRedirectDamageTest extends CardTestPlayerBase {

    /**
     * Tests that 2 of 3 damage is redirected while 1 damage is still dealt to original target
     */
    @Test
    public void testRedirectTwoDamage() {
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Constants.Zone.HAND, playerB, "Harm's Way");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Harm's Way", "Lightning Bolt^targetPlayer=PlayerA");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        // 2 damage was redirected back
        assertLife(playerA, 18);

        // 1 damage was still dealt
        assertLife(playerB, 19);
    }

    /**
     * Tests redirecting combat damage
     */
    @Test
    @Ignore
    // This test doesn't work in test framework but the test case works fine in real game
    //  -- this is because of no possibility to ask AI to play spell when triggered is in the stack
    public void testRedirectCombatDamage() {
        addCard(Constants.Zone.HAND, playerA, "Harm's Way");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        attack(2, playerB, "Craw Wurm");
        castSpell(2, Constants.PhaseStep.DECLARE_BLOCKERS, playerA, "Harm's Way", "Craw Wurm^targetPlayer=PlayerB");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        // only 4 combat damage
        assertLife(playerA, 16);

        // 2 damage is redirected
        assertLife(playerB, 18);
    }

    /**
     * Tests redirecting from triggered ability
     */
    @Test
    public void testRedirectTriggeredAbilityDamage() {
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.HAND, playerA, "Harm's Way");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Magma Phoenix");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Magma Phoenix");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Harm's Way", "Magma Phoenix^targetPlayer=PlayerB");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 19);

        assertLife(playerB, 15);
    }

}
