package org.mage.test.cards.single.mkc;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 * 701.15b
 * Goaded is a designation a permanent can have. A goaded creature attacks each combat if able and
 * attacks a player other than the controller of the permanent, spell, or ability that caused it to
 * be goaded if able. Goaded is neither an ability nor part of the permanent’s copiable values.
 *
 * @author JayDi85
 */
public class ImmortalObligationMultiplayerTest extends CardTestMultiPlayerBase {

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
    public void test_GoadedForAsLongAsDutyCounter() {
        // Return target creature card from an opponent's graveyard to the battlefield under their control with a
        // duty counter on it. For as long as that creature has a duty counter on it, it is goaded, can't attack you
        // or a permanent you control, and can't block creatures you control.
        addCard(Zone.HAND, playerA, "Immortal Obligation"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.GRAVEYARD, playerD, "Centaur Courser"); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Immortal Obligation", "Centaur Courser");

        // turns 2 and 6: goaded creature attacks on its own, also after the caster's next turn (turn 5)
        addTarget(playerD, playerC); // turn 2, defender for the goaded creature
        addTarget(playerD, playerC); // turn 6, defender for the goaded creature

        setStrictChooseMode(true);
        setStopAt(6, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerC, 20 - 3 - 3); // turn 2 attack + turn 6 attack
    }
}
