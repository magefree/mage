package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests regenerate and
 * tests that permanents with protection can be sacrificed
 * 
 * @author BetaSteward
 */
public class FaithsShieldTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Faith's Shield");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        setChoice(playerA, "Red");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Faith's Shield", "White Knight");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "White Knight");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertPermanentCount(playerA, "White Knight", 1);
    }

    @Test
    public void testCardExile1() {
        setLife(playerA, 5);
        addCard(Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Faith's Shield");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        setChoice(playerA, "Red");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Faith's Shield", "White Knight");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 5);
        assertLife(playerB, 20);
    }

}
