
package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CantBeTargetTest extends CardTestPlayerBase {

    /**
     * Test that the target of Vines of Vastwood can't
     * be the target of spells or abilities your opponents control this turn 
     */
    @Test
    public void testVinesofVastwood() {
        addCard(Zone.HAND, playerA, "Divine Favor");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.HAND, playerB, "Vines of Vastwood");
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divine Favor", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Vines of Vastwood", "Silvercoat Lion", "Divine Favor");
        setChoice(playerB, false); // don't use kicker
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertGraveyardCount(playerA, "Divine Favor", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }
}
