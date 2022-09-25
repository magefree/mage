
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
        addCard(Zone.HAND, playerA, "Brainwash");
        addCard(Zone.HAND, playerA, "Brainwash");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Rielle, the Everwise");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Faithless Looting");
        setChoice(playerA, "Brainwash"); // discard
        setChoice(playerA, "Brainwash"); // discard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 5);

    }

    @Test
    public void testRielleTheEverwiseAbilityCycling() {

        addCard(Zone.HAND, playerA, "Unearth");
        addCard(Zone.HAND, playerA, "Brainwash");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Rielle, the Everwise");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling {2}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 3);

    }

    @Test
    public void testRielleTheEverwiseAbilityTransmute() {

        addCard(Zone.HAND, playerA, "Tolaria West");
        addCard(Zone.HAND, playerA, "Brainwash");
        addCard(Zone.LIBRARY, playerA, "Memnite", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Rielle, the Everwise");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Transmute {1}{U}{U}");
        addTarget(playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 3);

    }

}
