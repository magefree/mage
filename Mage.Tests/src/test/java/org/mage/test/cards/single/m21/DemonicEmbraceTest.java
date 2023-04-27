package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DemonicEmbraceTest extends CardTestPlayerBase {

    @Test
    public void playFromGraveyard() {
        // Enchanted creature gets +3/+1, has flying, and is a Demon in addition to its other types.
        // You may cast Demonic Embrace from your graveyard by paying 3 life and discarding a card in addition to paying its other costs.
        addCard(Zone.HAND, playerA, "Mountain");
        addCard(Zone.GRAVEYARD, playerA, "Demonic Embrace");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Embrace", "Grizzly Bears");
        setChoice(playerA, "Mountain");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Demonic Embrace", 1);
        assertPowerToughness(playerA, "Grizzly Bears", 5, 3);
        assertGraveyardCount(playerA, "Mountain", 1);
        assertLife(playerA, 20 - 3);
    }
}
