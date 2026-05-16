package org.mage.test.cards.single.soc;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

import java.io.FileNotFoundException;

public class FurygaleFlockingTest extends CardTestPlayerAPIImpl {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(
                MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL,
                MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7
        );
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void testCreatesTwoHastyElementalsForEachOpponentThatMustAttackThatOpponent() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "Furygale Flocking");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Furygale Flocking");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Elemental Token", 6);
        assertLife(playerA, 20);
        assertLife(playerB, 14);
        assertLife(playerC, 14);
        assertLife(playerD, 14);
    }
}
