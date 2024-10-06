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
        addCard(Zone.GRAVEYARD, playerA, "Birds of Paradise"); // Mana value 1
        addCard(Zone.GRAVEYARD, playerA, "+2 Mace"); // Mana value 2
        
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertCounterCount(playerA, hourglass, CounterType.TIME,1);
        assertExileCount(playerA, 0);
        assertGraveyardCount(playerA, 2);

        setChoiceAmount(playerA, 1); // Remove 1 counter
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Remove X"); // Return all with mana value 1
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);

        execute();
        assertExileCount(playerA, 1);
        assertGraveyardCount(playerA, 1); // +2 Mace still in graveyard
        assertPermanentCount(playerA, "Birds of Paradise", 1);

    }
}