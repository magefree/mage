package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TheAkroanWarTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TheAkroanWar The Akroan War} {3}{R}
     * Enchantment — Saga
     * I — Gain control of target creature for as long as this Saga remains on the battlefield.
     * II — Until your next turn, creatures your opponents control attack each combat if able.
     * III — Each tapped creature deals damage to itself equal to its power.
     */
    private static final String war = "The Akroan War";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, war, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, war);
        addTarget(playerA, "Grizzly Bears");

        checkPermanentCount("after I, Bears under A control", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        checkPermanentCount("T3: Bears still in control ", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkPermanentCount("T3: Bears still in control ", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);
        checkPermanentCount("T4: Bears still in control ", 4, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        // turn 4 -- mandatory attack after II, so no need to declare
        //attack(4, playerB, "Memnite", playerA);

        // turn 5
        checkPermanentCount("before III, Bears still in control ", 5, PhaseStep.UPKEEP, playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 1);
        assertGraveyardCount(playerA, war, 1);
        assertGraveyardCount(playerB, "Memnite", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
    }
}
