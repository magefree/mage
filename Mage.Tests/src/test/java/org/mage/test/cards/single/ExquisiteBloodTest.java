package org.mage.test.cards.single;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class ExquisiteBloodTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // card we test
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Exquisite Blood", 1);

        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt");
        addCard(Constants.Zone.HAND, playerA, "Bump in the Night");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Raging Goblin", 2);

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Constants.Zone.HAND, playerB, "Shock");


        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Bump in the Night", playerB);

        attack(1, playerA, "Raging Goblin");
        attack(1, playerA, "Raging Goblin");

        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", playerA);

        setStopAt(2, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerB, 12);
        assertLife(playerA, 26);
    }

}
