package org.mage.test.cards.single.fra;

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

import java.io.FileNotFoundException;

public class JiangYangguAloneTest extends CardTestMultiPlayerBase {

    // Jiang Yanggu, Alone: "Whenever a creature you control attacks a player alone,
    // discard a card, then draw a card. Then put a +1/+1 counter on that creature
    // for each card you've discarded this turn."

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL,
                MulliganType.GAME_DEFAULT.getMulligan(0), 40, 7);
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void testAttackingTwoPlayersAloneTriggersTwice() {
        // Each creature attacks a different player alone, so both attacks trigger.
        // The first resolving trigger puts one counter on its creature; the second
        // sees both cards discarded this turn and puts two counters on its creature.
        addCard(Zone.BATTLEFIELD, playerA, "Jiang Yanggu, Alone");
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");
        addCard(Zone.HAND, playerA, "Forest", 2);

        attack(1, playerA, "Jiang Yanggu, Alone", playerB);
        attack(1, playerA, "Pillarfield Ox", playerC);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Jiang Yanggu, Alone", CounterType.P1P1, 2);
        assertCounterCount(playerA, "Pillarfield Ox", CounterType.P1P1, 1);
    }

    @Test
    public void testTwoCreaturesAttackingOnePlayerOnlyOtherLoneAttackerTriggers() {
        // Jiang Yanggu, Alone and Pillarfield Ox attack PlayerB together, so neither
        // is attacking that player alone. Silvercoat Lion attacks PlayerC alone,
        // so only Silvercoat Lion triggers and gets a counter.
        addCard(Zone.BATTLEFIELD, playerA, "Jiang Yanggu, Alone");
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Forest");

        attack(1, playerA, "Jiang Yanggu, Alone", playerB);
        attack(1, playerA, "Pillarfield Ox", playerB);
        attack(1, playerA, "Silvercoat Lion", playerC);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Jiang Yanggu, Alone", CounterType.P1P1, 0);
        assertCounterCount(playerA, "Pillarfield Ox", CounterType.P1P1, 0);
        assertCounterCount(playerA, "Silvercoat Lion", CounterType.P1P1, 1);
    }
}
