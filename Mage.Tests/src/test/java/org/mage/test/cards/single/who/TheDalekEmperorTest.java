package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * 102.1
 * A player is one of the people in the game. The active player is the player whose turn it is. The other players are
 * nonactive players.
 * <p>
 * 701.55d
 * If more than one player is instructed to face a villainous choice, the entire process described in rule 701.55a is
 * performed for each of those players one at a time in APNAP order. This is an exception to rule 608.2e.
 *
 * @author DarioBarrabinoDev
 */
public class TheDalekEmperorTest extends CardTestCommander4Players {

    @Test
    public void test_OpponentLostThisTurn() {
        // At the beginning of combat on your turn, each opponent faces a villainous choice -- That player sacrifices
        // a creature of their choice, or you create a 3/3 black Dalek artifact creature token with menace.
        addCard(Zone.BATTLEFIELD, playerA, "The Dalek Emperor");
        addCustomEffect_TargetDamage(playerA, 20);
        //
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerD, "Silvercoat Lion"); // lion 2/2

        // playerB loses the game
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target damage 20", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        runCode("playerB lost", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertTrue("playerB must lose", playerB.hasLost());
        });

        // beginning of combat: only playerD and playerC face the choice, playerB must not
        setChoice(playerD, false); // playerA creates a Dalek
        setChoice(playerC, true); // playerC sacrifices a creature
        setChoice(playerC, "Grizzly Bears"); // creature to sacrifice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertPermanentCount(playerA, "Dalek Token", 1);
        assertGraveyardCount(playerC, "Grizzly Bears", 1);
        assertPermanentCount(playerD, "Silvercoat Lion", 1);
    }
}
