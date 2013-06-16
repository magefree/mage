package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class ImmerwolfTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Afflicted Deserter");
        addCard(Zone.BATTLEFIELD, playerA, "Immerwolf");
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertPermanentCount(playerB, "Ornithopter", 0);
        assertPermanentCount(playerA, "Afflicted Deserter", 0);
        assertPermanentCount(playerA, "Werewolf Ransacker", 1);
    }

    @Test
    public void testCard1() {
        addCard(Zone.BATTLEFIELD, playerA, "Afflicted Deserter");
        addCard(Zone.BATTLEFIELD, playerA, "Immerwolf");
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 14);
        assertLife(playerB, 17);
        assertPermanentCount(playerB, "Ornithopter", 0);
        assertPermanentCount(playerA, "Afflicted Deserter", 0);
        assertPermanentCount(playerA, "Werewolf Ransacker", 1);
    }

}
