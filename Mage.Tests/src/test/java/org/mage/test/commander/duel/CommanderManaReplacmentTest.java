
package org.mage.test.commander.duel;

import java.io.FileNotFoundException;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 *
 * @author LevelX2
 */
public class CommanderManaReplacmentTest extends CardTestCommanderDuelBase {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        setDecknamePlayerA("CommanderDuel_UW.dck"); // Commander = Daxos of Meletis
        return super.createNewGameAndPlayers();
    }

    /**
     * This issue appears to arise in both Commander Two Player Duel and
     * Commander Free For All. Whenever a player (call her player 1) controls a
     * permanent with a mana doubling continuous effect (e.g. Mana Flare) and
     * another player (call her player 2) taps an affected permanent for mana
     * that is outside of player 1's color identity, player 2 gets an additional
     * colorless mana rather than the correct color of mana. I suspect the
     * reason for this is that Xmage is treating Mana Flare as producing mana of
     * the appropriate color and adding it to another player's mana pool, which
     * is both incorrect in the game rules and would cause this problem.
     *
     * For example, I am playing a mono red deck and control a Mana Flare. You
     * are playing a mono green deck and you tap a Forest for mana. You should
     * add GG, but instead, Xmage shows you adding 1G to your
     * mana pool.
     */
    @Test
    public void castCommanderWithFlash() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.HAND, playerA, "Vedalken Mastermind", 1); // {U}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Mana Flare");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}"); // should add {U}{U}
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vedalken Mastermind");
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Vedalken Mastermind", 1);

    }

}
