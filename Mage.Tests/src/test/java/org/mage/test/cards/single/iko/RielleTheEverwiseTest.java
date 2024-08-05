package org.mage.test.cards.single.iko;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class RielleTheEverwiseTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.r.RielleTheEverwise} Rielle, the Everwise {1}{U}{R}
     * Legendary Creature — Human Wizard
     * Rielle, the Everwise gets +1/+0 for each instant and sorcery card in your graveyard.
     * Whenever you discard one or more cards for the first time each turn, draw that many cards.
     * 0/3
     */
    private static final String rielle = "Rielle, the Everwise";

    private static void checkMidExecute(String info, Player player, Game game, int stack, int hand, int life) {
        Assert.assertEquals(info + " - stack size", stack, game.getStack().size());
        Assert.assertEquals(info + " - hand size", hand, player.getHand().size());
        Assert.assertEquals(info + " - life", life, player.getLife());
    }

    // Bug: you had no play priority to respond to Rielle's trigger at end step.
    @Test
    public void test_CleanupDiscard_Opp_Response() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, rielle);
        addCard(Zone.HAND, playerA, "Island", 10);
        addCard(Zone.LIBRARY, playerA, "Island", 3);
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.HAND, playerB, "Shock");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        // Choose to discard Islands on first cleanup
        setChoice(playerA, "Island", 3);

        // Rielle's Trigger on the stack.
        runCode("Trigger on the stack", 1, PhaseStep.CLEANUP, playerB,
                (i, p, g) -> checkMidExecute(i, playerA, g, 1, 7, 20));

        // Cast Bolt in response of Rielle's trigger.
        castSpell(1, PhaseStep.CLEANUP, playerB, "Lightning Bolt", playerA);
        runCode("Trigger+Bolt on the stack", 1, PhaseStep.CLEANUP, playerB,
                (i, p, g) -> checkMidExecute(i, playerA, g, 2, 7, 20));

        waitStackResolved(1, PhaseStep.CLEANUP, 1); // resolves first bolt
        runCode("Trigger+Bolt on the stack", 1, PhaseStep.CLEANUP, playerB,
                (i, p, g) -> checkMidExecute(i, playerA, g, 1, 7, 20 - 3));

        waitStackResolved(1, PhaseStep.CLEANUP, 1);  // resolves rielle trigger
        runCode("Trigger+Bolt on the stack", 1, PhaseStep.CLEANUP, playerB,
                (i, p, g) -> checkMidExecute(i, playerA, g, 0, 10, 20 - 3));

        // Cast Shock after Rielle's trigger resolved.
        castSpell(1, PhaseStep.CLEANUP, playerB, "Shock", playerA);
        runCode("Trigger+Bolt on the stack", 1, PhaseStep.CLEANUP, playerB,
                (i, p, g) -> checkMidExecute(i, playerA, g, 1, 10, 20 - 3));

        waitStackResolved(1, PhaseStep.CLEANUP, 1);
        runCode("Trigger+Bolt on the stack", 1, PhaseStep.CLEANUP, playerB,
                (i, p, g) -> checkMidExecute(i, playerA, g, 0, 10, 20 - 3 - 2));

        // Discard for the second cleanup.
        setChoice(playerA, "Island", 3);

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20 - 3 - 2);
        assertHandCount(playerA, 7);
        assertGraveyardCount(playerA, "Island", 3 + 3);
    }
}
