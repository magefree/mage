
package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author smartinsempere
 */
public class RielleTheEverwiseTest extends CardTestPlayerBase {

    @Test
    public void testRielleTheEverwiseAbilityDiscarding() {

        addCard(Zone.HAND, playerA, "Faithless Looting");
        addCard(Zone.HAND, playerA, "Brainwash");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Rielle, the Everwise");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Faithless Looting");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertHandCount(playerA, 3);

    }

    @Test
    public void testRielleTheEverwiseAbilityCycling() {

        addCard(Zone.HAND, playerA, "Ash Barrens");
        addCard(Zone.HAND, playerA, "Brainwash");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Rielle, the Everwise");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Basic landcycling {1}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertHandCount(playerA, 3);

    }

    @Test
    public void testRielleTheEverwiseAbilityTransmute() {

        addCard(Zone.HAND, playerA, "Tolaria West");
        addCard(Zone.HAND, playerA, "Brainwash");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Rielle, the Everwise");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Transmute {1}{U}{U}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertHandCount(playerA, 3);

    }

}
