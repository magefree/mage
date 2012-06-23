package org.mage.test.cards.triggers.dies;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 *
 *   Whenever another nontoken creature dies, you may draw a card.
 */
public class HarvesterOfSoulsTest extends CardTestPlayerBase {

    /**
     * Tests creature on any side would trigger effect
     * Also tests that tokens don't cause trigger to happen
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Constants.Zone.HAND, playerA, "Day of Judgment", 1);
        addCard(Constants.Zone.HAND, playerA, "Thatcher Revolt", 1);

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Harvester of Souls", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Craw Wurm", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Arrogant Bloodlord", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Thatcher Revolt");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Day of Judgment");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertHandCount(playerA, 2);
        assertHandCount(playerB, 0);
    }

}
