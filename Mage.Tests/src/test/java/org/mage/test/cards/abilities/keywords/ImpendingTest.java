package org.mage.test.cards.abilities.keywords;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ImpendingTest extends CardTestPlayerBase {

    private static final String hauntwoods = "Overlord of the Hauntwoods";

    @Test
    public void testCastRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, hauntwoods);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hauntwoods);
        setChoice(playerA, "Cast with no");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, hauntwoods, 1);
        assertType(hauntwoods, CardType.ENCHANTMENT, true);
        assertType(hauntwoods, CardType.CREATURE, true);
        assertSubtype(hauntwoods, SubType.AVATAR);
        assertSubtype(hauntwoods, SubType.HORROR);
        assertCounterCount(playerA, hauntwoods, CounterType.TIME, 0);
        assertPowerToughness(playerA, hauntwoods, 6, 5);

        assertPermanentCount(playerA, "Everywhere", 1);
    }

    @Test
    public void testCastImpending() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, hauntwoods);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hauntwoods);
        setChoice(playerA, "Cast with Impending");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, hauntwoods, 1);
        assertType(hauntwoods, CardType.ENCHANTMENT, true);
        assertType(hauntwoods, CardType.CREATURE, false);
        assertCounterCount(playerA, hauntwoods, CounterType.TIME, 4);

        assertPermanentCount(playerA, "Everywhere", 1);
    }
}
