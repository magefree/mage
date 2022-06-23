package org.mage.test.multiplayer;

import mage.abilities.keyword.HexproofAbility;
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

/**
 * Created by escplan9
 */
public class PrivilegedPositionTest extends CardTestMultiPlayerBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 40);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");
        return game;
    }

    /*
     * Reported bug: see issue #3328
     * Players unable to attack Planeswalker with Privileged Position on battlefield.
     */
    @Test
    public void testAttackPlaneswalkerWithHexproofPrivilegedPosition() {

        /*
         Privileged Position {2}{G/W}{G/W}{G/W}
        Enchantment
        Other permanents you control have hexproof.
         */
        String pPosition = "Privileged Position";
        String sorin = "Sorin, Solemn Visitor"; // planeswalker {2}{W}{B} 4 loyalty
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.BATTLEFIELD, playerD, pPosition);
        addCard(Zone.BATTLEFIELD, playerD, sorin);
        addCard(Zone.BATTLEFIELD, playerA, memnite);
        addCard(Zone.BATTLEFIELD, playerB, memnite);
        addCard(Zone.BATTLEFIELD, playerC, memnite);

        // Player order: A -> D -> C -> B
        attack(1, playerA, memnite, sorin);
        attack(3, playerC, memnite, sorin);
        attack(4, playerB, memnite, sorin);

        setStopAt(4, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerD, pPosition, 1);
        assertPermanentCount(playerD, sorin, 1);
        assertTappedCount(memnite, true, 3);
        assertLife(playerD, 40);
        assertCounterCount(sorin, CounterType.LOYALTY, 1);
        assertAbility(playerD, sorin, HexproofAbility.getInstance(), true);
    }
}
