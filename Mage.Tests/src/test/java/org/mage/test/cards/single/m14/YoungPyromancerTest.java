package org.mage.test.cards.single.m14;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author weirddan455
 */
public class YoungPyromancerTest extends CardTestPlayerBase {

    // https://github.com/magefree/mage/issues/7095
    // Cards that sacrifice Young Pyromancer as part of the cost should not trigger the ability.
    // Result should be 1 token made from casting Lightning Bolt.  Village Rites should not make a token.
    @Test
    public void villageRitesTest() {
        removeAllCardsFromHand(playerA);

        addCard(Zone.BATTLEFIELD, playerA, "Young Pyromancer");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerA, "Village Rites");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Village Rites");
        setChoice(playerA, "Young Pyromancer");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertLife(playerB, 17);
        assertHandCount(playerA, 2);
        assertPermanentCount(playerA, "Elemental", 1);
    }
}
