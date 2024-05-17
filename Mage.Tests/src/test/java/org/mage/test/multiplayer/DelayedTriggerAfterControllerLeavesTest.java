package org.mage.test.multiplayer;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

/**
 * 800.4d. If an object that would be owned by a player who has left the game would be created in any zone, it isn't created.
 * If a triggered ability that would be controlled by a player who has left the game would be put onto the stack, it isn't put on the stack.
 *
 * @author Susucr
 */
public class DelayedTriggerAfterControllerLeavesTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 40, 7);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void test_UntilYourNextTurn_AfterLeave() {
        setStrictChooseMode(true);

        // +1: Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Jace, Architect of Thought");

        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerD, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerC, "Elite Vanguard");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");

        attack(2, playerD, "Grizzly Bears", playerB);
        attack(2, playerD, "Elite Vanguard", playerB);
        setChoice(playerA, "Until your next turn"); // order trigger

        checkLife("2: after D attack affected by Delayed triggers", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, 40 - 2);
        concede(2, PhaseStep.END_TURN, playerA);

        attack(3, playerC, "Grizzly Bears", playerB);
        attack(3, playerC, "Elite Vanguard", playerB);

        checkLife("3: after C attack affected by Delayed triggers", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, 40 - 2 - 4);
        // No trigger, as triggers from leaved players don't trigger

        attack(5, playerD, "Grizzly Bears", playerB);
        attack(5, playerD, "Elite Vanguard", playerB);

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 40 - 2 - 4 - 4);
    }
}
