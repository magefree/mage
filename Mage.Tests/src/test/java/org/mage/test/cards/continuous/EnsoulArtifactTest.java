
package org.mage.test.cards.continuous;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EnsoulArtifactTest extends CardTestPlayerBase {

    /**
     * Tests boost disappeared after creature died
     */
    @Test
    public void testBoostWithUndying() {
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Citadel", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // Enchanted artifact is a creature with base power and toughness 5/5 in addition to its other types.
        addCard(Zone.HAND, playerA, "Ensoul Artifact");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ensoul Artifact", "Darksteel Citadel");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, "Darksteel Citadel", IndestructibleAbility.getInstance(), true);
        assertPowerToughness(playerA, "Darksteel Citadel", 5, 5);
    }

}
