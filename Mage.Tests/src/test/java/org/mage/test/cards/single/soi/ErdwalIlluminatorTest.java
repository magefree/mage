
package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author halljared
 */
public class ErdwalIlluminatorTest extends CardTestPlayerBase {
    /**
     *  Whenever you investigate for the first time each turn, investigate an additional time.
     */
    @Test
    public void investigateFirstTimeTriggers() {

        addCard(Zone.HAND, playerA, "Thraben Inspector", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Erdwal Illuminator", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 2);
    }

    @Test
    public void ignoresOpponentInvestigateTriggers() {

        addCard(Zone.HAND, playerB, "Thraben Inspector", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Erdwal Illuminator", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Thraben Inspector");
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Clue Token", 1);
    }

    @Test
    public void ignoresSecondInvestigateTriggers() {

        addCard(Zone.HAND, playerA, "Thraben Inspector", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Erdwal Illuminator", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 3);
    }

    @Test
    public void separateTurnsInvestigateTriggers() {

        addCard(Zone.HAND, playerA, "Thraben Inspector", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Erdwal Illuminator", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");
        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 4);
    }
}
