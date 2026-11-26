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
 * 701.15b
 * Goaded is a designation a permanent can have. A goaded creature attacks each combat if able and attacks a player
 * other than the controller of the permanent, spell, or ability that caused it to be goaded if able. Goaded is neither
 * an ability nor part of the permanent’s copiable values.
 *
 * @author DarioBarrabinoDev, JayDi85
 */
public class KangDynastyMultiplayerTest extends CardTestMultiPlayerBase {

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
    public void test_ChapterI_EachOpponent_TapGoadAndDraw() {
        // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures.
        // Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Kang Dynasty"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerC, "Silvercoat Lion"); // lion 2/2
        addCard(Zone.BATTLEFIELD, playerD, "Balduvian Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kang Dynasty");
        addTarget(playerA, "Balduvian Bears"); // chapter I target for playerD
        addTarget(playerA, "Silvercoat Lion"); // chapter I target for playerC
        addTarget(playerA, "Grizzly Bears"); // chapter I target for playerB
        checkPermanentTapped("tapped D", 1, PhaseStep.POSTCOMBAT_MAIN, playerD, "Balduvian Bears", true, 1);
        checkPermanentTapped("tapped C", 1, PhaseStep.POSTCOMBAT_MAIN, playerC, "Silvercoat Lion", true, 1);
        checkPermanentTapped("tapped B", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", true, 1);

        // turns 2-4: goaded creatures must attack a player other than playerA, the damage still draws for playerA
        addTarget(playerD, playerC); // turn 2, defender for the goaded bear
        addTarget(playerC, playerB); // turn 3, defender for the goaded lion
        addTarget(playerB, playerD); // turn 4, defender for the goaded bear

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        Assert.assertTrue(getPermanent("Balduvian Bears", playerD).getGoadingPlayers().contains(playerA.getId()));
        Assert.assertTrue(getPermanent("Silvercoat Lion", playerC).getGoadingPlayers().contains(playerA.getId()));
        Assert.assertTrue(getPermanent("Grizzly Bears", playerB).getGoadingPlayers().contains(playerA.getId()));
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
        assertLife(playerC, 20 - 2);
        assertLife(playerD, 20 - 2);
        assertHandCount(playerA, 1 + 3); // turn 1 draw step (no skip in multiplayer) + one draw per creature
    }
}
