package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class AzoriusHeraldTest extends CardTestPlayerBase {

    /*
     * Azorius Herald
     * Creature — Spirit 2/1, 2W (3)
     * Azorius Herald can't be blocked.
     * When Azorius Herald enters the battlefield, you gain 4 life.
     * When Azorius Herald enters the battlefield, sacrifice it unless {U} was spent to cast it.
     *
    */
    
    // When Azorius Herald enters the battlefield, sacrifice it unless {U} was spent to cast it.
    @Test
    public void testBlueManaWasPaid() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Azorius Herald");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Azorius Herald");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Azorius Herald", 1);

    }

    @Test
    public void testNoBlueManaWasPaid() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Azorius Herald");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Azorius Herald");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Azorius Herald", 0);
        assertGraveyardCount(playerA, "Azorius Herald", 1);

    }

}
