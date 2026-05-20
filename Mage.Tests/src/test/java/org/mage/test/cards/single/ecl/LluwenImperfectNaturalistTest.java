package org.mage.test.cards.single.ecl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LluwenImperfectNaturalistTest extends CardTestPlayerBase {

    @Test
    public void testBasic() {
        skipInitShuffling();

        addCard(Zone.LIBRARY, playerA, "Balduvian Bears");
        addCard(Zone.HAND, playerA, "Lluwen, Imperfect Naturalist");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lluwen, Imperfect Naturalist");
        setChoice(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLibraryCount(playerA, "Balduvian Bears", 1);
    }

    @Test(expected = AssertionError.class)
    public void testCards() {
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.HAND, playerA, "Lluwen, Imperfect Naturalist");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lluwen, Imperfect Naturalist");
        setChoice(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLibraryCount(playerA, "Balduvian Bears", 1);
    }

}
