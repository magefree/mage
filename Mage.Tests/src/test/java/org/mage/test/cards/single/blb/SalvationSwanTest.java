package org.mage.test.cards.single.blb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SalvationSwanTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SalvationSwan Salvation Swan} {3}{W}
     * <p>
     * Creature — Bird Cleric
     * Flash
     * Flying
     * Whenever Salvation Swan or another Bird you control enters, exile up to one target creature you control without flying. Return it to the battlefield under its owner’s control with a flying counter on it at the beginning of the next end step.
     * 3/3
     */
    private static final String swan = "Salvation Swan";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, swan);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, swan);
        addTarget(playerA, "Grizzly Bears");

        checkExileCount("Bears in exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.FLYING, 1);
    }
}
