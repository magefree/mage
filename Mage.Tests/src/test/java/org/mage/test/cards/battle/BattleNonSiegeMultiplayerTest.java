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
 * Battles without the Siege subtype (CR 20260807, see #15875)
 *
 * @author DarioBarrabinoDev
 */
public class BattleNonSiegeMultiplayerTest extends BattleBaseTest {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(
                MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL,
                MulliganType.GAME_DEFAULT.getMulligan(0), 20, 7
        );
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    @Test
    @Ignore("TODO: 310.9a - a battle without battle types must be protected by its controller, but engine asks for an opponent")
    public void test_NoBattleType_ControllerIsProtector() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addTypelessBattleToHand(playerA, 3, null);

        // no protector choice: only its controller can protect it
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, typelessBattle);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerA, typelessBattle);
    }

    @Test
    @Ignore("TODO: 310.9b - any opponent must be able to attack a battle protected by its controller (depends on 310.9a)")
    public void test_NoBattleType_AnyOpponentCanAttack() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerD, bear);
        addTypelessBattleToHand(playerA, 3, null);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, typelessBattle);

        attack(2, playerD, bear, typelessBattle);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertBattle(playerA, playerA, typelessBattle);
        assertLife(playerA, 20);
        assertCounterCount(typelessBattle, CounterType.DEFENSE, 3 - 2);
    }
}
