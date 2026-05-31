package org.mage.test.cards.abilities.oneshot.library;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SearchTest extends CardTestPlayerBase {

    @Test
    public void testSearchDifferentNames() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Emergent Ultimatum");
        addCard(Zone.LIBRARY, playerA, "Formidable Speaker@1");
        addCard(Zone.LIBRARY, playerA, "Formidable Speaker@2");
        addCard(Zone.LIBRARY, playerA, "Pore Over the Pages");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emergent Ultimatum");
        addTarget(playerA, "Pore Over the Pages");
        addTarget(playerA, "@1");
        setChoice(playerA, playerB.getName());
        setChoice(playerB, "Pore Over the Pages");
        setChoice(playerA, false);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertExileCount(playerA, "Formidable Speaker", 1);
    }

}
