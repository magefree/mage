package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TheMyriadPoolsTest extends CardTestPlayerBase {

    @Test
    public void castCopiesCorrectly() {
        addCard(Zone.BATTLEFIELD, playerA, "The Myriad Pools");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.HAND, playerA, "Flying Men");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flying Men");
        addTarget(playerA, "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "The Myriad Pools", 1);
        assertPermanentCount(playerA, "Memnite", 0);
        assertPermanentCount(playerA, "Flying Men", 2);
    }
}
