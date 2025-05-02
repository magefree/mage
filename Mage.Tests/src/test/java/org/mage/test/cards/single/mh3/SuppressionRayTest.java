package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SuppressionRayTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SuppressionRay Suppression Ray} {3}{W/U}{W/U}
     * Sorcery
     * Tap all creatures target player controls. You may pay X {E}, then choose up to X creatures tapped this way. Put a stun counter on each of them.
     * Orderly Plaza
     * Land
     * Orderly Plaza enters the battlefield tapped.
     * {T}: Add {W} or {U}.
     */
    private static final String ray = "Suppression Ray";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Zodiac Dog");
        addCard(Zone.BATTLEFIELD, playerB, "Zodiac Goat");
        addCard(Zone.BATTLEFIELD, playerB, "Zodiac Horse");
        addCard(Zone.BATTLEFIELD, playerB, "Zodiac Rabbit");
        addCard(Zone.BATTLEFIELD, playerB, "Zodiac Pig");
        addCard(Zone.HAND, playerA, "Suppression Ray");
        addCard(Zone.HAND, playerA, "Aethertide Whale"); // etb, you get 6 {E}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 11);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aethertide Whale", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Suppression Ray", playerB);
        setChoiceAmount(playerA, 3); // decide to pay 3 energy
        setChoice(playerA, "Zodiac Pig^Zodiac Rabbit"); // put stun on those 2 creatures

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped("Zodiac Dog", false);
        assertTapped("Zodiac Goat", true);
        assertTapped("Zodiac Horse", true);
        assertTapped("Zodiac Rabbit", true);
        assertTapped("Zodiac Pig", true);
        assertCounterCount(playerA, "Zodiac Dog", CounterType.STUN, 0);
        assertCounterCount(playerB, "Zodiac Goat", CounterType.STUN, 0);
        assertCounterCount(playerB, "Zodiac Horse", CounterType.STUN, 0);
        assertCounterCount(playerB, "Zodiac Rabbit", CounterType.STUN, 1);
        assertCounterCount(playerB, "Zodiac Pig", CounterType.STUN, 1);
        assertCounterCount(playerA, CounterType.ENERGY, 6 - 3);
    }
}
