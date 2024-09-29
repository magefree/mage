package org.mage.test.cards.single.mkm;

import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AKillerAmongUsTest extends CardTestPlayerBase {

    @Test
    public void test_TargetChosenCreatureType() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "A Killer Among Us");

        // When A Killer Among Us enters the battlefield, create a 1/1 white Human creature token, a 1/1 blue Merfolk creature token, and a 1/1 red Goblin creature token. Then secretly choose Human, Merfolk, or Goblin.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A Killer Among Us");
        setChoice(playerA, "Merfolk");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkPermanentCount("human token", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Human Token", 1);
        checkPermanentCount("merfolk token", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Merfolk Token", 1);
        checkPermanentCount("goblin token", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Token", 1);

        attack(3, playerA, "Merfolk Token");
        // Sacrifice A Killer Among Us, Reveal the chosen creature type: If target attacking creature token is the chosen type, put three +1/+1 counters on it and it gains deathtouch until end of turn.
        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "Sacrifice", "Merfolk Token");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "A Killer Among Us", 0);
        assertCounterCount(playerA, "Merfolk Token", CounterType.P1P1, 3);
        assertAbility(playerA, "Merfolk Token", DeathtouchAbility.getInstance(), true);
    }

    @Test
    public void test_TargetNotChosenCreatureType() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "A Killer Among Us");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A Killer Among Us");
        setChoice(playerA, "Merfolk");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        checkPermanentCount("human token", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Human Token", 1);
        checkPermanentCount("merfolk token", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Merfolk Token", 1);
        checkPermanentCount("goblin token", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Token", 1);

        attack(3, playerA, "Human Token");
        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "Sacrifice", "Human Token");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Human Token", CounterType.P1P1, 0);
        assertAbility(playerA, "Human Token", DeathtouchAbility.getInstance(), false);
    }

    // If the A Killer Among Us ETB trigger is copied, the last chosen creature type
    // will be used for the second ability
    @Test
    public void test_CopyTrigger_TargetLastChosenCreatureType() {
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 5 + 2);
        addCard(Zone.BATTLEFIELD, playerA, "Lithoform Engine");
        addCard(Zone.HAND, playerA, "A Killer Among Us");
        addCard(Zone.HAND, playerA, "Tremor");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A Killer Among Us");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        // Copy A Killer Among Us ETB trigger
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}", "stack ability (When");
        setChoice(playerA, "Human");
        setChoice(playerA, "Merfolk");

        attack(3, playerA, "Merfolk Token");
        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "Sacrifice", "Merfolk Token");

        // Destroy all the other tokens
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Tremor");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Merfolk Token", CounterType.P1P1, 3);
        assertAbility(playerA, "Merfolk Token", DeathtouchAbility.getInstance(), true);
    }

    @Test
    public void test_CopyTrigger_TargetNotLastChosenCreatureType() {
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 5 + 2);
        addCard(Zone.BATTLEFIELD, playerA, "Lithoform Engine");
        addCard(Zone.HAND, playerA, "A Killer Among Us");
        addCard(Zone.HAND, playerA, "Tremor");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "A Killer Among Us");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        // Copy A Killer Among Us ETB trigger
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}", "stack ability (When");
        setChoice(playerA, "Human");
        setChoice(playerA, "Merfolk");

        attack(3, playerA, "Human Token");
        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "Sacrifice", "Human Token");

        // Destroy all the tokens
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Tremor");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Human Token", 0);
    }
}
