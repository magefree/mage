package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.d.DoseOfDawnglow Dose of Dawnglow} {4}{B}
 * Instant
 * Return target creature card from your graveyard to the battlefield. Then if it isn't your main phase, blight 2.
 */
public class DoseOfDawnglowTest extends CardTestPlayerBase {

    private static final String dose = "Dose of Dawnglow";
    private static final String bears = "Grizzly Bears"; // the reanimation target.
    private static final String giant = "Hill Giant";   // 3/3 creature to survive blight 2.

    @Test
    public void test_BlightOutsideMainPhase() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, dose);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.GRAVEYARD, playerA, bears);          // to reanimate
        addCard(Zone.BATTLEFIELD, playerA, giant);        // to blight

        castSpell(1, PhaseStep.UPKEEP, playerA, dose, bears);
        setChoice(playerA, giant);                       // blight target (a choose, not a target)

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // The reanimated creature returned to the battlefield.
        assertPermanentCount(playerA, bears, 1);
        assertGraveyardCount(playerA, bears, 0);

        // The blight put two -1/-1 counters on the chosen creature (3/3 -> 1/1).
        assertPermanentCount(playerA, giant, 1);
        assertCounterCount(playerA, giant, CounterType.M1M1, 2);
        assertPowerToughness(playerA, giant, 1, 1);
    }

    /**
     * Cast during your own main phase. The spell returns the creature but does
     * NOT blight (it IS your main phase), so no counters are placed.
     */
    @Test
    public void test_NoBlightDuringMainPhase() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, dose);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.GRAVEYARD, playerA, bears);          // to reanimate
        addCard(Zone.BATTLEFIELD, playerA, giant);        // to not blight

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dose, bears);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // The reanimated creature returned to the battlefield.
        assertPermanentCount(playerA, bears, 1);
        assertGraveyardCount(playerA, bears, 0);
        
        // No blight during the main phase: no counters on the creature.
        assertCounterCount(playerA, giant, CounterType.M1M1, 0);
        assertPowerToughness(playerA, giant, 3, 3);
    }
}
