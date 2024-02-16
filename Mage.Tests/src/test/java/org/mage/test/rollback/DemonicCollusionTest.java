package org.mage.test.rollback;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class DemonicCollusionTest extends CardTestPlayerBase {

    // https://github.com/magefree/mage/issues/5835

    // Demonic Collusion {3}{B}{B}
    // Buyback—Discard two cards. (You may discard two cards in addition to any other costs as you cast this spell.
    // If you do, put this card into your hand as it resolves.)

    // Search your library for a card and put that card into your hand. Then shuffle your library.

    @Test
    public void test_BuybackNormal() {
        addCard(Zone.HAND, playerA, "Demonic Collusion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Forest", 2);

        checkHandCardCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Collusion", 1);
        checkHandCardCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest", 2);

        // cast with buyback
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Collusion");
        setChoice(playerA, true); // use buyback
        setChoice(playerA, "Forest"); // pay
        setChoice(playerA, "Forest"); // pay
        addTarget(playerA, "Mountain"); // return from lib

        checkHandCardCount("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Demonic Collusion", 1);
        checkHandCardCount("after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Forest", 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_BuybackAfterRollback() {
        addCard(Zone.HAND, playerA, "Demonic Collusion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Forest", 2);

        // turn 1
        checkHandCardCount("before roll", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Collusion", 1);
        checkHandCardCount("before roll", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest", 2);

        // turn 3 - rollback at the end (to the start)
        rollbackTurns(3, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        // turn 5 - check
        checkHandCardCount("after roll", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Collusion", 1);
        checkHandCardCount("after roll", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest", 2);

        // cast with buyback
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Collusion");
        setChoice(playerA, true); // use buyback
        setChoice(playerA, "Forest"); // pay
        setChoice(playerA, "Forest"); // pay
        addTarget(playerA, "Mountain"); // return from lib

        checkHandCardCount("after buy", 5, PhaseStep.BEGIN_COMBAT, playerA, "Demonic Collusion", 1);
        checkHandCardCount("after buy", 5, PhaseStep.BEGIN_COMBAT, playerA, "Forest", 0);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();
    }
}
