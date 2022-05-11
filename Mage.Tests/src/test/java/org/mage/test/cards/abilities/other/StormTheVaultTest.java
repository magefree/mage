package org.mage.test.cards.abilities.other;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.io.FileNotFoundException;

public class StormTheVaultTest extends CardTestPlayerBase {
    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 20);
        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        return game;
    }


    @Test
    public void testAttackMultiplePlayers() {
        addCard(Zone.BATTLEFIELD, playerA, "Storm the Vault");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Nessian Courser");
        addCard(Zone.BATTLEFIELD, playerA, "Suntail Hawk");
        addCard(Zone.BATTLEFIELD, playerA, "Lantern Kami");

        attack(1, playerA, "Grizzly Bears", playerB);
        attack(1, playerA, "Nessian Courser", playerB);
        attack(1, playerA, "Suntail Hawk", playerC);
        attack(1, playerA, "Lantern Kami", playerC);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Treasure Token", 2);
    }

    @Test
    public void testAttackOnePlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Storm the Vault");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Suntail Hawk");

        attack(1, playerA, "Grizzly Bears", playerB);
        attack(1, playerA, "Suntail Hawk", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Treasure Token", 1);
    }
}
