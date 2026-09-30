package org.mage.test.cards.sld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

public class LucyMacLeanPositivelyArmedTest extends CardTestCommander4Players {

    @Test
    public void testTargetPlayerMustBeOtherThanController() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Lucy MacLean, Positively Armed");
        addCard(Zone.HAND, playerA, "Elemental Summoning");

        addTarget(playerA, playerB);
        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elemental Summoning");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Elemental Token", 1);
        assertPermanentCount(playerA, "Elemental Token", 1);
    }

    @Test
    public void testTargetPlayerMustBeOtherThanControllerOpponentCast() {
        addCard(Zone.BATTLEFIELD, playerD, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Lucy MacLean, Positively Armed");
        addCard(Zone.HAND, playerD, "Elemental Summoning");

        addTarget(playerA, playerA);
        setChoice(playerA, true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Elemental Summoning");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerD, "Elemental Token", 1);
        assertPermanentCount(playerA, "Elemental Token", 1);
    }

    @Test
    public void testMaySkip() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Lucy MacLean, Positively Armed");
        addCard(Zone.HAND, playerA, "Elemental Summoning");

        addTarget(playerA, playerB);
        setChoice(playerA, false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elemental Summoning");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Elemental Token", 1);
        assertPermanentCount(playerB, "Elemental Token", 0);
    }
}
