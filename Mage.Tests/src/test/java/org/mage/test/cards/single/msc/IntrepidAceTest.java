package org.mage.test.cards.single.msc;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class IntrepidAceTest extends CardTestPlayerBase {

    @Test
    public void testAttackRemovesBoostAndCombatEndRestoresIt() {
        addCard(Zone.BATTLEFIELD, playerA, "Intrepid Ace");

        checkPT("before combat", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Intrepid Ace", 4, 1);
        attack(1, playerA, "Intrepid Ace");
        checkPT("attacking", 1, PhaseStep.DECLARE_BLOCKERS, playerA, "Intrepid Ace", 2, 1);
        checkPT("after combat", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Intrepid Ace", 4, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 18);
        assertPowerToughness(playerA, "Intrepid Ace", 4, 1);
    }

    @Test
    public void testBlockingRemovesBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Intrepid Ace");
        addCard(Zone.BATTLEFIELD, playerB, "Phyrexian Walker");

        attack(2, playerB, "Phyrexian Walker");
        block(2, playerA, "Intrepid Ace", "Phyrexian Walker");
        checkPT("blocking", 2, PhaseStep.COMBAT_DAMAGE, playerA, "Intrepid Ace", 2, 1);
        checkPT("after blocking", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Intrepid Ace", 4, 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Phyrexian Walker", 1);
        assertPermanentCount(playerA, "Intrepid Ace", 1);
    }

    @Test
    public void testBoostCanPayCrewFour() {
        addCard(Zone.BATTLEFIELD, playerA, "Intrepid Ace");
        addCard(Zone.BATTLEFIELD, playerA, "Aradara Express");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 4");
        setChoice(playerA, "Intrepid Ace");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Intrepid Ace", true);
        assertType("Aradara Express", CardType.CREATURE, true);
        assertPowerToughness(playerA, "Intrepid Ace", 4, 1);
    }
}
