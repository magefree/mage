package org.mage.test.cards.single.m3c;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class HourglassOfTheLostTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.h.HourglassOfTheLost HourglassOfTheLost} {2}{U}
     */
    private static final String hourglass = "Hourglass of the Lost";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, hourglass);
            addCard(Zone.GRAVEYARD, playerA, "Birds of Paradise"); // Tribal Enchantment
        
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertCounterCount(playerA, hourglass, CounterType.TIME,1);

        showAvailableAbilities("Abilities: ", 3, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Remove X");
        setChoice(playerA, "X=1");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        execute();
        assertExileCount(playerA, 1);
        assertGraveyardCount(playerA, 0);
        assertPermanentCount(playerA, "Birds of Paradise", 1);

    }
}