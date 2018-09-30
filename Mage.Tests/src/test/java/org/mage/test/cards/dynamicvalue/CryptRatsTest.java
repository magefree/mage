package org.mage.test.cards.dynamicvalue;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CryptRatsTest extends CardTestPlayerBase {

    String cRats = "Crypt Rats";

    @Test
    public void damageOnlyCreatureAndPlayers() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.BATTLEFIELD, playerA, cRats, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Shivan Dragon", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Gideon, Battle-Forged", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}");
        setChoice(playerA, "X=4");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 16);
        assertLife(playerB, 16);
        assertGraveyardCount(playerA, cRats, 1);
        assertDamageReceived(playerA, "Swamp", 0);
        assertDamageReceived(playerB, "Shivan Dragon", 4);
        assertDamageReceived(playerA, "Gideon, Battle-Forged", 0);
    }
}
