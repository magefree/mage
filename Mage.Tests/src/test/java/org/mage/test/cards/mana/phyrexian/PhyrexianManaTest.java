package org.mage.test.cards.mana.phyrexian;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class PhyrexianManaTest extends CardTestPlayerBase {

    @Test
    public void testNoManaToCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.HAND, playerA, "Apostle's Blessing");

        setChoice(playerA, "Black");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Apostle's Blessing", "Elite Vanguard");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        int life = playerA.getLife();
        int hand = playerA.getHand().size();
        // can be played only through life pay
        Assert.assertTrue(life == 20 && hand == 1 || life == 18 && hand == 0);
    }

}
