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

public class MultiplayerTriggerTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 40);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        playerD = createPlayer(game, playerD, "PlayerD");
        return game;
    }

    @Test
    public void testMultiplayerAttackStinkdrinkerBanditTrigger() {
        // Flash
        // Flying
        // When Pestermite enters the battlefield, you may tap or untap target permanent.
        String pestermite = "Pestermite"; // 2/1

        // Prowl {1}, {B} (You may cast this for its prowl cost if you dealt combat damage to a player this turn with a Goblin or Rogue.)
        // Whenever a Rogue you control attacks and isn't blocked, it gets +2/+1 until end of turn.
        String stinkdrinker = "Stinkdrinker Bandit"; // 2/1
        addCard(Zone.BATTLEFIELD, playerA, stinkdrinker);
        addCard(Zone.BATTLEFIELD, playerA, pestermite, 1);

        attack(1, playerA, pestermite, playerB);
        attack(1, playerA, stinkdrinker, playerC);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, stinkdrinker, 4, 2);
        assertPowerToughness(playerA, pestermite, 4, 2);
    }
}
