package org.mage.test.cards.single.uds;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author PurpleCrowbar
 */
public class OpalescenceTest extends CardTestPlayerBase {

    /**
     * Test that Opalescence is applying its effect
     */
    @Test
    public void testOpalescenceApplies() {
        // 1 CMC 1/2 enchantment creature
        addCard(Zone.BATTLEFIELD, playerA, "Dockside Chef", 1);
        // 1 CMC non-creature enchantment
        addCard(Zone.BATTLEFIELD, playerA, "Alms", 1);
        // Each other non-Aura enchantment is a creature in addition to its other types and has base power and base toughness each equal to its mana value.
        addCard(Zone.BATTLEFIELD, playerA, "Opalescence", 1);

        // 2 CMC non-creature enchantment
        addCard(Zone.BATTLEFIELD, playerB, "Amok", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Dockside Chef", 1, 1);
        assertType("Alms", CardType.CREATURE, true);
        assertPowerToughness(playerA, "Alms", 1, 1);
        assertType("Amok", CardType.CREATURE, true);
        assertPowerToughness(playerB, "Amok", 2, 2);
    }

    /**
     * Test that Opalescence's effect ends when it leaves the battlefield
     */
    @Test
    public void testOpalescenceEffectEnds() {
        // 1 CMC 1/2 enchantment creature
        addCard(Zone.BATTLEFIELD, playerA, "Dockside Chef", 1);
        // 1 CMC non-creature enchantment
        addCard(Zone.BATTLEFIELD, playerA, "Alms", 1);
        // Each other non-Aura enchantment is a creature in addition to its other types and has base power and base toughness each equal to its mana value.
        addCard(Zone.HAND, playerA, "Opalescence", 1);
        addCard(Zone.HAND, playerA, "Vindicate", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 7);

        // 2 CMC non-creature enchantment
        addCard(Zone.BATTLEFIELD, playerB, "Amok", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Opalescence");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Vindicate", "Opalescence");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertType("Dockside Chef", CardType.CREATURE, true);
        assertPowerToughness(playerA, "Dockside Chef", 1, 2);
        assertType("Alms", CardType.CREATURE, false);
        assertType("Amok", CardType.CREATURE, false);
    }
}
