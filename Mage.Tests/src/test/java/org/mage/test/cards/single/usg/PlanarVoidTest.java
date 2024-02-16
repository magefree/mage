package org.mage.test.cards.single.usg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.p.PlanarVoid Planar Void}
 * {B}
 * Enchantment
 * Whenever another card is put into a graveyard from anywhere, exile that card.
 *
 * @author Alex-Vasile
 */
public class PlanarVoidTest extends CardTestPlayerBase {
    private static final String planarVoid = "Planar Void";

    /**
     * Test that it triggers for another card going to the graveyard.
     */
    @Test
    public void triggersForOthers() {
        addCard(Zone.BATTLEFIELD, playerA, planarVoid);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertExileCount(playerA, "Lightning Bolt", 1);
        assertExileCount(playerA, "Silvercoat Lion", 1);
    }

    /**
     * Test that it doesn't trigger for itself
     */
    @Test
    public void doesntTriggerForItself() {
        addCard(Zone.BATTLEFIELD, playerA, planarVoid);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Allay"); // {1}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Allay");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertGraveyardCount(playerA, planarVoid, 1);
    }
}
