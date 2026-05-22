package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class EsperOriginsTest extends CardTestPlayerBase {

    @Test
    public void testTransform() {
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Balduvian Bears");
        addCard(Zone.GRAVEYARD, playerA, "Esper Origins");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        setChoice(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Summon: Esper Maduin", 1);
        assertExileCount(playerA, 0);
    }

}
