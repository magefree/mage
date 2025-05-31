package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TrialOfATimeLordTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TrialOfATimeLord Trial of a Time Lord} {1}{W}{W}
     * Enchantment — Saga
     * (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
     * I, II, III — Exile target nontoken creature an opponent controls until this Saga leaves the battlefield.
     * IV — Starting with you, each player votes for innocent or guilty. If guilty gets more votes, the owner of each card exiled with this Saga puts that card on the bottom of their library.
     */
    private static final String trial = "Trial of a Time Lord";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, trial, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Bear Cub", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, trial);
        addTarget(playerA, "Memnite");

        checkExileCount("T1: Memnite exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Memnite", 1);
        checkExileCount("T1: Grizzly Bears not exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", 0);
        checkExileCount("T1: Bear Cub not exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Bear Cub", 0);

        // turn 3
        addTarget(playerA, "Grizzly Bears");

        checkExileCount("T3: Memnite exiled", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Memnite", 1);
        checkExileCount("T3: Grizzly Bears exiled", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", 1);
        checkExileCount("T3: Bear Cub not exiled", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Bear Cub", 0);

        // turn 5
        addTarget(playerA, "Bear Cub");

        checkExileCount("T5: Memnite exiled", 5, PhaseStep.POSTCOMBAT_MAIN, playerB, "Memnite", 1);
        checkExileCount("T5: Grizzly Bears exiled", 5, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", 1);
        checkExileCount("T5: Bear Cub exiled", 5, PhaseStep.POSTCOMBAT_MAIN, playerB, "Bear Cub", 1);

        // turn 7
        setChoice(playerA, true); // Innocent
        setChoice(playerB, false); // Guilty

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Memnite", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Bear Cub", 1);
    }
}
