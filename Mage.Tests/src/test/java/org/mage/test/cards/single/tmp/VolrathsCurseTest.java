package org.mage.test.cards.single.tmp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class VolrathsCurseTest extends CardTestPlayerBase {

    private static final String curse = "Volrath's Curse";
    // Enchanted creature can’t attack or block, and its activated abilities can’t be activated.
    // That creature’s controller may sacrifice a permanent of their choice
    // for that player to ignore this effect until end of turn.
    private static final String soulmender = "Soulmender";
    // {T}: You gain 1 life

    @Test
    public void testActivation() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, curse);
        addCard(Zone.BATTLEFIELD, playerB, soulmender);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curse, soulmender);

        checkPlayableAbility("can't activate", 2, PhaseStep.UPKEEP, playerB, "{T}: You gain", false);

        activateAbility(2, PhaseStep.BEGIN_COMBAT, playerB, "Sacrifice");
        setChoice(playerB, "Mountain");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: You gain");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 21);
        assertTapped(soulmender, true);
        assertAttachedTo(playerB, curse, soulmender, true);
    }

}
