package org.mage.test.cards.abilities.oneshot.sacrifice;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class MorticianBeetleTest extends CardTestPlayerBase {

    /**
     * Checks that pro black can still be sacrificed
     */
    @Test
    public void testSacrifice() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Constants.Zone.HAND, playerA, "Cruel Edict");
        addCard(Constants.Zone.HAND, playerA, "Geth's Verdict");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mortician Beetle");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Savannah Lions");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Sigiled Paladin");


        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Cruel Edict");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Geth's Verdict");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, 0);
        assertPowerToughness(playerA, "Mortician Beetle", 3, 3);
    }
}
