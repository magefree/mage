package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MirrormindCrownTest extends CardTestPlayerBase {

    @Test
    public void testFirstTokenCreationCanBeReplacedOnOpponentsTurn() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mirrormind Crown");
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Raise the Alarm");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Hill Giant");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm", true);
        setChoice(playerA, true); // replace Soldier tokens with copies of equipped creature

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Soldier Token", 0);
        assertPermanentCount(playerA, "Hill Giant", 3);
    }
}
