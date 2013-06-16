package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class UnhallowedCatharTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Loyal Cathar");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Loyal Cathar");
        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Loyal Cathar", 0);
        assertPermanentCount(playerA, "Unhallowed Cathar", 1);
    }

}
