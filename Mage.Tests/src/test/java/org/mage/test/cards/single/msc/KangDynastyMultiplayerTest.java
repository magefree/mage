package org.mage.test.cards.single.msc;

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
 * @author DarioBarrabinoDev
 */
public class KangDynastyMultiplayerTest extends CardTestMultiPlayerBase {

    // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures.
    // Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
    private static final String kangDynasty = "Kang Dynasty";

    private static final String grizzly = "Grizzly Bears"; // 2/2
    private static final String lion = "Silvercoat Lion"; // 2/2
    private static final String balduvian = "Balduvian Bears"; // 2/2

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
    public void test_ChapterI_EachOpponent_DrawsOnDamageToAnyPlayer() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, kangDynasty);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerB, grizzly);
        addCard(Zone.BATTLEFIELD, playerC, lion);
        addCard(Zone.BATTLEFIELD, playerD, balduvian);

        // one target per opponent, in turn order
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kangDynasty);
        addTarget(playerA, balduvian);
        addTarget(playerA, lion);
        addTarget(playerA, grizzly);

        checkPermanentTapped("D tapped", 1, PhaseStep.POSTCOMBAT_MAIN, playerD, balduvian, true, 1);
        checkPermanentTapped("C tapped", 1, PhaseStep.POSTCOMBAT_MAIN, playerC, lion, true, 1);
        checkPermanentTapped("B tapped", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, grizzly, true, 1);

        // goaded creatures must attack a player other than playerA, each hit still draws a card for playerA
        addTarget(playerD, playerC);
        addTarget(playerC, playerB);
        addTarget(playerB, playerD);

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        Assert.assertTrue(getPermanent(balduvian, playerD).getGoadingPlayers().contains(playerA.getId()));
        Assert.assertTrue(getPermanent(lion, playerC).getGoadingPlayers().contains(playerA.getId()));
        Assert.assertTrue(getPermanent(grizzly, playerB).getGoadingPlayers().contains(playerA.getId()));
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
        assertLife(playerC, 20 - 2);
        assertLife(playerD, 20 - 2);
        assertHandCount(playerA, 1 + 3); // turn 1 draw + one card for each combat damage trigger
    }
}
