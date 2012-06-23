package org.mage.test.cards.single;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests regenerate and
 * tests that permanents with protection can be sacrificed
 * 
 * @author BetaSteward
 */
public class ClingingMistsTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Constants.Zone.HAND, playerA, "Clinging Mists");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Clinging Mists");
        attack(1, playerA, "White Knight");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void testCardExile1() {
        setLife(playerA, 5);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Abbey Griffin");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Constants.Zone.HAND, playerA, "Clinging Mists");

        attack(1, playerA, "Abbey Griffin");
        castSpell(1, Constants.PhaseStep.DECLARE_BLOCKERS, playerA, "Clinging Mists");

        setStopAt(3, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 5);
        assertLife(playerB, 20);
        assertTapped("Abbey Griffin", true);
    }

    @Test
    public void testCardExile2() {
        setLife(playerA, 5);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Constants.Zone.HAND, playerA, "Clinging Mists");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Abbey Griffin");
        addCard(Constants.Zone.HAND, playerB, "Lightning Bolt");

        attack(2, playerB, "Abbey Griffin");
        castSpell(2, Constants.PhaseStep.DECLARE_BLOCKERS, playerA, "Clinging Mists");
        castSpell(2, Constants.PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStopAt(6, Constants.PhaseStep.DRAW);
        execute();

        assertLife(playerA, 2);
        assertLife(playerB, 20);
        assertTapped("Abbey Griffin", false);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
    }

}
