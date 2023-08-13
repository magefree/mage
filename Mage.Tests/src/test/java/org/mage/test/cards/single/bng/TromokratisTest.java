package org.mage.test.cards.single.bng;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TromokratisTest extends CardTestPlayerBase {

    /**
     * Tromokratis
     * {5}{U}{U}
     * Legendary Creature — Kraken
     * <p>
     * Tromokratis has hexproof unless it’s attacking or blocking.
     * <p>
     * Tromokratis can’t be blocked unless all creatures defending player controls block it. (If any creature that player controls doesn’t block this creature, it can’t be blocked.)
     */
    private final static String tromokratis = "Tromokratis";

    @Test
    public void notBlocked() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tromokratis);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 3); // 1/1

        attack(1, playerA, tromokratis);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();
    }

    @Test
    public void blockedByAll() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tromokratis);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 3); // 1/1

        attack(1, playerA, tromokratis);
        block(1, playerB, "Memnite:0", tromokratis);
        block(1, playerB, "Memnite:1", tromokratis);
        block(1, playerB, "Memnite:2", tromokratis);
        // Blockers are ordered in order added by the test framework
        setChoice(playerA, "X=1"); // 1 damage to first defender
        setChoice(playerA, "X=1"); // 1 damage to second defender
        setChoice(playerA, "X=6"); // 6 damage to third defender

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        execute();
    }

    @Test
    public void blockedIncorrect() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tromokratis);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 3); // 1/1

        attack(1, playerA, tromokratis);
        block(1, playerB, "Memnite:0", tromokratis);
        block(1, playerB, "Memnite:1", tromokratis);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);

        try {
            execute();
            fail("Expected exception not thrown");
        } catch (UnsupportedOperationException e) {
            assertEquals(tromokratis + " is blocked incorrectly. It can't be blocked unless all creatures defending player controls block it.", e.getMessage());
        }
    }
}
