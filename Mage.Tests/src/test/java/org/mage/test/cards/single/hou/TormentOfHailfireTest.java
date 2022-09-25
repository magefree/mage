package org.mage.test.cards.single.hou;

import java.io.FileNotFoundException;

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

/**
 *
 * @author LevelX2
 */
public class TormentOfHailfireTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // Start Life = 2
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 20);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void test_Normal() {
        setStrictChooseMode(true);
        
        // Repeat the following process X times. Each opponent loses 3 life unless they sacrifice a nonland permanent or discards a card.        
        addCard(Zone.HAND, playerA, "Torment of Hailfire", 1); // Sorcery {X}{B}{B}        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 12);
        
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2);
        addCard(Zone.HAND, playerB, "Plains", 1);
        
        addCard(Zone.BATTLEFIELD, playerC, "Silvercoat Lion", 3);
        
        addCard(Zone.BATTLEFIELD, playerD, "Silvercoat Lion", 3);        
        addCard(Zone.HAND, playerD, "Plains", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Torment of Hailfire");
        setChoice(playerA, "X=10");
        
        setChoice(playerD, true);// Sacrifices a nonland permanent?
        setChoice(playerD, "Silvercoat Lion");

        setChoice(playerB, true);// Sacrifices a nonland permanent?
        setChoice(playerB, "Silvercoat Lion");

        setChoice(playerD, true);// Sacrifices a nonland permanent?
        setChoice(playerD, "Silvercoat Lion");

        setChoice(playerB, true);// Sacrifices a nonland permanent?
        setChoice(playerB, "Silvercoat Lion");

        setChoice(playerD, false);// Sacrifices a nonland permanent?
        setChoice(playerD, true);// Discard a card?
        
        setChoice(playerB, true);// Discard a card?

        setChoice(playerD, true);// Sacrifices a nonland permanent?
        setChoice(playerD, "Silvercoat Lion");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Torment of Hailfire", 1);
        
        assertLife(playerA, 20);
        assertLife(playerC, 20);
        assertLife(playerD, 2);
        assertLife(playerB, -1);
        Assert.assertFalse("Player B is dead", playerB.isInGame());
        
    }
}
