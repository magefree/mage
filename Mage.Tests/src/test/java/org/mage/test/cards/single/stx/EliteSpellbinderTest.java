package org.mage.test.cards.single.stx;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class EliteSpellbinderTest extends CardTestPlayerBase {

    // reported bug #11520
    private static final String spellbinder = "Elite Spellbinder";
    // When Elite Spellbinder enters the battlefield, look at target opponent’s hand.
    // You may exile a nonland card from it. For as long as that card remains exiled, its owner may play it.
    // A spell cast this way costs {2} more to cast.

    private static final String cascader = "Maelstrom Colossus"; // 8 mana creature with cascade
    private static final String bolt = "Lightning Bolt";

    @Test
    public void testCostIncreaseDoesntAffectCascade() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, spellbinder);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 10);
        addCard(Zone.HAND, playerB, cascader);
        addCard(Zone.LIBRARY, playerB, bolt); // to be cascaded into
        addCard(Zone.LIBRARY, playerB, "Shock"); // top of library, gets drawn
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spellbinder);
        addTarget(playerA, playerB); // target opponent's hand
        setChoice(playerA, cascader); // exiled

        checkExileCount("cascader", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, cascader, 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, cascader);
        setChoice(playerB, true); // yes to cascade
        addTarget(playerB, playerA); // bolt target

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Wastes", true, 10);
        assertLife(playerA, 17);
    }
}
