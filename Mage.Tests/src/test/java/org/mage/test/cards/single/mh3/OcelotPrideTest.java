package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class OcelotPrideTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.o.OcelotPride Ocelot Pride} {W}
     * Creature — Cat
     * First strike, lifelink
     * Ascend
     * At the beginning of your end step, if you gained life this turn, create a 1/1 white Cat creature token. Then if you have the city's blessing, for each token you control that entered the battlefield this turn, create a token that's a copy of it.
     */
    private static final String ocelot = "Ocelot Pride";

    @Test
    public void test_No_Trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ocelot);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, 1);
    }

    @Test
    public void test_Trigger_NoCitysBlessing() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ocelot);
        addCard(Zone.HAND, playerA, "Hard Evidence");
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hard Evidence");

        attack(1, playerA, ocelot, playerB);

        checkPermanentCount("1: before trigger, 1 Crab", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Crab Token", 1);
        checkPermanentCount("1: before trigger, 0 Cat", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cat Token", 0);
        checkPermanentCount("1: before trigger, 1 Clue", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Clue Token", 1);

        attack(3, playerA, ocelot, playerB);

        checkPermanentCount("3: before trigger, 1 Crab", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Crab Token", 1);
        checkPermanentCount("3: before trigger, 1 Cat", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cat Token", 1);
        checkPermanentCount("3: before trigger, 1 Clue", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Clue Token", 1);

        setStopAt(4, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, 4 + 1 + 1);
        assertPermanentCount(playerA, "Crab Token", 1);
        assertPermanentCount(playerA, "Cat Token", 2); // 1 per trigger
        assertPermanentCount(playerA, "Clue Token", 1);
    }

    @Test
    public void test_Trigger_CitysBlessing() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ocelot);
        addCard(Zone.HAND, playerA, "Hard Evidence");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hard Evidence");

        attack(1, playerA, ocelot, playerB);

        checkPermanentCount("1: before trigger, 1 Crab", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Crab Token", 1);
        checkPermanentCount("1: before trigger, 0 Cat", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cat Token", 0);
        checkPermanentCount("1: before trigger, 1 Clue", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Clue Token", 1);

        attack(3, playerA, ocelot, playerB);

        checkPermanentCount("3: before trigger, 2 Crab", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Crab Token", 2);
        checkPermanentCount("3: before trigger, 2 Cat", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cat Token", 2);
        checkPermanentCount("3: before trigger, 2 Clue", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Clue Token", 2);

        setStopAt(4, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, (6 + 1) + 2 + 4 + 2);
        assertPermanentCount(playerA, "Crab Token", 1 + 1); // 1 by Hard Evidence, 1 on trigger
        assertPermanentCount(playerA, "Cat Token", 4); // 2 per trigger
        assertPermanentCount(playerA, "Clue Token", 1 + 1); // 1 by Hard Evidence, 1 on trigger
    }
}
