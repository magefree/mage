package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author Jmlundeen
 */
public class MaximumCarnageTest extends CardTestCommander4Players {

    /*
    Maximum Carnage
    {4}{R}
    Enchantment - Saga
    (As this Saga enters step and after your draw step, add a lore counter. Sacrifice after III.)
    I -- Until your next turn, each creature attacks each combat if able and attacks a player other than you if able.
    II -- Add {R}{R}{R}.
    III -- This Saga deals 5 damage to each opponent.
    */
    private static final String maximumCarnage = "Maximum Carnage";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear
    
    2/2
    */
    private static final String bearCub = "Bear Cub";

    @Test
    public void testMaximumCarnage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, maximumCarnage);
        addCard(Zone.BATTLEFIELD, playerB, bearCub);
        addCard(Zone.BATTLEFIELD, playerD, bearCub);

        addTarget(playerD, playerC); // must attack
        addTarget(playerB, playerD); // must attack

        setStopAt(4, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void testMaximumCarnageCantAttackController() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, maximumCarnage);
        addCard(Zone.BATTLEFIELD, playerB, bearCub);
        addCard(Zone.BATTLEFIELD, playerD, bearCub);

        addTarget(playerD, playerA); // must attack

        setStopAt(4, PhaseStep.END_TURN);
        try {
            execute();
        } catch (AssertionError e) {
            assertTrue("Shouldn't be able to attack playerA",
                    e.getMessage().contains("[targetPlayer=PlayerA], but not used"));
        }
    }
}