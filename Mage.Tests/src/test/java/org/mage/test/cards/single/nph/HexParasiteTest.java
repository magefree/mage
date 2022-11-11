package org.mage.test.cards.single.nph;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.h.HexParasite Hex Parasite}
 *
 * {X}{B/P}: Remove up to X counters from target permanent.
 *           For each counter removed this way, Hex Parasite gets +1/+0 until end of turn.
 *
 * @author Alex-Vasile
 */
public class HexParasiteTest extends CardTestPlayerBase {

    private static final String hexParasite = "Hex Parasite";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9446
     *      Removing 0 counters results in "Server's error: (minimum = value = maximum) is false"
     */
    @Test
    public void remove0Counters() {
        addCard(Zone.BATTLEFIELD, playerA, hexParasite);
        addCard(Zone.HAND, playerA, "Urza's Saga");

        setStrictChooseMode(true);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Urza's Saga");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{X}");
        setChoice(playerA, "X=0");
        setChoice(playerA, "Yes"); // Pay 2 life
        addTarget(playerA, "Urza's Saga");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertCounterCount(playerA, "Urza's Saga", CounterType.LORE, 1);
        assertLife(playerA, 20 - 2);
    }
}
