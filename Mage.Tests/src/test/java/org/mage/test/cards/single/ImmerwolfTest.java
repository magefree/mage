package org.mage.test.cards.single;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class ImmerwolfTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Afflicted Deserter");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Immerwolf");        
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Ornithopter");

        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertPermanentCount(playerB, "Ornithopter", 0);
        assertPermanentCount(playerA, "Afflicted Deserter", 0);
        assertPermanentCount(playerA, "Werewolf Ransacker", 1);
    }

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Afflicted Deserter");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Immerwolf");        
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Ornithopter");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Constants.Zone.HAND, playerB, "Lightning Bolt", 2);

        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        setStopAt(3, Constants.PhaseStep.DRAW);
        execute();

        assertLife(playerA, 14);
        assertLife(playerB, 17);
        assertPermanentCount(playerB, "Ornithopter", 0);
        assertPermanentCount(playerA, "Afflicted Deserter", 0);
        assertPermanentCount(playerA, "Werewolf Ransacker", 1);
    }

}
