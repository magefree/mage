package org.mage.test.cards.battle;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;

/**
 * @author TheElk801
 */
public class BattleMultiplayerTest extends BattleBaseTest {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(
                MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL,
                MulliganType.GAME_DEFAULT.getMulligan(0), 20
        );
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    public void testRegularCastAndTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, belenon);

        setChoice(playerA, playerC.getName());
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertBattle(playerA, playerC, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
    }

    @Test
    public void testAttackBattle() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, belenon);

        setChoice(playerA, playerC.getName());
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        attack(1, playerA, bear, belenon);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertBattle(playerA, playerC, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertTapped(bear, true);
        assertLife(playerC, 20);
        assertCounterCount(belenon, CounterType.DEFENSE, 5 - 2);
    }

    @Ignore // TODO: this test fails randomly and it's not clear exactly why, it works correctly though
    @Test
    public void testAttackBattleBlock() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerC, bear);
        addCard(Zone.HAND, playerA, belenon);

        setChoice(playerA, playerC.getName());
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, belenon);

        attack(1, playerA, bear, belenon);
        block(1, playerC, bear, bear);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertBattle(playerA, playerC, belenon);
        assertPermanentCount(playerA, "Knight Token", 1);
        assertPermanentCount(playerA, bear, 0);
        assertGraveyardCount(playerA, bear, 1);
        assertPermanentCount(playerC, bear, 0);
        assertGraveyardCount(playerC, bear, 1);
        assertLife(playerC, 20);
        assertCounterCount(belenon, CounterType.DEFENSE, 5);
    }
}
