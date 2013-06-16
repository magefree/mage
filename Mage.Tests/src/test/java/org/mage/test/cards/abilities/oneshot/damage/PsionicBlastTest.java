package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Loki
 */
public class PsionicBlastTest extends CardTestPlayerBase {

    @Test
    public void testDamageInPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Psionic Blast");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA ,"Psionic Blast");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 16);
    }
}
