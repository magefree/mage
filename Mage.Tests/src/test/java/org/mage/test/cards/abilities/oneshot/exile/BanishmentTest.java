package org.mage.test.cards.abilities.oneshot.exile;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BanishmentTest extends CardTestPlayerBase {

    @Test
    public void testBanishment() throws GameException {
        FreeForAll multiPlayerGame = new FreeForAll(MultiplayerAttackOption.LEFT, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 20);
        multiPlayerGame.setNumPlayers(3);
        playerA = createPlayer(multiPlayerGame, "PlayerA", "RB Aggro.dck");
        playerB = createPlayer(multiPlayerGame, "PlayerB", "RB Aggro.dck");
        playerC = createPlayer(multiPlayerGame, "PlayerC", "RB Aggro.dck");

        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Banishment", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Steel Overseer", 4);
        addCard(Zone.BATTLEFIELD, playerC, "Memnite", 5);
        addCard(Zone.BATTLEFIELD, playerC, "Steel Overseer", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banishment");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerB, "Memnite");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Memnite", 2);
        assertPermanentCount(playerA, "Banishment", 1);
        assertPermanentCount(playerB, "Memnite", 0);
        assertPermanentCount(playerB, "Steel Overseer", 4);
        assertPermanentCount(playerC, "Memnite", 0);
        assertPermanentCount(playerC, "Steel Overseer", 1);
    }
}
