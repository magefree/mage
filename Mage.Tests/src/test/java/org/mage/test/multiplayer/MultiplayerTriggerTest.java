package org.mage.test.multiplayer;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

public class MultiplayerTriggerTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, 0, 40);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        playerD = createPlayer(game, playerD, "PlayerD");
        return game;
    }

    @Test
    public void testMultiplayerAttackStinkdrinkerBanditTrigger(){
        String pestermite = "Pestermite";
        String stinkdrinker = "Stinkdrinker Bandit";
        addCard(Zone.BATTLEFIELD, playerA, stinkdrinker);
        addCard(Zone.BATTLEFIELD, playerA, pestermite, 1);

        attack(1, playerA, pestermite, playerB);
        attack(1, playerA, stinkdrinker, playerC);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, pestermite, 4, 2);
    }
}
