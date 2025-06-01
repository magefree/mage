package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AlenaKessigTrapperTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AlenaKessigTrapper Alena, Kessig Trapper} {4}{R}
     * Legendary Creature — Human Scout
     * First strike
     * {T}: Add an amount of {R} equal to the greatest power among creatures you control that entered this turn.
     * Partner
     * 4/3
     */
    private static final String alena = "Alena, Kessig Trapper";

    @Test
    public void test_alena() {
        addCard(Zone.BATTLEFIELD, playerA, alena);
        addCard(Zone.HAND, playerA, "Frost Walker"); // 4/1 {1}{U}
        addCard(Zone.HAND, playerA, "Barbarian Horde", 2); // 3/3 {3}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // start on turn 3 as Alena has technically entered t1.
        checkPlayableAbility("1: Can not cast Horde", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Barbarian Horde", false);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Frost Walker");

        checkPlayableAbility("2: Can cast Horde", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Barbarian Horde", true);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Barbarian Horde");

        checkPlayableAbility("3: Can not cast Horde", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Barbarian Horde", false);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Barbarian Horde", 1);
        assertHandCount(playerA, "Barbarian Horde", 1);
    }
}
