package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ValiantTest extends CardTestPlayerBase {

    private static final String hero = "Heartfire Hero";
    private static final String growth = "Giant Growth";

    @Test
    public void opponentsSpellDoesNotUseUpValiant() {
        addCard(Zone.BATTLEFIELD, playerA, hero);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, growth);
        addCard(Zone.BATTLEFIELD, playerB, "Forest");
        addCard(Zone.HAND, playerB, growth);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, growth, hero);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, growth, hero);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, growth, 1);
        assertGraveyardCount(playerB, growth, 1);
        assertCounterCount(playerA, hero, CounterType.P1P1, 1);
    }

    @Test
    public void controllersFirstSpellTriggersValiant() {
        addCard(Zone.BATTLEFIELD, playerA, hero);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, growth);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, growth, hero);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, growth, 1);
        assertCounterCount(playerA, hero, CounterType.P1P1, 1);
    }

    @Test
    public void opponentsAbilityDoesNotUseUpValiant() {
        addCard(Zone.BATTLEFIELD, playerA, hero);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, growth);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Icy Manipulator");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}, {T}: Tap target", hero);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, growth, hero);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(hero, true);
        assertGraveyardCount(playerA, growth, 1);
        assertCounterCount(playerA, hero, CounterType.P1P1, 1);
    }

    @Test
    public void controllersSecondSpellDoesNotTriggerValiant() {
        addCard(Zone.BATTLEFIELD, playerA, hero);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, growth, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, growth, hero);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, growth, hero);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, growth, 2);
        assertCounterCount(playerA, hero, CounterType.P1P1, 1);
    }

    @Test
    public void opponentsSpellDoesNotTriggerValiant() {
        addCard(Zone.BATTLEFIELD, playerA, hero);
        addCard(Zone.BATTLEFIELD, playerB, "Forest");
        addCard(Zone.HAND, playerB, growth);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, growth, hero);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, growth, 1);
        assertCounterCount(playerA, hero, CounterType.P1P1, 0);
    }

    @Test
    public void valiantTriggersAgainNextTurn() {
        addCard(Zone.BATTLEFIELD, playerA, hero);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, growth, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, growth, hero);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, growth, hero);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, growth, 2);
        assertCounterCount(playerA, hero, CounterType.P1P1, 2);
    }

    @Test
    public void earlierTargetingCountsAfterGainingControl() {
        addCard(Zone.BATTLEFIELD, playerB, hero);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, growth, 2);
        addCard(Zone.HAND, playerA, "Kitnap");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, growth, hero);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        setChoice(playerA, false); // Don't promise the gift.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kitnap", hero);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, growth, hero);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, hero, 1);
        assertGraveyardCount(playerA, growth, 2);
        assertCounterCount(playerA, hero, CounterType.P1P1, 0);
    }

    @Test
    public void previousControllersTargetingDoesNotUseUpValiant() {
        addCard(Zone.BATTLEFIELD, playerB, hero);
        addCard(Zone.BATTLEFIELD, playerB, "Forest");
        addCard(Zone.HAND, playerB, growth);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Insurrection");
        addCard(Zone.HAND, playerA, growth);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, growth, hero);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Insurrection");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, growth, hero);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, hero, 1);
        assertGraveyardCount(playerA, growth, 1);
        assertGraveyardCount(playerB, growth, 1);
        assertCounterCount(playerA, hero, CounterType.P1P1, 2);
    }
}
