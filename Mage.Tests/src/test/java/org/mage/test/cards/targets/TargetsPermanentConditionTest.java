package org.mage.test.cards.targets;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */

public class TargetsPermanentConditionTest extends CardTestPlayerBase {

    private static final String town = "This Town Ain't Big Enough";
    private static final String piker = "Goblin Piker";
    private static final String ghoul = "Warpath Ghoul";
    private static final String wurm = "Craw Wurm";

    // Reported bug: This Town Ain't Big Enough cost reduction doesn't consider the second targeted permanent

    /* This Town Ain't Big Enough {4}{U} Instant
     * This spell costs {3} less to cast if it targets a permanent you control.
     * Return up to two target nonland permanents to their owners’ hands.
     */

    @Test
    public void testThisTownAintBigEnough1() {
        testThisTownAintBigEnoughBase(true);
    }

    @Test
    public void testThisTownAintBigEnough2() {
        testThisTownAintBigEnoughBase(false);
    }

    private void testThisTownAintBigEnoughBase(boolean targetOrder) {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, town);
        addCard(Zone.BATTLEFIELD, playerA, piker);
        addCard(Zone.BATTLEFIELD, playerB, ghoul);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, town);
        if (targetOrder) {
            addTarget(playerA, piker + "^" + ghoul);
        } else {
            addTarget(playerA, ghoul + "^" + piker);
        }

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, town, 1);
        assertHandCount(playerA, piker, 1);
        assertHandCount(playerB, ghoul, 1);
    }

    @Test
    public void testThisTownAintBigEnoughNoReduce() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, town);
        addCard(Zone.BATTLEFIELD, playerA, piker);
        addCard(Zone.BATTLEFIELD, playerB, ghoul);
        addCard(Zone.BATTLEFIELD, playerB, wurm);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, town);
        addTarget(playerA, ghoul + "^" + wurm);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, town, 1);
        assertTappedCount("Island", true, 5);
        assertHandCount(playerB, ghoul, 1);
        assertHandCount(playerB, wurm, 1);
    }

}
