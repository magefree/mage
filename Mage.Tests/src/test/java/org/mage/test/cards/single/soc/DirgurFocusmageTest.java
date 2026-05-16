package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DirgurFocusmageTest extends CardTestPlayerBase {

    @Test
    public void testReducedHighManaValueInstantPreparesBraingeyser() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Dirgur Focusmage");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Jace's Ingenuity");
        addCard(Zone.LIBRARY, playerA, "Island", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Jace's Ingenuity");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Jace's Ingenuity", 1);
        assertHandCount(playerA, "Island", 3);
        assertExileCount(playerA, "Braingeyser", 1);
    }
}
