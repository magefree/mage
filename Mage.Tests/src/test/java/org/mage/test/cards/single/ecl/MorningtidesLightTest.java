package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MorningtidesLightTest extends CardTestPlayerBase {

    @Test
    public void testExiledCreaturesReturnTappedAtNextEndStep() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Morningtide's Light");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Morningtide's Light", "Silvercoat Lion^Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertTappedCount("Silvercoat Lion", true, 1);
        assertTappedCount("Grizzly Bears", true, 1);
    }
}
