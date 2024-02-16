package org.mage.test.cards.single.ltc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */

public class GilraenDunedainProtectorTest extends CardTestPlayerBase {

    /**
     * Gilraen, Dúnedain Protector
     * {2}{W}
     * Legendary Creature — Human Noble
     * <p>
     * {2}, {T}: Exile another target creature you control. You may return that card to the battlefield under its owner’s control. If you don’t, at the beginning of the next end step, return that card to the battlefield under its owner’s control with a vigilance counter and a lifelink counter on it.
     */
    private final String gilraen = "Gilraen, Dunedain Protector";

    /**
     * Ajani's Welcome
     * {W}
     * Enchantment
     * <p>
     * Whenever a creature enters the battlefield under your control, you gain 1 life.
     */
    private final String welcome = "Ajani's Welcome";

    @Test
    public void ReturnNow() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, gilraen);
        addCard(Zone.BATTLEFIELD, playerA, welcome);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Exile another target creature you control", "Silvercoat Lion");
        setChoice(playerA, true); // return now.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        // No counter.
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.VIGILANCE, 0);
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.LIFELINK, 0);

        // Lion did re-enter the battlefield.
        assertLife(playerA, 20 + 1);
    }

    @Test
    public void ReturnLater() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, gilraen);
        addCard(Zone.BATTLEFIELD, playerA, welcome);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Exile another target creature you control", "Silvercoat Lion");
        setChoice(playerA, false); // return at end of turn.

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 0);

        // Lion did not re-enter the battlefield.
        assertLife(playerA, 20);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        // With counters
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.VIGILANCE, 1);
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.LIFELINK, 1);

        // Lion did re-enter the battlefield.
        assertLife(playerA, 20 + 1);
    }
}
