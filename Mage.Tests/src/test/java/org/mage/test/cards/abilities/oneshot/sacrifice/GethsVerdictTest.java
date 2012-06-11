package org.mage.test.cards.abilities.oneshot.sacrifice;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class GethsVerdictTest extends CardTestPlayerBase {

    /**
     * Checks that pro black can still be sacrificed
     */
    @Test
    public void testVersusProtectionFromBlack() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Constants.Zone.HAND, playerA, "Geth's Verdict");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "White Knight");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Geth's Verdict");
        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "White Knight", 0);
        assertLife(playerB, 19);
    }
}
