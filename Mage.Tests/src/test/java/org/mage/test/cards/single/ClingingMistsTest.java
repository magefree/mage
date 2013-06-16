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
public class ClingingMistsTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Clinging Mists");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clinging Mists");
        attack(1, playerA, "White Knight");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void testCardExile1() {
        setLife(playerA, 5);
        addCard(Zone.BATTLEFIELD, playerA, "Abbey Griffin");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Clinging Mists");

        attack(1, playerA, "Abbey Griffin");
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Clinging Mists");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 5);
        assertLife(playerB, 20);
        assertTapped("Abbey Griffin", true);
    }

    @Test
    public void testCardExile2() {
        setLife(playerA, 5);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Clinging Mists");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Abbey Griffin");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        attack(2, playerB, "Abbey Griffin");
        castSpell(2, PhaseStep.DECLARE_BLOCKERS, playerA, "Clinging Mists");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStopAt(6, PhaseStep.DRAW);
        execute();

        assertLife(playerA, 2);
        assertLife(playerB, 20);
        assertTapped("Abbey Griffin", false);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
    }

}
