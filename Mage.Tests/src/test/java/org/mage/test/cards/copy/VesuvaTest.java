
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class VesuvaTest extends CardTestPlayerBase {

    /**
     * played a Vesuva on Glimmerpost and since when Vesuva becames Glimmerpost
     * it is already on the battlefield the ability won't trigger. Then when i
     * used Vesuva to copy an opponent's Dark Depth i got the land with the 10
     * counters.
     *
     * If Dark Depth says "it enters the battlefield with 10 counter" but Vesuva
     * is already on the field shouldnt have any counters or the problem was
     * that Glimmerpost should have trigger?
     */
    @Test
    public void testGlimmerpost() {
        // When Glimmerpost enters the battlefield, you gain 1 life for each Locus on the battlefield.
        // {T}: {1} Add.
        addCard(Zone.HAND, playerA, "Glimmerpost", 1);
        // You may have Vesuva enter the battlefield tapped as a copy of any land on the battlefield.
        addCard(Zone.HAND, playerA, "Vesuva", 1);

        addCard(Zone.HAND, playerB, "Glimmerpost", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glimmerpost");
        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Glimmerpost");
        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Vesuva");
        setChoice(playerA, "Glimmerpost");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Glimmerpost", 2);
        assertPermanentCount(playerB, "Glimmerpost", 1);

        assertLife(playerA, 24); // 20 + 1 + 3
        assertLife(playerB, 22); // 20 + 2
    }

    @Test
    public void testDarkDepth() {
        // Dark Depths enters the battlefield with ten ice counters on it.
        // {3}: Remove an ice counter from Dark Depths.
        // When Dark Depths has no ice counters on it, sacrifice it. If you do, put a legendary 20/20 black Avatar creature token with flying and "This creature is indestructible" named Marit Lage onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Dark Depths", 1);

        // You may have Vesuva enter the battlefield tapped as a copy of any land on the battlefield.
        addCard(Zone.HAND, playerA, "Vesuva", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vesuva");
        setChoice(playerA, "Dark Depths");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Dark Depths", 1);
        assertPermanentCount(playerA, "Dark Depths", 1);

        Permanent darkDepth = getPermanent("Dark Depths", playerA);
        if (darkDepth != null) {
            Assert.assertEquals(10, darkDepth.getCounters(currentGame).getCount(CounterType.ICE));
        }
        assertTappedCount("Dark Depths", true, 1);
    }

}
