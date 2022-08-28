package org.mage.test.cards.single.tsp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by Alexsandr0x.
 */
public class HivestoneTest extends CardTestPlayerBase {

    /**
     * If a creature is already a Sliver, Hivestone has no effect on it.
     */
    @Test
    public void abilityCheckTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1); // Creature {1}{G}
        // Creatures you control are Slivers in addition to their other creature types.
        addCard(Zone.HAND, playerA, "Hivestone", 1); // Artifact {2}

        // All Sliver creatures get +1/+1.
        addCard(Zone.BATTLEFIELD, playerA, "Muscle Sliver", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear", 1); // Creature 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hivestone");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Hivestone", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, "Grizzly Bears", 3, 3, Filter.ComparisonScope.Any);
        assertPowerToughness(playerB, "Runeclaw Bear", 2, 2, Filter.ComparisonScope.Any);

    }

    /**
     * Turns only your creatures on the battlefield, not in other zones, into
     * Slivers. It won't allow you to have Root Sliver on the battlefield and
     * make your Grizzly Bears uncounterable, for example.
     */
    @Test
    public void rootSliverTest() {
        addCard(Zone.HAND, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Hivestone", 1);
        // Root Sliver can't be countered. Sliver spells can't be countered.
        addCard(Zone.BATTLEFIELD, playerA, "Root Sliver", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Grizzly Bears", "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, 1);
    }
}
