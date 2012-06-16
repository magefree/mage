package org.mage.test.cards.abilities.oneshot.damage;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestBase;

/**
 * @author Loki
 */
public class PsionicBlastTest extends CardTestBase {

    @Test
    public void testDamageInPlayer() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island");
        addCard(Constants.Zone.HAND, playerA, "Psionic Blast");

        castSpell(playerA, "Psionic Blast");
        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 16);
    }
}
