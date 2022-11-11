package org.mage.test.multiplayer;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;

public class AngelOfSerenityTest extends CardTestMultiPlayerBase {
    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // Start Life = 2
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 2);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    /**
     * In a multiplayer game when Angel of Serenity leaves the Battlefield because the OWNER leaves the game 800.4a,
     * it should return the exiled creatures if Angel of Serenity is controlled by a player that does not leave,
     * since then 800.4d does not apply.
     * 20200417 - 800.4a/800.4d
     */
    @Test
    public void TestAngelOfSerenityTakeoverExileReturn() {

        addCard(Zone.BATTLEFIELD, playerA, "Island", 7); // add mana to cast card to take over Angel
        addCard(Zone.HAND, playerA, "Agent of Treachery");


        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Dromoka Dunecaster", 3);

        addCard(Zone.BATTLEFIELD, playerC, "Plains", 7);
        addCard(Zone.HAND, playerC, "Angel of Serenity"); // {4}{W}{W}{W}


        // player C turn is 3
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerC, "Angel of Serenity");
        addTarget(playerC, "Dromoka Dunecaster^Dromoka Dunecaster^Dromoka Dunecaster");

        // player A 2nd turn is 5
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Agent of Treachery");
        addTarget(playerA, "Angel of Serenity");

        concede(5, PhaseStep.POSTCOMBAT_MAIN, playerC);

        setStopAt(6, PhaseStep.UPKEEP);
        execute();

        Assert.assertFalse("Player of Angel of Serenity did not leave the game", playerC.isInGame());
        assertPermanentCount(playerA, 8);
        assertPermanentCount(playerB, 3);
        assertHandCount(playerB, 4);
    }
}
