package org.mage.test.cards.single.iko;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import mage.game.TwoPlayerDuel;
import mage.game.mulligan.MulliganType;
import org.junit.Test;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

import java.io.FileNotFoundException;

/**
 * @author TheElk801
 */
public class ZirdaTheDawnwakerCompanionTest extends CardTestPlayerAPIImpl {

    private static final String zirda = "Zirda, the Dawnwaker";

    public ZirdaTheDawnwakerCompanionTest() {
        deckNameA = "Companion_ZirdaValid.dck";
        deckNameB = "Companion_ZirdaInvalid.dck";
    }

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new TwoPlayerDuel(MultiplayerAttackOption.LEFT, RangeOfInfluence.ONE, MulliganType.GAME_DEFAULT.getMulligan(0), 60, 20, 7);

        playerA = createPlayer(game, "PlayerA", deckNameA);
        playerB = createPlayer(game, "PlayerB", deckNameB);
        return game;
    }

    @Test
    public void testCompanionAbility() {
        setChoice(playerA, true); // choice to companion Zirda
        // playerB does not make a choice.

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);

        checkPlayableAbility("Valid Zirda Deck", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Companion", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Companion");
        checkPlayableAbility("Invalid Zirda Deck", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Companion", false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, zirda, 1);
    }
}
