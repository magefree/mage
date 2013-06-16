package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class CounterlashTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 6);
        addCard(Zone.HAND, playerB, "Counterlash");
        addCard(Zone.HAND, playerB, "Beacon of Immortality");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterlash", "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 40);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, 1);

    }


}
