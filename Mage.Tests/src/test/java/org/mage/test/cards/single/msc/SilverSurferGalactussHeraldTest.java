package org.mage.test.cards.single.msc;

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
 * @author DarioBarrabinoDev
 */
public class SilverSurferGalactussHeraldTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void test_TargetCreatureAttacksDamagedPlayer() {
        // Whenever Silver Surfer deals combat damage to a player, until the end of your next turn,
        // target creature attacks that player each combat if able.
        addCard(Zone.BATTLEFIELD, playerD, "Silver Surfer, Galactus's Herald"); // 4/5
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears"); // 2/2

        attack(2, playerD, "Silver Surfer, Galactus's Herald", playerB);
        addTarget(playerD, "Grizzly Bears");

        // turn 3 is PlayerC's turn, the bears must attack PlayerB
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4 - 2);
        assertLife(playerA, 20);
        assertLife(playerD, 20);
    }

    @Test
    public void test_RequirementEndsAfterYourNextTurn() {
        // Whenever Silver Surfer deals combat damage to a player, until the end of your next turn,
        // target creature attacks that player each combat if able.
        addCard(Zone.BATTLEFIELD, playerD, "Silver Surfer, Galactus's Herald"); // 4/5
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears"); // 2/2

        attack(2, playerD, "Silver Surfer, Galactus's Herald", playerB);
        addTarget(playerD, "Grizzly Bears");

        // PlayerD's next turn is turn 6, so on turn 7 the bears don't have to attack anymore
        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 4 - 2);
    }
}
