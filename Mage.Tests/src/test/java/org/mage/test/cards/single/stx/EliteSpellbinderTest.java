package org.mage.test.cards.single.stx;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class EliteSpellbinderTest extends CardTestPlayerBase {

    @Test
    public void testEliteSpellbinder() {
        addCard(Zone.HAND, playerA, "Elite Spellbinder"); // {3}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        addCard(Zone.HAND, playerB, "Divination");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Spellbinder");
        addTarget(playerA, playerB);
        setChoice(playerA, "Yes");
        setChoice(playerA, "Divination");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Divination");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        //assertTappedCount("Island", true, 5);
    }

}
