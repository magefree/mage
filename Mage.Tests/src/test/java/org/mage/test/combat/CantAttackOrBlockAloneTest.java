package org.mage.test.combat;

import junit.framework.Assert;
import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx_at_gmail.com
 */
public class CantAttackOrBlockAloneTest extends CardTestPlayerBase {

    /**
     * Try to attack alone
     */
    @Test
    public void testCantAttackAlone() {
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mogg Flunkies");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        attack(2, playerB, "Mogg Flunkies");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);

        Permanent moggFlunkies = getPermanent("Mogg Flunkies", playerB.getId());
        Assert.assertFalse("Shouldn't be tapped because it can't attack alone", moggFlunkies.isTapped());
    }

    /**
     * This time attack in pair
     */
    @Test
    public void testCantAttackAlone2() {
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mogg Flunkies");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        attack(2, playerB, "Mogg Flunkies");
        attack(2, playerB, "Elite Vanguard");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 15);
    }

    /**
     * Try to block alone
     */
    @Test
    public void testCantBlockAlone() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mogg Flunkies");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Mogg Flunkies", "Elite Vanguard");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
    }

    /**
     * Try to block in pair
     */
    @Test
    public void testCantBlockAlone2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mogg Flunkies");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Mogg Flunkies", "Elite Vanguard");
        block(2, playerA, "Llanowar Elves", "Elite Vanguard");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
    }
}
