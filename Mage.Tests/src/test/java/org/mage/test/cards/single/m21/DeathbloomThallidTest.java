package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DeathbloomThallidTest extends CardTestPlayerBase {

    @Test
    public void diesTrigger() {
        // When Deathbloom Thallid dies, create a 1/1 green Saproling creature token.
        addCard(Zone.BATTLEFIELD, playerA, "Deathbloom Thallid");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Deathbloom Thallid");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Saproling Token", 1);

    }
}
