
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GoldnightCommanderTest extends CardTestPlayerBase {

    /*
     * Goldnight Commander {3}{W} Human Cleric Soldier
     * Whenever another creature enters the battlefield under your control, creatures you control get
     * +1/+1 until end of turn.
     */
    @Test
    public void testThreeCreaturesEnterAtTheSameTime() {
        // The ability of the Commander triggers three times and each trigger sees all three creatures
        addCard(Zone.HAND, playerA, "Thatcher Revolt");
        addCard(Zone.BATTLEFIELD, playerA, "Goldnight Commander", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thatcher Revolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Human Token", 3);
        assertPowerToughness(playerA, "Human Token", 4, 4, Filter.ComparisonScope.All);

    }

}
