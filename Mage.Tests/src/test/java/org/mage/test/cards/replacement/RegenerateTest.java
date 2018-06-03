
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class RegenerateTest extends CardTestPlayerBase {

    @Test
    public void testRegenerateAbilityGainedByEnchantment() {
        addCard(Zone.BATTLEFIELD, playerA, "Underworld Cerberus");

        addCard(Zone.BATTLEFIELD, playerB, "Sagu Archer");
        addCard(Zone.HAND, playerB, "Molting Snakeskin");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);


        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Molting Snakeskin", "Sagu Archer");
        attack(2, playerB, "Sagu Archer");
        block(2, playerA, "Underworld Cerberus", "Sagu Archer");
        activateAbility(2, PhaseStep.DECLARE_BLOCKERS, playerB, "{2}{B}: Regenerate {this}.");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // attacker has to regenerat because of damage
        
        assertPermanentCount(playerA, "Underworld Cerberus", 1);
        assertPermanentCount(playerB, "Sagu Archer", 1);
        assertPermanentCount(playerB, "Molting Snakeskin", 1);

    }
}
