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
    public void test_NoBattleType_ControllerIsProtector() {
        // 310.9a - if it has no battle types, only its controller can be its protector
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
    public void test_NoBattleType_AnyOpponentCanAttack() {
        // 310.9b - any attacking player for whom the protector is a defending player can attack it
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
