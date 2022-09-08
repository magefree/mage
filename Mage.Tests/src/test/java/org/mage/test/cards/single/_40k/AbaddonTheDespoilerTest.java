package org.mage.test.cards.single._40k;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * During your turn, spells you cast from your hand with mana value X or less have cascade,
 * where X is the total amount of life your opponents have lost this turn.
 * @author Alex-Vasile
 */
public class AbaddonTheDespoilerTest extends CardTestPlayerBase {
    private static final String abaddonTheDespoiler = "Abaddon the Despoiler";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9490
     */
    @Test
    public void cascadeWorks() {
        String lightningBolt = "Lightning Bolt";
        addCard(Zone.BATTLEFIELD, playerA, abaddonTheDespoiler);
        addCard(Zone.HAND, playerA, lightningBolt, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.LIBRARY, playerA, "Crimson Kobolds"); // {0}

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lightningBolt, playerB);
        setChoice(playerA, true); // Cast with Cascade

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        assertLife(playerB, 20 - 2*3);
        assertPermanentCount(playerA, "Crimson Kobolds", 1);
    }
}
