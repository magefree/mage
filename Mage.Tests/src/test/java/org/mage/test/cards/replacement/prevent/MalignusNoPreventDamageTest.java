package org.mage.test.cards.replacement.prevent;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Malignus:
 *   Malignus's power and toughness are each equal to half the highest life total among your opponents, rounded up
 *   Damage that would be dealt by Malignus can't be prevented.
 *
 * @author noxx
 */
public class MalignusNoPreventDamageTest extends CardTestPlayerBase {

    /**
     * Tests "If Malignus becomes blocked by a creature with protection from red, the damage Malignus deals to that creature won't be prevented."
     */
    @Test
    public void testBlockByCreatureWithProRed() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Vedalken Outlander");
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Malignus");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);

        attack(2, playerB, "Malignus");
        block(2, playerA, "Vedalken Outlander", "Malignus");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 17);
        assertLife(playerB, 20);

        assertPermanentCount(playerB, "Malignus", 1);
        assertPowerToughness(playerB, "Malignus", 9, 9);

        assertPermanentCount(playerA, "Vedalken Outlander", 0);
    }

    /**
     * Tests that blocking red creature by creature by pro red will prevent damage
     */
    @Test
    public void testBlockAnotherWithProRed() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Vedalken Outlander");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Ogre Resister");

        attack(2, playerB, "Ogre Resister");
        block(2, playerA, "Vedalken Outlander", "Ogre Resister");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerB, "Ogre Resister", 1);
        assertPermanentCount(playerA, "Vedalken Outlander", 1);
    }

}
