package org.mage.test.cards.triggers.damage;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

/**
 * @author PurpleCrowbar
 */
public class TamiyoUpriserCrownedTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // reason: must use MultiplayerAttackOption.MULTIPLE
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    /**
     * Ensures Tamiyo's triggered ability only triggers once, taps and stuns relevant creatures, and only affects
     * creatures attacking her controller.
     */
    @Test
    public void testTamiyoUpriserCrowned() {
        // Player A (Tamiyo controller)
        addCard(Zone.HAND, playerA, "Tamiyo, Upriser Crowned", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 6);
        // Player D (attacks Tamiyo's controller and another player)
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerD, "Serra Angel", 1); // Vigilance
        addCard(Zone.BATTLEFIELD, playerD, "Segovian Angel", 1); // Vigilance
        addCard(Zone.BATTLEFIELD, playerD, "Grakk the Pacifist", 1); // Can't attack

        // Player A plays Tamiyo, becomes monarch
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Tamiyo, Upriser Crowned");

        // Player D attacks player A and B
        attack(2, playerD, "Grizzly Bears", playerA); // attack player A with regular creature
        attack(2, playerD, "Serra Angel", playerA); // attack player A with Vigilance creature
        attack(2, playerD, "Segovian Angel", playerB); // attack player B with Vigilance creature
        setChoice(playerA, "Whenever a creature deals combat damage to the monarch, its controller becomes the monarch.", 2);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        // Stun counters
        assertCounterCount(playerD, "Grizzly Bears", CounterType.STUN, 1);
        assertCounterCount(playerD, "Serra Angel", CounterType.STUN, 1);
        assertCounterCount(playerD, "Segovian Angel", CounterType.STUN, 0); // not stunned (attacked player other than Tamiyo's controller)
        assertCounterCount(playerD, "Grakk the Pacifist", CounterType.STUN, 0); // not stunned (didn't attack)

        // Tapping
        assertTapped("Grizzly Bears", true);
        assertTapped("Serra Angel", true);
        assertTapped("Segovian Angel", false); // not tapped (attacked player other than Tamiyo's controller)
        assertTapped("Grakk the Pacifist", false); // not tapped (didn't attack)
    }
}
