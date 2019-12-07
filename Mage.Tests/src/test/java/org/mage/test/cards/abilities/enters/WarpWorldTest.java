
package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class WarpWorldTest extends CardTestPlayerBase {

    /**
     * Hello. I was playing a 4 player EDH game, and after quite a ways into it
     * I casted Warp World. The game said that Prime Speaker Zegana's ability
     * triggered for hitting the battlefield, however on all of our screens
     * nothing was on the battlefield (not even other things that should have
     * hit from Warp World). I tried to upload the log but it gave me an error
     * about it possibly being an attack vector. Sorry. Below is at least the
     * end of the log with hopefully some useful information:
     */
    @Test
    public void testWarpWorld() {
        // Each player shuffles all permanents they own into their library, then reveals that many cards from the top of their library.
        // Each player puts all artifact, creature, and land cards revealed this way onto the battlefield, then does the same for enchantment cards,
        // then puts all cards revealed this way that weren't put onto the battlefield on the bottom of their library.
        addCard(Zone.HAND, playerA, "Warp World"); // Sorcery {5}{R}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);

        playerA.getLibrary().clear();
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 8);
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Warp World");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertHandCount(playerA, "Warp World", 0);
        assertPermanentCount(playerA, 8);

    }
}
