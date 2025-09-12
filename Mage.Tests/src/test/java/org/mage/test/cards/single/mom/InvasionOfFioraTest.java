package org.mage.test.cards.single.mom;

import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;

public class InvasionOfFioraTest extends CardTestPlayerBase {

    private static final String invasion = "Invasion of Fiora";
    private static final String marchesa = "Marchesa, Resolute Monarch";
    // {4}{B}{B}
    // Battle — Siege
    // When Invasion of Fiora enters the battlefield, choose one or both --
    // * Destroy all legendary creatures.
    // * Destroy all nonlegendary creatures.
    //
    // Legendary Creature — Human Noble
    // 3/6
    // Menace, deathtouch
    // Whenever Marchesa, Resolute Monarch attacks, remove all counters from up to one target permanent.
    // At the beginning of your upkeep, if you haven't been dealt combat damage since your last turn, you draw a card and you lose 1 life.

    private static final String bruiser = "Bellowing Bruiser"; // (4/4) Haste
    private static final String emrakul = "Emrakul, the Promised End"; // (13/13) Legendary
    private static final String researcher = "Blood Researcher"; // (2/2) Menace, gets +1/+1 when controller gains life
    private static final String caress = "Lich's Caress";

    @Test
    public void testSiege() {
        skipInitShuffling();
        setStrictChooseMode(true);

        setLife(playerA, 20);

        addCard(Zone.HAND, playerA, invasion);
        addCard(Zone.BATTLEFIELD, playerA, bruiser);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        addCard(Zone.BATTLEFIELD, playerB, researcher, 1, true);
        addCard(Zone.BATTLEFIELD, playerB, emrakul);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 5);
        addCard(Zone.HAND, playerB, caress);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, invasion);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        setModeChoice(playerA, "1");
        setModeChoice(playerA, TestPlayer.MODE_SKIP);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        checkPermanentCount("Battle on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, invasion, 1);
        checkPermanentCount("Emrakul destroyed", 1, PhaseStep.PRECOMBAT_MAIN, playerB, emrakul, 0);

        attack(1, playerA, bruiser, invasion);

        setChoice(playerA, true); // yes to cast Marchesa

        checkPermanentCount("Marchesa on battlefield", 1, PhaseStep.END_TURN, playerA, marchesa, 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, caress, bruiser);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        // Researcher got +1/+1 counter from Caress's life gain
        checkPermanentCounters("Researcher gained one counter", 2, PhaseStep.BEGIN_COMBAT, playerB, researcher, CounterType.P1P1, 1);
        attack(2, playerB, researcher);

        checkLife("PlayerA life after turn 2", 2, PhaseStep.END_TURN, playerA, 17);
        checkHandCount("PlayerA hand after turn 2", 2, PhaseStep.END_TURN, playerA, 0);

        // Turn 3: No life loss nor extra card.
        checkHandCount("PlayerA hand after turn 3 draw", 3, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
        checkLife("PlayerA life after turn 3 draw", 3, PhaseStep.PRECOMBAT_MAIN, playerA, 17);

        // Marchesa attacks, remove counters from Researcher
        attack(3, playerA, marchesa);
        addTarget(playerA, researcher);
        checkPermanentCounters("Researcher lost its counter", 3, PhaseStep.END_COMBAT, playerB, researcher, CounterType.P1P1, 0);

        // Turn 4: B skips attack
        attackSkip(4, playerB);

        // Turn 5: Lose 1 life, draw extra card
        checkHandCount("PlayerA hand in turn 5 precombat main", 5, PhaseStep.PRECOMBAT_MAIN, playerA, 3);
        checkLife("PlayerA life in turn 5 precombat main", 5, PhaseStep.PRECOMBAT_MAIN, playerA, 16);

        setStopAt(5, PhaseStep.DECLARE_ATTACKERS);
        execute();
    }
}
