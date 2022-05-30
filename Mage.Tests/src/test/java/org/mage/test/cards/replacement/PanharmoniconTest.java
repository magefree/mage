
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PanharmoniconTest extends CardTestPlayerBase {

    /**
     * Check that Panharmonicon adds EtB triggers correctly.
     */
    @Test
    public void testAddsTrigger() {
        // If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Whenever another creature enters the battlefield, you gain 1 life.
        addCard(Zone.HAND, playerA, "Soul Warden");
        // When Devout Monk enters the battlefield, you gain 1 life.
        addCard(Zone.HAND, playerA, "Devout Monk");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Soul Warden");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Devout Monk"); // Life: 20 + 2*1 + 2*1 = 24

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 24);
    }

    /**
     * Check that Panharmonicon doesn't add to opponents' triggers.
     */
    @Test
    public void testDoesntAddOpponentsTriggers() {
        // If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Whenever another creature enters the battlefield, you gain 1 life.
        addCard(Zone.HAND, playerB, "Soul Warden");
        // When Devout Monk enters the battlefield, you gain 1 life.
        addCard(Zone.HAND, playerB, "Devout Monk");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Soul Warden");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Devout Monk"); // Life: 20 + 1 + 1 = 22

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 22);
    }

    /**
     * Check that Panharmonicon doesn't add to lands triggers.
     */
    @Test
    public void testDoesntAddLandsTriggers() {
        // If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        // When Radiant Fountain enters the battlefield, you gain 2 life.
        addCard(Zone.HAND, playerA, "Radiant Fountain");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Radiant Fountain"); // Life: 20 + 2 = 22

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 22);
    }

    /**
     * Check that Panharmonicon doesn't add to non-permanents triggers.
     */
    @Test
    public void testDoesntAddNonPermanentsTriggers() {
        // If an artifact or creature entering the battlefield causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        // When a Dragon enters the battlefield, you may return Bladewing's Thrall from your graveyard to the battlefield.
        addCard(Zone.GRAVEYARD, playerA, "Bladewing's Thrall");
        // A 4/4 vanilla dragon
        addCard(Zone.HAND, playerA, "Scion of Ugin");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scion of Ugin");
        setChoice(playerA, false); // Return Bladewing's Thrall from your graveyard to the battlefield?
        // There should only be one trigger, so no need for another choice

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Bladewing's Thrall", 1);
    }
}
