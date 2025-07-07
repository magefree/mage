package org.mage.test.cards.single.cmm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BattleAtTheHelvaultTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BattleAtTheHelvault Battle at the Helvault} {4}{W}{W}
     * Enchantment — Saga
     * I, II — For each player, exile up to one target non-Saga, nonland permanent that player controls until this Saga leaves the battlefield.
     * III — Create Avacyn, a legendary 8/8 white Angel creature token with flying, vigilance, and indestructible.
     */
    private static final String battle = "Battle at the Helvault";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, battle, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Augmenting Automaton", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Bear Cub", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, battle);
        addTarget(playerA, "Memnite");
        addTarget(playerA, "Grizzly Bears");

        checkExileCount("T1: Memnite exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 1);
        checkExileCount("T1: Augmenting Automaton not exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Augmenting Automaton", 0);
        checkExileCount("T1: Grizzly Bears exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", 1);
        checkExileCount("T1: Bear Cub not exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Bear Cub", 0);

        // turn 3
        addTarget(playerA, "Augmenting Automaton");
        addTarget(playerA, "Bear Cub");

        checkExileCount("T3: Memnite exiled", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 1);
        checkExileCount("T3: Augmenting Automaton exiled", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Augmenting Automaton", 1);
        checkExileCount("T3: Grizzly Bears exiled", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", 1);
        checkExileCount("T3: Bear Cub exiled", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Bear Cub", 1);

        // turn 5

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Memnite", 1);
        assertPermanentCount(playerA, "Augmenting Automaton", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Bear Cub", 1);
        assertPermanentCount(playerA, "Avacyn", 1);
    }
}
