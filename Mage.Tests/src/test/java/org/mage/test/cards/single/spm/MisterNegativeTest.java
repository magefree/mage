package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Jmlundeen
 */
public class MisterNegativeTest extends CardTestPlayerBase {

    /*
    Mister Negative
    {5}{W}{B}
    Legendary Creature - Human Villain
    Vigilance, lifelink
    When Mister Negative enters, you may exchange your life total with target opponent. If you lose life this way, draw that many cards.
    5/5
    */
    private static final String misterNegative = "Mister Negative";

    @Test
    public void testMisterNegative() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, misterNegative);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 7);
        setLife(playerB, 15);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, misterNegative);
        addTarget(playerA, playerB);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 5);
        assertLife(playerA, 15);
        assertLife(playerB, 20);
    }

    @Test
    public void testMisterNegativeNoDraw() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, misterNegative);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 7);
        setLife(playerB, 21);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, misterNegative);
        addTarget(playerA, playerB);
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertLife(playerA, 21);
        assertLife(playerB, 20);
    }
}
