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
 * @author LevelX2
 */
public class VindictiveLichTest extends CardTestMultiPlayerBase {

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

    /**
     * Tests multiplayer effects Player order: A -> D -> C -> B
     */
    @Test
    public void CallerOfThePackTest() {

        // When Vindictive Lich dies, choose one or more. Each mode must target a different player.
        // *Target opponent sacrifices a creature.
        // *Target opponent discards two cards.
        // *Target opponent loses 5 life.
        addCard(Zone.BATTLEFIELD, playerA, "Vindictive Lich"); // Creature {3}{B} 4/1

        // Sacrifice a creature: Put a +1/+1 counter on Bloodflow Connoisseur.
        addCard(Zone.BATTLEFIELD, playerA, "Bloodflow Connoisseur"); // Creature {2}{B} 1/1

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2);
        addCard(Zone.HAND, playerC, "Lightning Bolt", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, "Vindictive Lich");

        setModeChoice(playerA, "1");
        addTarget(playerA, playerB);
        setModeChoice(playerA, "2");
        addTarget(playerA, playerC);
        setModeChoice(playerA, "3");
        addTarget(playerA, playerD);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Vindictive Lich", 1);
        assertPowerToughness(playerA, "Bloodflow Connoisseur", 2, 2);

        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertHandCount(playerC, 0);
        assertLife(playerD, 35);

    }

}
