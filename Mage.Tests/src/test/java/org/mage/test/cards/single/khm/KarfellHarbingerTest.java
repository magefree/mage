package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.k.KarfellHarbinger Karfell Harbinger}
 * {1}{U}
 * Creature — Zombie Wizard
 * {T}: Add {U}. Spend this mana only to foretell a card from your hand or cast an instant or sorcery spell.
 *
 * @author TheElk801
 */

public class KarfellHarbingerTest extends CardTestPlayerBase {

    /**
     * Test that spending the money on Foretell works.
     */
    @Test
    public void testForetellMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Karfell Harbinger");
        addCard(Zone.HAND, playerA, "Augury Raven");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fore");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertHandCount(playerA, "Augury Raven", 0);
    }

    /**
     * Test that spending the mana on an instant works.
     */
    @Test
    public void testSpellMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Karfell Harbinger");
        addCard(Zone.HAND, playerA, "Obsessive Search");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Obsessive Search");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Obsessive Search", 1);
    }

    /**
     * Test that you can't use the mana on something else.
     */
    @Test
    public void testOtherMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Karfell Harbinger");
        addCard(Zone.HAND, playerA, "Flying Men");

        checkPlayableAbility("can't cast flying man", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Flying", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Flying Men", 1);
    }
}
