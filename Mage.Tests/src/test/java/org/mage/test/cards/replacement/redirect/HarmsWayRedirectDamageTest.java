package org.mage.test.cards.replacement.redirect;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Harm's Way: The next 2 damage that a source of your choice would deal to you
 * and/or permanents you control this turn is dealt to any target instead.
 *
 * @author noxx
 */
public class HarmsWayRedirectDamageTest extends CardTestPlayerBase {

    /**
     * Tests that 2 of 3 damage is redirected while 1 damage is still dealt to
     * original target
     */
    @Test
    public void testRedirectTwoDamage() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Zone.HAND, playerB, "Harm's Way"); // Instant {W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Harm's Way", playerA);
        setChoice(playerB, "Lightning Bolt");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Harm's Way", 1);

        // 2 damage was redirected back
        assertLife(playerA, 18);

        // 1 damage was still dealt
        assertLife(playerB, 19);
    }

    /**
     * Tests redirecting combat damage
     */
    @Test
    public void testRedirectCombatDamage() {
        addCard(Zone.HAND, playerA, "Harm's Way");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        attack(2, playerB, "Craw Wurm");
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Harm's Way", playerB);
        setChoice(playerA, "Craw Wurm");

        setStopAt(2, PhaseStep.END_TURN);
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
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        // The next 2 damage that a source of your choice would deal to you and/or permanents
        // you control this turn is dealt to any target instead.
        addCard(Zone.HAND, playerA, "Harm's Way");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        // Flying
        // When Magma Phoenix dies, it deals 3 damage to each creature and each player.
        addCard(Zone.BATTLEFIELD, playerB, "Magma Phoenix");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Harm's Way", playerB);
        setChoice(playerA, "Magma Phoenix");
        /**
         * When Magma Phoenix dies, Magma Phoenix deals 3 damage to each
         * creature and each player *
         */
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Magma Phoenix");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 19); // 3 damage from dying Phoenix -> 2 redirected to playerB so playerA gets only 1 damage

        assertLife(playerB, 15); // 3 damage from dying Phoenix directly and 2 redirected damage from playerA
    }

    /**
     * Tests that not preventable damage is redirected
     */
    @Test
    public void testRedirectNotPreventableDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        // <i>Ferocious</i> If you control a creature with power 4 or greater, damage can't be prevented this turn.
        // Wild Slash deals 2 damage to any target.
        addCard(Zone.HAND, playerA, "Wild Slash"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Serra Angel");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        // The next 2 damage that a source of your choice would deal to you and/or permanents
        // you control this turn is dealt to any target instead.
        addCard(Zone.HAND, playerB, "Harm's Way"); // {W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.BATTLEFIELD, playerB, "Birds of Paradise");

        // the 2 damage can't be prevented and have to be redirected to Silvercoat Lion of player A
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wild Slash", "Birds of Paradise");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Harm's Way", "Silvercoat Lion", "Wild Slash");
        setChoice(playerB, "Wild Slash");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Wild Slash", 1);
        assertGraveyardCount(playerB, "Harm's Way", 1);
        assertPermanentCount(playerB, "Birds of Paradise", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

}
