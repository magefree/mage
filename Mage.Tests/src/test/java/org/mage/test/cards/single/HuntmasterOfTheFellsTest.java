package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward, noxx
 */
public class HuntmasterOfTheFellsTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Huntmaster of the Fells");
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Huntmaster of the Fells");
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 22);
        assertLife(playerB, 18);
        assertPermanentCount(playerA, "Wolf", 1);
        assertPermanentCount(playerA, "Huntmaster of the Fells", 0);
        assertPermanentCount(playerA, "Ravager of the Fells", 1);
        assertPermanentCount(playerB, "Ornithopter", 0);
    }

    /**
     * Tests first trigger happens both on enter battlefield and transform events
     */
    @Test
    public void testCard2() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Huntmaster of the Fells");
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Huntmaster of the Fells");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        setStopAt(4, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 18); // -6 damage, +4 life
        assertLife(playerB, 18);
        assertPermanentCount(playerA, "Wolf", 2);
        assertPermanentCount(playerA, "Ravager of the Fells", 0); // transformed back
        assertPermanentCount(playerA, "Huntmaster of the Fells", 1);
        assertPermanentCount(playerB, "Ornithopter", 0);
    }
}
