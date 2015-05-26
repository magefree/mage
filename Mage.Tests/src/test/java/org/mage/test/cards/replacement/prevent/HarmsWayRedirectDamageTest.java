package org.mage.test.cards.replacement.prevent;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Zone.HAND, playerB, "Harm's Way");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Harm's Way", playerA);
        setChoice(playerB, "Lightning Bolt");

        setStopAt(1, PhaseStep.END_TURN);
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
        // you control this turn is dealt to target creature or player instead.
        addCard(Zone.HAND, playerA, "Harm's Way");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        // Flying
        // When Magma Phoenix dies, it deals 3 damage to each creature and each player.
        addCard(Zone.BATTLEFIELD, playerB, "Magma Phoenix");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Harm's Way", playerB);
        setChoice(playerA, "Magma Phoenix");        
        /** When Magma Phoenix dies, Magma Phoenix deals 3 damage to each creature and each player **/
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Magma Phoenix");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 19); // 3 damage from dying Phoenix -> 2 redirected to playerB so playerA gets only 1 damage

        assertLife(playerB, 15); // 3 damage from dying Phoenix directly and 2 redirected damage from playerA
    }

}
