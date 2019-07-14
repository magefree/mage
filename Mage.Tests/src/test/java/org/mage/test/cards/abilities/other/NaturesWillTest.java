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

public class NaturesWillTest extends CardTestPlayerBase {
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
        addCard(Zone.HAND, playerA, "Nature's Will");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Suntail Hawk");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerC, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nature's Will");
        attack(1, playerA, "Grizzly Bears", playerB);
        attack(1, playerA, "Suntail Hawk", playerC);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Forest", false, 4);
        assertTappedCount("Mountain", true, 4);
        assertTappedCount("Island", true, 4);
    }

    @Test
    public void testAttackOnePlayer() {
        addCard(Zone.HAND, playerA, "Nature's Will");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerC, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nature's Will");
        attack(1, playerA, "Grizzly Bears", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Forest", false, 4);
        assertTappedCount("Mountain", true, 4);
        assertTappedCount("Island", false, 4);
    }
}
