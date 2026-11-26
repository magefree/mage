package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class CosimaGodOfTheVoyageTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.CosimaGodOfTheVoyage}
     * <p>
     * {2}{U} Legendary Creature - God 2/4<br>
     * At the beginning of your upkeep, you may exile Cosima. If you do, it gains "Whenever a land you control enters,
     * if Cosima is exiled, you may put a voyage counter on it. If you don't, return Cosima to the battlefield with
     * X +1/+1 counters on it and draw X cards, where X is the number of voyage counters on it."<br>
     * //<br>
     * {1}{U} Legendary Artifact - Vehicle 3/3 (The Omenkeel)
     */
    private static final String COSIMA = "Cosima, God of the Voyage";

    @Test
    public void test_ExileAndReturnWithCounters() {
        addCard(Zone.BATTLEFIELD, playerA, COSIMA);
        //
        addCard(Zone.HAND, playerA, "Island", 2); // lands for the voyage trigger

        setChoice(playerA, true); // exile Cosima on upkeep of turn 1
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");
        setChoice(playerA, true); // put a voyage counter

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");
        setChoice(playerA, false); // don't put a voyage counter, so return to the battlefield

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, COSIMA, 1);
        assertCounterCount(playerA, COSIMA, CounterType.P1P1, 1);
        assertPowerToughness(playerA, COSIMA, 2 + 1, 4 + 1);
        assertHandCount(playerA, 2); // draw of turn 3 + one card by the voyage counter
    }
}
