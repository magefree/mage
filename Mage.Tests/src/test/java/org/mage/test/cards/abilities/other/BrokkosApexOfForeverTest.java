package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BrokkosApexOfForeverTest extends CardTestPlayerBase {
    @Test
    public void testBrokkosApexOfForeverFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Grumgully, the Generous");
        addCard(Zone.GRAVEYARD, playerA, "Brokkos, Apex of Forever");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
