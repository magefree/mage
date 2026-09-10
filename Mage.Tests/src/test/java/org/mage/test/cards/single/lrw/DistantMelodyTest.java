package org.mage.test.cards.single.lrw;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DistantMelodyTest extends CardTestPlayerBase {

    private static final String melody = "Distant Melody";
    private static final String elf = "Cylian Elf";
    private static final String lion = "Silvercoat Lion";

    /**
     * #14392 — NPE when SubType.byDescription(choiceKey) is null.
     */
    @Test
    public void testDrawsForChosenCreatureType() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, elf, 2);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, melody);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, melody);
        setChoice(playerA, "Elf");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, melody, 1);
        assertHandCount(playerA, 2);
        assertPermanentCount(playerA, elf, 2);
        assertPermanentCount(playerA, lion, 1);
    }
}
