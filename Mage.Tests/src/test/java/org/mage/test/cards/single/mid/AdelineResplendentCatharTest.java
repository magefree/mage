package org.mage.test.cards.single.mid;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * 800.4a
 * When a player leaves the game, all objects (see rule 109) owned by that player leave the game and any effects which
 * give that player control of any objects or players end. Then, if that player controlled any objects on the stack not
 * represented by cards, those objects cease to exist. Then, if there are any objects still controlled by that player,
 * those objects are exiled. This is not a state-based action. It happens as soon as the player leaves the game. If the
 * player who left the game had priority at the time they left, priority passes to the next player in turn order who’s
 * still in the game.
 * <p>
 * 508.4a
 * If a creature would be put onto the battlefield attacking a certain player, and that player is no longer in the game,
 * the creature is put onto the battlefield but is never considered an attacking creature. [...]
 *
 * @author DarioBarrabinoDev
 */
public class AdelineResplendentCatharTest extends CardTestCommander4Players {

    @Test
    public void test_IgnoreLeavedPlayers() {
        // Whenever you attack, for each opponent, create a 1/1 white Human creature token
        // that's tapped and attacking that player or a planeswalker they control.
        addCard(Zone.BATTLEFIELD, playerA, "Adeline, Resplendent Cathar"); // */4, vigilance
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        //
        addCustomEffect_TargetDamage(playerA, 20); // kill playerB

        // playerB loses the game
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target damage 20", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // tokens for playerD and playerC only
        attack(1, playerA, "Grizzly Bears", playerC);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Human Token", 2);
        assertLife(playerC, 20 - 2 - 1);
        assertLife(playerD, 20 - 1);
    }
}
