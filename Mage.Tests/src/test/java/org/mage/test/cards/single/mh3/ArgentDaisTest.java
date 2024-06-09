package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ArgentDaisTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.ArgentDais Argent Dais} {1}{W}
     * Artifact
     * Argent Dais enters the battlefield with two oil counters on it.
     * Whenever two or more creatures attack, put an oil counter on Argent Dais.
     * {2}, {T}, Remove two oil counters from Argent Dais: Exile another target nonland permanent. Its controller draws two cards.
     */
    private static final String dais = "Argent Dais";

    @Test
    public void test_Attack_One_No_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, dais);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        attack(1, playerA, "Grizzly Bears", playerB);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
        assertCounterCount(playerA, dais, CounterType.OIL, 2);
    }

    @Test
    public void test_Attack_Two_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, dais);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        attack(1, playerA, "Grizzly Bears", playerB);
        attack(1, playerA, "Memnite", playerB);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2 - 1);
        assertCounterCount(playerA, dais, CounterType.OIL, 2 + 1);
    }

    @Test
    public void test_Attack_Two_OtherPlayer_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, dais);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        attack(1, playerA, "Grizzly Bears", playerB);
        attack(1, playerA, "Memnite", playerB);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2 - 1);
        assertCounterCount(playerB, dais, CounterType.OIL, 2 + 1);
    }

    @Test
    public void test_Activate_On_Own_Creature() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, dais);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}", "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, 2);
    }

    @Test
    public void test_Activate_On_Opponent_Creature() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, dais);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}", "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerB, 2);
    }
}
