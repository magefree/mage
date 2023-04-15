package org.mage.test.cards.single.neo;

import mage.cards.s.Storyweave;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class StoryweaveTest extends CardTestPlayerBase {
    private static final String fang = "Fang of Shigeki";
    private static final String colossus = "Nyxborn Colossus";
    private static final String intervention = "Fated Intervention";

    private void addEffectToGame() {
        // casting the spell is a pain to set up, this is easier
        addCustomCardWithAbility("tester", playerA, Storyweave.makeAbility());
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
    }

    @Test
    public void test__WorksOnlyOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        addCard(Zone.HAND, playerA, fang);
        addCard(Zone.HAND, playerA, colossus);

        addEffectToGame();
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fang, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, colossus);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, fang, CounterType.P1P1, 2);
        assertCounterCount(playerA, colossus, CounterType.P1P1, 0);
    }

    @Test
    public void test__MultipleOnlyOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, intervention);
        addCard(Zone.HAND, playerA, fang);

        addEffectToGame();
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, intervention, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, fang);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, fang, CounterType.P1P1, 0);
        assertPermanentCount(playerA, "Centaur Token", 2);
        Assert.assertTrue(currentGame
                .getBattlefield()
                .getAllActivePermanents()
                .stream()
                .filter(permanent -> "Centaur Token".equals(permanent.getName()))
                .noneMatch(permanent -> permanent.getCounters(currentGame).getCount(CounterType.P1P1) != 2));
    }

    @Test
    public void test__SingleOnlyOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, intervention);
        addCard(Zone.HAND, playerA, fang);

        addEffectToGame();
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fang);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, intervention);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, fang, CounterType.P1P1, 2);
        assertPermanentCount(playerA, "Centaur Token", 2);
        Assert.assertTrue(currentGame
                .getBattlefield()
                .getAllActivePermanents()
                .stream()
                .filter(permanent -> "Centaur Token".equals(permanent.getName()))
                .noneMatch(permanent -> permanent.getCounters(currentGame).getCount(CounterType.P1P1) != 0));
    }
}
