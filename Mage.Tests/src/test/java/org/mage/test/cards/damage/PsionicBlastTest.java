package org.mage.test.cards.damage;

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
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 16);
    }
}
