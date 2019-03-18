package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DackFaydenTest extends CardTestPlayerBase {

    @Test
    public void testDackFaydenEmblem() {

        addCard(Zone.BATTLEFIELD, playerA, "Dack Fayden");

        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");

        addCard(Zone.HAND, playerA, "Gut Shot");


        for(int i = 1; i < 8; i+= 2) {
            activateAbility(i, PhaseStep.PRECOMBAT_MAIN, playerA,"+1: Target player draws two cards, then discards two cards.", playerB);
        }

        activateAbility(9, PhaseStep.PRECOMBAT_MAIN, playerA,
                "-6: You get an emblem with \"Whenever you cast a spell that targets one or more permanents, gain control of those permanents.\"");

        castSpell(10, PhaseStep.PRECOMBAT_MAIN, playerA, "Gut Shot", "Ornithopter");

        setStopAt(10, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);

        assertCounterCount("Dack Fayden", CounterType.LOYALTY, 1);
        assertPermanentCount(playerA, "Ornithopter", 1);
    }

    //Ensure that if a permanent moves to a different zone and comes back,
    //it won't still be under the controller of Dack's Emblem.
    @Test
    public void testDackFaydenEmblemAcrossZones() {
        addCard(Zone.BATTLEFIELD, playerA, "Dack Fayden");

        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");

        addCard(Zone.HAND, playerA, "Gut Shot");

        addCard(Zone.HAND, playerA, "Unsummon");
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        for(int i = 1; i < 8; i+= 2) {
            activateAbility(i, PhaseStep.PRECOMBAT_MAIN, playerA,"+1: Target player draws two cards, then discards two cards.", playerB);
        }

        activateAbility(9, PhaseStep.PRECOMBAT_MAIN, playerA,
                "-6: You get an emblem with \"Whenever you cast a spell that targets one or more permanents, gain control of those permanents.\"");

        castSpell(10, PhaseStep.PRECOMBAT_MAIN, playerA, "Gut Shot", "Ornithopter");
        castSpell(10, PhaseStep.PRECOMBAT_MAIN, playerA,  "Unsummon", "Ornithopter", "Gut Shot", StackClause.WHILE_NOT_ON_STACK);
        castSpell(10, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ornithopter");

        setStopAt(10, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);

        assertCounterCount("Dack Fayden", CounterType.LOYALTY, 1);
        assertPermanentCount(playerB, "Ornithopter", 1);
    }
}
