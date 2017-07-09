package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.VigilanceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class EternalizeTest extends CardTestPlayerBase {

    private String sentinel = "Steadfast Sentinel";

    @Test
    public void testEternalize() {
        addCard(Zone.GRAVEYARD, playerA, sentinel, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eternalize");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPermanentCount(playerA, sentinel, 1);
        assertPowerToughness(playerA, sentinel, 4, 4);
        assertAbility(playerA, sentinel, VigilanceAbility.getInstance(), true);
    }
}
