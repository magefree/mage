package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class Vault87ForcedEvolutionTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.v.Vault87ForcedEvolution Vault 87: Forced Evolution} {3}{G}{U}
     * Enchantment — Saga
     * I — Gain control of target non-Mutant creature for as long as you control this Saga.
     * II — Put a +1/+1 counter on target creature you control. It becomes a Mutant in addition to its other types.
     * III — Draw cards equal to the greatest power among Mutants you control.
     */
    private static final String vault = "Vault 87: Forced Evolution";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, vault, 1);

        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vault);
        addTarget(playerA, "Memnite");

        checkPermanentCount("after I: Memnite control by A", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 1);

        // turn 3
        addTarget(playerA, "Memnite");

        // turn 5
        checkPermanentCount("before III: Memnite control by A", 5, PhaseStep.UPKEEP, playerA, "Memnite", 1);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Memnite", 1);
        assertCounterCount(playerB, "Memnite", CounterType.P1P1, 1);
        assertSubtype("Memnite", SubType.MUTANT);
        assertSubtype("Memnite", SubType.CONSTRUCT);
        assertHandCount(playerA, 4);
    }
}
