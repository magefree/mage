package org.mage.test.cards.single.msh;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class BeastEruditeAerialistTest extends CardTestPlayerBase {

    // As long as you've put one or more +1/+1 counters on Beast this turn, he has flying.
    private static final String beast = "Beast, Erudite Aerialist";

    @Test
    public void test_CounterOnBeast_GainsFlyingThisTurnOnly() {
        addCard(Zone.BATTLEFIELD, playerA, beast);
        // Put a +1/+1 counter on target creature.
        addCard(Zone.HAND, playerA, "Battlegrowth"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battlegrowth", beast);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkAbility("flying after the counter", 1, PhaseStep.PRECOMBAT_MAIN, playerA, beast, FlyingAbility.class, true);
        checkAbility("no flying next turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, beast, FlyingAbility.class, false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, beast, CounterType.P1P1, 1);
    }

    @Test
    public void test_CounterOnAnotherCreature_NoFlying() {
        addCard(Zone.BATTLEFIELD, playerA, beast);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Battlegrowth"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battlegrowth", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, beast, FlyingAbility.getInstance(), false);
    }

    @Test
    public void test_CopyOfBeast_GainsFlying() {
        // the watcher is shared by all Beasts, a copy must also get flying
        addCard(Zone.BATTLEFIELD, playerA, beast);
        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
        addCard(Zone.HAND, playerB, "Clone"); // {3}{U}
        addCard(Zone.HAND, playerB, "Battlegrowth"); // {G}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {U}", 4);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, true);
        setChoice(playerB, beast);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Battlegrowth", beast + "[only copy]");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerB, beast, CounterType.P1P1, 1);
        assertAbility(playerB, beast, FlyingAbility.getInstance(), true);
        assertAbility(playerA, beast, FlyingAbility.getInstance(), false);
    }
}
