package org.mage.test.cards.single.c17;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class KindredDominanceTest extends CardTestPlayerBase {

    private static final String dominance = "Kindred Dominance";
    private static final String elf = "Cylian Elf";
    private static final String lion = "Silvercoat Lion";

    /**
     * #14392 — NPE when SubType.byDescription(choiceKey) is null.
     */
    @Test
    public void testDestroysCreaturesNotOfChosenType() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 7);
        addCard(Zone.BATTLEFIELD, playerA, elf);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, dominance);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dominance);
        setChoice(playerA, "Elf");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, dominance, 1);
        assertPermanentCount(playerA, elf, 1);
        assertPermanentCount(playerA, lion, 0);
        assertGraveyardCount(playerA, lion, 1);
    }
}
