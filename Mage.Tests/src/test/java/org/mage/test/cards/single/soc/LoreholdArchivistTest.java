package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LoreholdArchivistTest extends CardTestPlayerBase {

    @Test
    public void testThreeArtifactOrCreatureCardsPrepareRestoreRelic() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Lorehold Archivist");
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 4);
        addCard(Zone.GRAVEYARD, playerA, "Memnite");
        addCard(Zone.GRAVEYARD, playerA, "Ornithopter");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Restore Relic");
        addTarget(playerA, "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Memnite", 1);
        assertPermanentCount(playerA, "Memnite", 1);
        assertGraveyardCount(playerA, "Restore Relic", 0);
    }
}
