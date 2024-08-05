package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class MariposaMilitaryBaseTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.m.MariposaMilitaryBase Mariposa Military Base} {3}{R}{W}
     * Land
     * You may have Mariposa Military Base enter the battlefield tapped. If you do, you get two rad counters.
     * {T}: Add {C}.
     * {5}, {T}: Draw a card. This ability costs {1} less to activate for each rad counter you have.
     */
    private static final String mariposa = "Mariposa Military Base";

    @Test
    public void test_No_Radiation() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, mariposa);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, mariposa);
        setChoice(playerA, false); // Not tapped, no rad
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Swamp", true, 5);
        assertTappedCount(mariposa, true, 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_Radiation() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, mariposa);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, mariposa);
        setChoice(playerA, true); // Tapping, get 2 rad

        activateAbility(3, PhaseStep.UPKEEP, playerA, "{5}");

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertTappedCount("Swamp", true, 3);
        assertTappedCount(mariposa, true, 1);
        assertHandCount(playerA, 1 + 1); // 1 from normal draw, 1 from Mariposa
        assertCounterCount(playerA, CounterType.RAD, 2);
    }
}
