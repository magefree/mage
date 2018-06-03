
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BuybackTest extends CardTestPlayerBase {

    /**
     * Tests boosting on being blocked
     */
    @Test
    public void testNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // Buyback {4} (You may pay an additional as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Target creature gets +2/+2 until end of turn.
        addCard(Zone.HAND, playerA, "Elvish Fury", 1); // Instant  {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Fury", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertHandCount(playerA, "Elvish Fury", 1);
    }

    /**
     * It seems that a spell with it's buyback cost paid returned to hand after
     * it fizzled (by failing to target) when it should go to graveyard.
     *
     * "Q: If I pay a spell's buyback cost, but it fizzles, do I get the card
     * back anyway? A: If you pay a buyback cost, you would get the card back
     * during the spell's resolution. So if it never resolves (i.e., something
     * counters it or it fizzles against all of its targets), you don't get the
     * card back."
     */
    @Test
    public void testBuybackSpellFizzles() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // Buyback {4} (You may pay an additional as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Target creature gets +2/+2 until end of turn.
        addCard(Zone.HAND, playerA, "Elvish Fury", 1); // Instant  {G}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Boomerang", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Fury", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Boomerang", "Silvercoat Lion", "Elvish Fury");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Boomerang", 1);
        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerA, "Elvish Fury", 0);
        assertGraveyardCount(playerA, "Elvish Fury", 1);
    }

    @Test
    public void testBuybackSpellWasCountered() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // Buyback {4} (You may pay an additional as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Target creature gets +2/+2 until end of turn.
        addCard(Zone.HAND, playerA, "Elvish Fury", 1); // Instant  {G}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elvish Fury", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", "Elvish Fury", "Elvish Fury");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Counterspell", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertHandCount(playerA, "Elvish Fury", 0);
        assertGraveyardCount(playerA, "Elvish Fury", 1);
    }
}
