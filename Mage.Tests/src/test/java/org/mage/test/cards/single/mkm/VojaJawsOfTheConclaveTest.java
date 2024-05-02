package org.mage.test.cards.single.mkm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class VojaJawsOfTheConclaveTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.v.VojaJawsOfTheConclave Voja, Jaws of the Conclave} {2}{R}{G}{W}
     * Legendary Creature — Wolf
     * Vigilance, trample, ward {3}
     * Whenever Voja, Jaws of the Conclave attacks, put X +1/+1 counters on each creature you control, where X is the number of Elves you control. Draw a card for each Wolf you control.
     * 5/5
     */
    private static final String voja = "Voja, Jaws of the Conclave";

    @Test
    public void test_NoElves() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, voja);

        attack(1, playerA, voja, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, voja, 5, 5);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_2_Elves() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, voja);
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves", 2);

        attack(1, playerA, voja, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, voja, 5 + 2, 5 + 2);
        assertHandCount(playerA, 1);
    }
}
