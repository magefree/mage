
package org.mage.test.cards.continuous;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.CardType;
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
    public void test_Boost() {
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Citadel", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // Enchanted artifact is a creature with base power and toughness 5/5 in addition to its other types.
        addCard(Zone.HAND, playerA, "Ensoul Artifact");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ensoul Artifact", "Darksteel Citadel");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, "Darksteel Citadel", IndestructibleAbility.getInstance(), true);
        assertType("Darksteel Citadel", CardType.CREATURE, true);
        assertPowerToughness(playerA, "Darksteel Citadel", 5, 5);
    }

    @Test
    public void test_BoostDisappearedOnBlink() {
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Citadel", 1);

        // blink
        addCard(Zone.HAND, playerA, "Momentary Blink", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // Enchanted artifact is a creature with base power and toughness 5/5 in addition to its other types.
        addCard(Zone.HAND, playerA, "Ensoul Artifact");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ensoul Artifact", "Darksteel Citadel");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Momentary Blink", "Darksteel Citadel");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Momentary Blink", 0);
        assertPermanentCount(playerA, "Darksteel Citadel", 1);
        assertPowerToughness(playerA, "Darksteel Citadel", 0, 0);
        assertType("Darksteel Citadel", CardType.CREATURE, false);
        assertAbility(playerA, "Darksteel Citadel", IndestructibleAbility.getInstance(), true);
        assertPermanentCount(playerA, "Ensoul Artifact", 0);
    }
}
