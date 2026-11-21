package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SamutSplitSecondTest extends CardTestPlayerBase {

    @Test
    public void testInstantGainsSplitSecond() {
        addCard(Zone.BATTLEFIELD, playerA, "Samut, Tyrant of Naktamun");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Shock");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);

        try {
            execute();
            Assert.fail("expected failure to cast Counterspell");
        } catch (AssertionError e) {
            Assert.assertTrue(e.getMessage(), e.getMessage().contains("Can't find ability to activate command: Cast Counterspell$target=Shock"));
        }
    }

    @Test
    public void testAdventureSpellGainsSplitSecond() {
        addCard(Zone.BATTLEFIELD, playerA, "Samut, Tyrant of Naktamun");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Brazen Borrower");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Daze");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Petty Theft", "Balduvian Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Daze", "Petty Theft");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);

        try {
            execute();
            Assert.fail("expected failure to cast Daze");
        } catch (AssertionError e) {
            Assert.assertTrue(e.getMessage(), e.getMessage().contains("Can't find ability to activate command: Cast Daze$target=Petty Theft"));
        }
    }
}
