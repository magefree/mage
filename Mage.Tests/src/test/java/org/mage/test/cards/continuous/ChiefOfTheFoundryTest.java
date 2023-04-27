
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ChiefOfTheFoundryTest extends CardTestPlayerBase {

    /**
     * Having two Chief of Foundry's out doesn't make them buff each other.
     */
    @Test
    public void testBoostOtherFoundry() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.HAND, playerA, "Chief of the Foundry");
        addCard(Zone.HAND, playerA, "Chief of the Foundry");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chief of the Foundry", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chief of the Foundry");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Chief of the Foundry", 3, 4, Filter.ComparisonScope.All);
    }
}
