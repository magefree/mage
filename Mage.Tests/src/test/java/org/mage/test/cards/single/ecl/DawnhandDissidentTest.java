package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DawnhandDissidentTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        addCard(Zone.BATTLEFIELD, playerA, "Dawnhand Dissident");
        addCard(Zone.BATTLEFIELD, playerA, "Barbarian Horde");
        addCard(Zone.GRAVEYARD, playerA, "Ageless Guardian");
        addCard(Zone.HAND, playerA, "Adaptive Shimmerer"); // enters with three +1/+1 counters on it
        addCard(Zone.HAND, playerA, "Burdened Stoneback"); // enters with two -1/-1 counters on it
        addCard(Zone.HAND, playerA, "Belligerent Hatchling"); // enters with four -1/-1 counters on it
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Adaptive Shimmerer");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Burdened Stoneback");
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Belligerent Hatchling");
        activateAbility(7, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Blight 2: Exile target card from a graveyard");
        setChoice(playerA, "Barbarian Horde");
        addTarget(playerA, "Ageless Guardian");
        castSpell(7, PhaseStep.POSTCOMBAT_MAIN, playerA, "Ageless Guardian");
        setChoice(playerA, "Adaptive Shimmerer^Burdened Stoneback^Belligerent Hatchling");
        setChoice(playerA, "X=1");
        setChoice(playerA, "X=1");

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Ageless Guardian", 1);
    }

}
