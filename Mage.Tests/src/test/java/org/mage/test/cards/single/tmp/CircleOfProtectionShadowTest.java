package org.mage.test.cards.single.tmp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class CircleOfProtectionShadowTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.CircleOfProtectionShadow Circle of Protection: Shadow} {1}{W}
     * Enchantment
     * {1}: The next time a creature of your choice with shadow would deal damage to you this turn, prevent that damage.
     */
    private static final String circle = "Circle of Protection: Shadow";

    @Test
    public void test_NotShadow_NoPrevent() {
        addCard(Zone.BATTLEFIELD, playerA, circle, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}");
        // no creature with shadow to choose

        attack(2, playerB, "Goblin Piker", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 2);
        assertTappedCount("Plains", true, 1);
    }

    @Test
    public void test_Shadow_Prevent() {
        addCard(Zone.BATTLEFIELD, playerA, circle, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Dauthi Marauder", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}");
        setChoice(playerA, "Dauthi Marauder");

        attack(2, playerB, "Dauthi Marauder", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertTappedCount("Plains", true, 1);
    }
}
