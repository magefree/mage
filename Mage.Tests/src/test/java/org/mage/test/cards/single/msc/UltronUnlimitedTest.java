package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class UltronUnlimitedTest extends CardTestPlayerBase {

    /**
     * Ultron, Unlimited {1}{U}{B}
     * Legendary Artifact Creature - Robot Villain 2/2
     * Flying
     * Whenever Ultron attacks, he connives.
     * Whenever a creature you control connives, you may pay {1}. If you do, create a 2/2 colorless Robot Villain
     * artifact creature token.
     */
    private static final String ultron = "Ultron, Unlimited";

    @Test
    public void test_Attack_ConnivesAndPayForRobot() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, ultron);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1); // for {1}
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // nonland card to discard
        addCard(Zone.LIBRARY, playerA, "Island"); // card to draw

        attack(1, playerA, ultron, playerB);
        setChoice(playerA, "Grizzly Bears"); // connive discard
        setChoice(playerA, true); // pay {1}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, ultron, CounterType.P1P1, 1);
        assertLife(playerB, 20 - 2 - 1); // ultron + counter
        assertPermanentCount(playerA, "Robot Villain Token", 1);
        assertTappedCount("Island", true, 1);
    }

    @Test
    public void test_OtherCreatureConnives_DontPay() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, ultron);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1); // for {1}
        //
        addCard(Zone.HAND, playerA, "Echo Inspector"); // {3}{U}, 2/3, connives on enter
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // nonland card to discard
        addCard(Zone.LIBRARY, playerA, "Island"); // card to draw

        // pay Echo Inspector by its own lands only, keep one Island for {1}
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Echo Inspector");
        setChoice(playerA, "Grizzly Bears"); // connive discard
        setChoice(playerA, false); // don't pay {1}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Echo Inspector", CounterType.P1P1, 1);
        assertPermanentCount(playerA, "Robot Villain Token", 0);
        assertTappedCount("Island", true, 4);
    }
}
