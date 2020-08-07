

package org.mage.test.cards.single.dis;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class CourtHussarTest extends CardTestPlayerBase {

    // When Court Hussar enters the battlefield, sacrifice it unless {W} was spent to cast it.
    @Test
    public void testWhiteManaWasPaidCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Court Hussar");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Court Hussar");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Court Hussar", 1);

    }

    @Test
    public void testNoWhiteManaWasPaidCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Court Hussar");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Court Hussar");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Court Hussar", 0);

    }

}
