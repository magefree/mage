package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class MrNegativeTest extends CardTestPlayerBase {

    /*
    Mr. Negative
    {5}{W}{B}
    Legendary Creature - Human Villain
    Vigilance, lifelink
    When Mr. Negative enters, you may exchange your life total with target opponent. If you lose life this way, draw that many cards.
    5/5
    */
    private static final String mrNegative = "Mr. Negative";

    @Test
    public void testMrNegative() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, mrNegative);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 7);
        setLife(playerB, 15);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mrNegative);
        addTarget(playerA, playerB);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 5);
        assertLife(playerA, 15);
        assertLife(playerB, 20);
    }

    @Test
    public void testMrNegativeNoDraw() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, mrNegative);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 7);
        setLife(playerB, 21);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mrNegative);
        addTarget(playerA, playerB);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertLife(playerA, 21);
        assertLife(playerB, 20);
    }
}