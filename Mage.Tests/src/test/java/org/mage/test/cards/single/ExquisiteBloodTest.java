package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class ExquisiteBloodTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // card we test
        addCard(Zone.BATTLEFIELD, playerA, "Exquisite Blood", 1);

        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerA, "Bump in the Night");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Shock");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bump in the Night", playerB);

        attack(1, playerA, "Raging Goblin");
        attack(1, playerA, "Raging Goblin");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", playerA);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 12);
        assertLife(playerA, 26);
    }

}
