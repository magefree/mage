package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class VanguardOfTheRestlessGraveyardTest extends CardTestPlayerBase {

    @Test
    public void testCanPayWhenSpiritEntersToReturnFromGraveyard() {
        addCard(Zone.GRAVEYARD, playerA, "Vanguard of the Restless");
        addCard(Zone.HAND, playerA, "Selfless Spirit");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Selfless Spirit");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Selfless Spirit", 1);
        assertPermanentCount(playerA, "Vanguard of the Restless", 1);
        assertGraveyardCount(playerA, "Vanguard of the Restless", 0);
    }
}
