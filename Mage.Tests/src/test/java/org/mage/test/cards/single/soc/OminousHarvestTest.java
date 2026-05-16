package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class OminousHarvestTest extends CardTestPlayerBase {

    private static final String OMINOUS_HARVEST = "Ominous Harvest";

    @Test
    public void testGravestormCountsPermanentsPutIntoGraveyardBeforeCast() {
        addCard(Zone.HAND, playerA, OMINOUS_HARVEST);
        addCard(Zone.HAND, playerA, "Doom Blade");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        // One permanent goes from battlefield to graveyard before Ominous Harvest is cast.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, OMINOUS_HARVEST, playerB);
        addTarget(playerA, playerB); // new target for the gravestorm copy

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 18);
    }

    @Test
    public void testGravestormCountsPermanentsPutIntoGraveyardBeforeTriggerResolves() {
        addCard(Zone.HAND, playerA, OMINOUS_HARVEST);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");

        // The permanent dies while Ominous Harvest's gravestorm trigger is still on the stack.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, OMINOUS_HARVEST, playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Memnite", OMINOUS_HARVEST);
        addTarget(playerA, playerB); // new target for the gravestorm copy

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 18);
    }
}
