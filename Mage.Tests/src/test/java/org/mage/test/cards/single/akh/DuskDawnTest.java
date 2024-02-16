package org.mage.test.cards.single.akh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.d.DuskDawn Dusk}
 * {2}{W}{W}
 * Sorcery
 * Destroy all creatures with power 3 or greater.
 *
 * {@link mage.cards.d.DuskDawn Dawn}
 * {3}{W}{W}
 * Sorcery
 * Aftermath (Cast this spell only from your graveyard. Then exile it.)
 * Return all creature cards with power 2 or less from your graveyard to your hand.
 *
 * @author Quercitron
 */
public class DuskDawnTest extends CardTestPlayerBase {

    /**
     * Test that you can properly cast Dusk (regular part) from hand
     */
    @Test
    public void testCastDusk() {
        addCard(Zone.BATTLEFIELD, playerB, "Watchwolf");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Dusk // Dawn");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dusk");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // check that we paid the right side's mana
        assertTappedCount("Plains", true, 4);

        assertPermanentCount(playerB, "Watchwolf", 0);
        assertGraveyardCount(playerB, "Watchwolf", 1);
        assertGraveyardCount(playerA, "Dusk // Dawn", 1);
    }

    /**
     * Test that you cannot cast Dusk (Reguar part) from graveyard.
     */
    @Test
    public void testCastDuskFromGraveyardFail() {
        addCard(Zone.BATTLEFIELD, playerB, "Watchwolf");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.GRAVEYARD, playerA, "Dusk // Dawn");

        checkPlayableAbility("Can't cast Dusk", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dusk", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Watchwolf", 1);
        assertGraveyardCount(playerB, "Watchwolf", 0);
        assertGraveyardCount(playerA, "Dusk // Dawn", 1);
    }

    /**
     * Test that you can cast Dawn (Aftermath part) from graveyard.
     */
    @Test
    public void testCastDawnFromGraveyard() {
        addCard(Zone.GRAVEYARD, playerA, "Dusk // Dawn");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.GRAVEYARD, playerA, "Devoted Hero");
        addCard(Zone.GRAVEYARD, playerA, "Watchwolf");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dawn");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Dusk // Dawn should have been cast and exiled
        assertExileCount(playerA, "Dusk // Dawn", 1);
        assertGraveyardCount(playerA, "Dusk // Dawn", 0);

        // Devoted hero should be in the hand
        assertHandCount(playerA, "Devoted Hero", 1);
        assertGraveyardCount(playerA, "Devoted Hero", 0);

        // Watchwolf should still be in the graveyard
        assertGraveyardCount(playerA, "Watchwolf", 1);
    }

    /**
     * Test that you can't cast Dawn (Aftermath part) from hand.
     */
    @Test
    public void testCastDawnFail() {
        addCard(Zone.HAND, playerA, "Dusk // Dawn");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.GRAVEYARD, playerA, "Devoted Hero"); // Creature 1/2 {W}

        checkPlayableAbility("Can't cast Dawn", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Dawn", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Dusk // Dawn", 1);
    }
}
