package org.mage.test.cards.single.afr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ProsperousInnkeeperTest extends CardTestPlayerBase {

    // When Prosperous Innkeeper enters the battlefield, create a Treasure token.
    // Whenever another creature enters the battlefield under your control, you gain 1 life.
    private final String innkeeper = "Prosperous Innkeeper";

    @Test
    public void createTreasureToken(){
        addCard(Zone.HAND, playerA, innkeeper);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, innkeeper);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPermanentCount(playerA, "Treasure Token", 1);
    }

    @Test
    public void gainLife(){
        addCard(Zone.HAND, playerA, innkeeper, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, innkeeper);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, innkeeper);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertLife(playerA, 21);
    }

}
