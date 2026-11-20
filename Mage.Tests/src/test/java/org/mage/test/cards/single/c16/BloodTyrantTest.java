package org.mage.test.cards.single.c16;

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

public class BloodTyrantTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void test_PlayerLosesTheGame() {
        // Flying, trample
        // At the beginning of your upkeep, each player loses 1 life. Put a +1/+1 counter on this creature for each 1 life lost this way.
        // Whenever a player loses the game, put five +1/+1 counters on this creature.
        addCard(Zone.BATTLEFIELD, playerD, "Blood Tyrant"); // 5/5
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        // Lightning Bolt deals 3 damage to any target.
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        setLife(playerC, 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerC);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLostTheGame(playerC);
        assertCounterCount(playerD, "Blood Tyrant", CounterType.P1P1, 5);
        assertPowerToughness(playerD, "Blood Tyrant", 10, 10);
    }
}
