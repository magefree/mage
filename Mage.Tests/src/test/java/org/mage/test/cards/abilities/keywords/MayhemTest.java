package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class MayhemTest extends CardTestPlayerBase {

    private static final String islanders = "Spider-Islanders";

    @Test
    public void testCastRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, islanders);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, islanders);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, islanders, 1);
    }

    private static final String imp = "Putrid Imp";

    @Test
    public void testCastDiscarded() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, imp);
        addCard(Zone.HAND, playerA, islanders);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, islanders);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, islanders + " with Mayhem");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, islanders, 1);
    }

    @Test
    public void testCantCastGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.GRAVEYARD, playerA, islanders);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, islanders + " with Mayhem");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        try {
            execute();
        } catch (Throwable e) {
            Assert.assertEquals(
                    "Should fail to be able to cast " + islanders + " with mayhem as it wasn't discarded this turn",
                    "Can't find ability to activate command: Cast " + islanders + " with Mayhem", e.getMessage()
            );
        }
    }

    @Test
    public void testCantCastDiscardedPreviously() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, imp);
        addCard(Zone.HAND, playerA, islanders);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard");
        setChoice(playerA, islanders);
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, islanders + " with Mayhem");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        try {
            execute();
        } catch (Throwable e) {
            Assert.assertEquals(
                    "Should fail to be able to cast " + islanders + " with mayhem as it wasn't discarded this turn",
                    "Can't find ability to activate command: Cast " + islanders + " with Mayhem", e.getMessage()
            );
        }
    }
}
