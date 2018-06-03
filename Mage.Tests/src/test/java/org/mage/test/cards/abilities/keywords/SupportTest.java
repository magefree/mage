
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SupportTest extends CardTestPlayerBase {

    /**
     * Support Ability can target its source. Its cannot really.
     */
    @Test
    public void testCreatureSupport() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        // When Gladehart Cavalry enters the battlefield, support 6.
        // Whenever a creature you control with a +1/+1 counter on it dies, you gain 2 life.
        addCard(Zone.HAND, playerA, "Gladehart Cavalry"); // {5}{G}{G} 6/6

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gladehart Cavalry");
        addTarget(playerA, "Silvercoat Lion^Pillarfield Ox^Gladehart Cavalry");// Gladehart Cavalry should not be allowed

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);
        assertPowerToughness(playerA, "Pillarfield Ox", 3, 5);
        assertPowerToughness(playerA, "Gladehart Cavalry", 6, 6);
    }

}
