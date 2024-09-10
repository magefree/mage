package org.mage.test.cards.single.otj;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SlickSequenceTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SlickSequence Slick Sequence} {U}{R}
     * Instant
     * Slick Sequence deals 2 damage to any target. If you’ve cast another spell this turn, draw a card.
     */
    private static final String sequence = "Slick Sequence";

    @Test
    public void testOne() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 2);
        addCard(Zone.HAND, playerA, sequence);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sequence, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, sequence, 1);
        assertLife(playerB, 20 - 2);
        assertHandCount(playerA, 0);
    }

    @Test
    public void testOneResolveThenAnother() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 4);
        addCard(Zone.HAND, playerA, sequence, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sequence, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sequence, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, sequence, 2);
        assertLife(playerB, 20 - 2 * 2);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testOneThenAnotherInResponse() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 4);
        addCard(Zone.HAND, playerA, sequence, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sequence, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sequence, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, sequence, 2);
        assertLife(playerB, 20 - 2 * 2);
        assertHandCount(playerA, 1 * 2);
    }

    @Test
    public void testOneRemandedThenRecast() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 4);
        addCard(Zone.HAND, playerA, sequence, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Remand", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sequence, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Remand", sequence);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sequence, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, sequence, 1);
        assertGraveyardCount(playerB, "Remand", 1);
        assertLife(playerB, 20 - 2);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testOtherPlayerSpellsNotCounting() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 2);
        addCard(Zone.HAND, playerA, sequence, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sequence, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, sequence, 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertLife(playerB, 20 - 2 - 3);
        assertHandCount(playerA, 0);
    }
}
