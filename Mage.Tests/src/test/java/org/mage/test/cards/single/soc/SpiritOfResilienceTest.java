package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SpiritOfResilienceTest extends CardTestPlayerBase {

    @Test
    public void testCopiesArtifactOrCreatureThatLeftGraveyard() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Spirit of Resilience");
        addCard(Zone.BATTLEFIELD, playerA, "Remorseful Cleric");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        addTarget(playerA, playerA);
        setChoice(playerA, true);
        setChoice(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
    }
}
