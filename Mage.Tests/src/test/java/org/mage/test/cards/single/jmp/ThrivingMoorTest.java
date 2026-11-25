package org.mage.test.cards.single.jmp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author DarioBarrabinoDev
 */
public class ThrivingMoorTest extends CardTestPlayerBase {

    @Test
    public void test_EntersTappedAndChosenColorWorks() {
        // This land enters tapped. As it enters, choose a color other than black.
        // {T}: Add {B} or one mana of the chosen color.
        addCard(Zone.HAND, playerA, "Thriving Moor");
        addCard(Zone.HAND, playerA, "Llanowar Elves"); // {G}

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thriving Moor");
        setChoice(playerA, "Green");
        checkPermanentTapped("enters tapped", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thriving Moor", true, 1);

        // only mana source is the Moor, so green must be the chosen color
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Llanowar Elves", 1);
    }
}
