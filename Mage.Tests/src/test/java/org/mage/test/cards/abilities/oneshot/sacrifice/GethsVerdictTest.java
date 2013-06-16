package org.mage.test.cards.abilities.oneshot.sacrifice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Geth's Verdict");
        addCard(Zone.BATTLEFIELD, playerB, "White Knight");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Geth's Verdict");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "White Knight", 0);
        assertLife(playerB, 19);
    }
}
