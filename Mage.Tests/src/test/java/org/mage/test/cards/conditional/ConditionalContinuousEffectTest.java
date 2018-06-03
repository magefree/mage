
package org.mage.test.cards.conditional;

import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ConditionalContinuousEffectTest extends CardTestPlayerBase {

    @Test
    public void testManorGargoyle() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Manor Gargoyle");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: Until end of turn");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped("Mountain", true);
        assertAbility(playerA, "Manor Gargoyle", DefenderAbility.getInstance(), false);
        assertAbility(playerA, "Manor Gargoyle", IndestructibleAbility.getInstance(), false);
    }

}
