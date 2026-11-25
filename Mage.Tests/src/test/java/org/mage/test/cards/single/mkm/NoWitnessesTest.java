package org.mage.test.cards.single.mkm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.n.NoWitnesses No Witnesses}
 * {2}{W}{W}
 * Sorcery
 * Each player who controls the most creatures investigates. Then destroy all creatures.
 */
public class NoWitnessesTest extends CardTestPlayerBase {

    private static final String witnesses = "No Witnesses";
    private static final String bears = "Grizzly Bears"; // 2/2
    private static final String clue = "Clue Token";

    private void castWitnesses() {
        addCard(Zone.HAND, playerA, witnesses);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, witnesses);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertGraveyardCount(playerA, witnesses, 1);
    }

    /**
     * Nobody controls a creature, so every player is tied for the most (zero) and each investigates.
     */
    @Test
    public void testNoCreaturesEachPlayerInvestigates() {
        castWitnesses();

        assertPermanentCount(playerA, clue, 1);
        assertPermanentCount(playerB, clue, 1);
    }

    /**
     * A tie above zero: both players investigate.
     */
    @Test
    public void testTieAtTwoBothInvestigate() {
        addCard(Zone.BATTLEFIELD, playerA, bears, 2);
        addCard(Zone.BATTLEFIELD, playerB, bears, 2);
        castWitnesses();

        assertPermanentCount(playerA, clue, 1);
        assertPermanentCount(playerB, clue, 1);
        assertPermanentCount(playerA, bears, 0);
        assertPermanentCount(playerB, bears, 0);
    }

    /**
     * Only the player with the most creatures investigates; a zero-creature player does not.
     */
    @Test
    public void testOnlyMostInvestigates() {
        addCard(Zone.BATTLEFIELD, playerB, bears, 1);
        castWitnesses();

        assertPermanentCount(playerA, clue, 0);
        assertPermanentCount(playerB, clue, 1);
    }
}
