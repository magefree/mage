package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ReadAheadTest extends CardTestPlayerBase {

    private static final String war = "The Elder Dragon War";

    @Test
    public void testElderDragonWarChapter1() {
        addCard(Zone.HAND, playerA, war);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, war);
        setChoice(playerA, "X=1");


        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20 - 2);
        assertPermanentCount(playerA, war, 1);
        assertGraveyardCount(playerA, war, 0);
    }

    @Test
    public void testElderDragonWarChapter2() {
        addCard(Zone.HAND, playerA, war);
        addCard(Zone.HAND, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, war);
        setChoice(playerA, "X=2");
        setChoice(playerA, "Island");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20);
        assertGraveyardCount(playerA, "Island", 1);
        assertHandCount(playerA, "Island", 0);
        assertPermanentCount(playerA, war, 1);
        assertGraveyardCount(playerA, war, 0);
    }

    @Test
    public void testElderDragonWarChapter3() {
        addCard(Zone.HAND, playerA, war);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, war);
        setChoice(playerA, "X=3");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Dragon Token", 1);
        assertPermanentCount(playerA, war, 0);
        assertGraveyardCount(playerA, war, 1);
    }
}
