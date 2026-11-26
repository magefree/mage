package org.mage.test.cards.continuous;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 * @author JayDi85
 */
public class OracleEnVecNextTurnMultiplayerTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException {
        // Player order: A -> D -> C -> B
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void test_NotNextPlayer_DestroyAtThatPlayersEndStep() {
        // {T}: Target opponent chooses any number of creatures they control. During that player’s next turn, the chosen
        // creatures attack if able, and other creatures can’t attack. At the beginning of that turn’s end step,
        // destroy each of the chosen creatures that didn’t attack this turn. Activate this ability only during your turn.
        addCard(Zone.BATTLEFIELD, playerA, "Oracle en-Vec");
        //
        addCard(Zone.BATTLEFIELD, playerC, "Balduvian Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerC, "Angelic Wall", 1); // wall, can't attack
        addCard(Zone.BATTLEFIELD, playerC, "Ashcoat Bear", 1); // 2/2

        // turn 1 - playerA targets playerC, not the next player
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target opponent", playerC);
        setChoice(playerC, "Balduvian Bears");
        setChoice(playerC, "Angelic Wall");
        setChoice(playerC, TestPlayer.CHOICE_SKIP);

        // turn 2 - playerD's turn: not that player's turn, nothing destroyed at its end step
        checkPermanentCount("after turn 2", 3, PhaseStep.UPKEEP, playerC, "Balduvian Bears", 1);
        checkPermanentCount("after turn 2", 3, PhaseStep.UPKEEP, playerC, "Angelic Wall", 1);
        checkPermanentCount("after turn 2", 3, PhaseStep.UPKEEP, playerC, "Ashcoat Bear", 1);

        // turn 3 - playerC's turn: the chosen bear attacks, the other bear can't, the wall is destroyed at the end
        addTarget(playerC, playerA); // defender for the attacking bear
        checkLife("turn 3", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, 20 - 2);
        checkPermanentCount("after turn 3", 4, PhaseStep.UPKEEP, playerC, "Balduvian Bears", 1);
        checkPermanentCount("after turn 3", 4, PhaseStep.UPKEEP, playerC, "Angelic Wall", 0);
        checkPermanentCount("after turn 3", 4, PhaseStep.UPKEEP, playerC, "Ashcoat Bear", 1);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 2);
        assertLife(playerB, 20);
        assertLife(playerD, 20);
        assertGraveyardCount(playerC, "Angelic Wall", 1);
    }
}
