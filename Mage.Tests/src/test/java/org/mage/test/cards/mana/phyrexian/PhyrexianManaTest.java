package org.mage.test.cards.mana.phyrexian;

import mage.Constants;
import mage.Constants.PhaseStep;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class PhyrexianManaTest extends CardTestPlayerBase {

    @Test
    public void testNoManaToCast() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Constants.Zone.HAND, playerA, "Apostle's Blessing");

        setChoice(playerA, "Black");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Apostle's Blessing", "Elite Vanguard");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertHandCount(playerA, 1);
    }

}
