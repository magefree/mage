package org.mage.test.cards.single.nec;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.m.MyojinOfGrimBetrayal Myojin of Grim Betrayal} {5}{B}{B}{B}{B}
 * Legendary Creature - Spirit 5/2
 * Myojin of Grim Betrayal enters the battlefield with an indestructible counter on it if you cast it from your hand.
 * Remove an indestructible counter from Myojin of Grim Betrayal:
 *  Put onto the battlefield under your control all creature cards in all graveyards that were put there from anywhere this turn.
 *
 * Issue #11721 - The second ability of Myojin was only returning
 * creatures that were put into the graveyard from the battlefield.
 *
 * @author DominionSpy
 */
public class MyojinOfGrimBetrayalTest extends CardTestPlayerBase {

    @Test
    public void test_MyojinOfGrimBetrayal() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9 + 2 + 1 + 2 + 4);
        addCard(Zone.BATTLEFIELD, playerA, "Vampire Hexmage");
        addCard(Zone.BATTLEFIELD, playerA, "Thrull Wizard");
        addCard(Zone.EXILED, playerB, "Llanowar Elves");

        addCard(Zone.HAND, playerA, "Myojin of Grim Betrayal");
        addCard(Zone.HAND, playerA, "Archfiend of Ifnir");
        addCard(Zone.HAND, playerA, "Banehound");
        addCard(Zone.HAND, playerA, "Mind Raker");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Myojin of Grim Betrayal");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Creature put into graveyard from battlefield (Vampire Hexmage)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice", "Thrull Wizard");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Creature put into graveyard from hand (Archfiend of Ifnir)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Creature put into graveyard from stack (Banehound)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Banehound");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}", "Banehound");
        setChoice(playerA, false);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        showExile("exile b", 1, PhaseStep.PRECOMBAT_MAIN, playerB);
        showGraveyard("grave b", 1, PhaseStep.PRECOMBAT_MAIN, playerB);

        // Creature put into graveyard from exile (Llanowar Elves)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Raker");
        setChoice(playerA, true);
        addTarget(playerA, "Llanowar Elves");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Remove");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Vampire Hexmage", 1);
        assertPermanentCount(playerA, "Archfiend of Ifnir", 1);
        assertPermanentCount(playerA, "Banehound", 1);
        assertPermanentCount(playerA, "Llanowar Elves", 1);
    }
}
