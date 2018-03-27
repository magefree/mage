package org.mage.test.cards.cost.additional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PayXLifeTest extends CardTestPlayerBase {

    @Test
    public void testToxicDeluge() {
        addCard(Zone.HAND, playerA, "Toxic Deluge");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerB, "Serra Angel");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Toxic Deluge");
        setChoice(playerA, "X=4");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertLife(playerA, 16);
        assertGraveyardCount(playerB, "Serra Angel", 1);
    }
}
