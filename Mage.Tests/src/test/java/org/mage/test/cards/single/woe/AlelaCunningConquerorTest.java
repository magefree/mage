package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.multiplayer.MultiplayerTriggerTest;
import org.mage.test.player.TestPlayer;

/**
 * @author Susucr
 */
public class AlelaCunningConquerorTest extends MultiplayerTriggerTest {

    /**
     * Alela, Cunning Conqueror
     * {2}{U}{B}
     * Legendary Creature — Faerie Warlock
     * <p>
     * Flying
     * <p>
     * Whenever you cast your first spell during each opponent’s turn, create a 1/1 black Faerie Rogue creature token with flying.
     * <p>
     * Whenever one or more Faeries you control deal combat damage to a player, goad target creature that player controls.
     */
    private static final String alela = "Alela, Cunning Conqueror";

    // 2/1 Faerie
    private static final String pestermite = "Pestermite";
    // not a Faerie
    private static final String stormCrow = "Storm Crow";

    // Bears for playerB
    private static final String bears = "Grizzly Bears";

    // Carp for playerC
    private static final String carp = "Ancient Carp";

    // Devoted for playerD
    private static final String devoted = "Devoted Hero";


    @Test
    public void attackSinglePlayer() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, alela);
        addCard(Zone.BATTLEFIELD, playerA, pestermite);
        addCard(Zone.BATTLEFIELD, playerB, bears);
        addCard(Zone.BATTLEFIELD, playerC, carp);
        addCard(Zone.BATTLEFIELD, playerD, devoted);

        attack(1, playerA, alela, playerC);
        attack(1, playerA, pestermite, playerC);
        addTarget(playerA, carp); // One trigger to goad a creature for playerC

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGoadedByPlayer(carp, playerA);
        assertLife(playerC, 40 - 2 - 2);
    }

    @Test
    public void attackTwoPlayers() {
        //setStrictChooseMode(true); // did not succesfully differentiate the two very similar triggers to order them in the stack.
        addCard(Zone.BATTLEFIELD, playerA, alela);
        addCard(Zone.BATTLEFIELD, playerA, pestermite);
        addCard(Zone.BATTLEFIELD, playerB, bears);
        addCard(Zone.BATTLEFIELD, playerC, carp);
        addCard(Zone.BATTLEFIELD, playerD, devoted);

        attack(1, playerA, alela, playerB);
        attack(1, playerA, pestermite, playerD);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGoadedByPlayer(bears, playerA);
        assertGoadedByPlayer(devoted, playerA);
        assertLife(playerB, 40 - 2);
        assertLife(playerD, 40 - 2);
    }

    @Test
    public void attackWithNotAFaerie() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, alela);
        addCard(Zone.BATTLEFIELD, playerA, stormCrow);
        addCard(Zone.BATTLEFIELD, playerB, bears);
        addCard(Zone.BATTLEFIELD, playerC, carp);
        addCard(Zone.BATTLEFIELD, playerD, devoted);

        attack(1, playerA, alela, playerB);
        attack(1, playerA, stormCrow, playerD);
        addTarget(playerA, bears); // One trigger to goad a creature for playerB

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGoadedByPlayer(bears, playerA);
        assertLife(playerB, 40 - 2);
        assertLife(playerD, 40 - 1);
    }

    private void assertGoadedByPlayer(String attacker, TestPlayer player) {
        Permanent permanent = getPermanent(attacker);
        Assert.assertTrue(
                "Creature should be goaded by " + player.getName(),
                permanent.getGoadingPlayers().contains(player.getId())
        );
    }
}