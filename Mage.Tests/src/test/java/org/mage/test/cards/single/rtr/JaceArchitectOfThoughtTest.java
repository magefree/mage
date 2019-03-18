
package org.mage.test.cards.single.rtr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Jace, Architect of Thought {2}{U}{U} Planeswalker — Jace Loyalty 4 +1: Until
 * your next turn, whenever a creature an opponent controls attacks, it gets
 * -1/-0 until end of turn. -2: Reveal the top three cards of your library. An
 * opponent separates those cards into two piles. Put one pile into your hand
 * and the other on the bottom of your library in any order. -8: For each
 * player, search that player's library for a nonland card and exile it, then
 * that player shuffles their library. You may cast those cards without
 * paying their mana costs.
 *
 * @author LevelX2
 */
public class JaceArchitectOfThoughtTest extends CardTestPlayerBase {

    @Test
    public void testAbility1normal() {
        addCard(Zone.BATTLEFIELD, playerA, "Jace, Architect of Thought");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.");

        attack(2, playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertCounterCount("Jace, Architect of Thought", CounterType.LOYALTY, 5);
        assertPowerToughness(playerB, "Silvercoat Lion", 1, 2);

        assertLife(playerA, 19);
        assertLife(playerB, 20);

    }

    @Test
    public void testAbilit1lastOnlyUntilNextTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Jace, Architect of Thought");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.");

        attack(2, playerB, "Silvercoat Lion");
        attack(4, playerB, "Silvercoat Lion");

        setStopAt(4, PhaseStep.END_COMBAT);
        execute();

        assertCounterCount("Jace, Architect of Thought", CounterType.LOYALTY, 5);
        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);

        assertLife(playerA, 17);
        assertLife(playerB, 20);

    }
    /*
     Ability 1 has still to trigger next turn if used also if Jace left the battlefield.
     */

    @Test
    public void testAbility1AfterJacesWasExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Jace, Architect of Thought");

        // Sorcery {R}{B}
        // Destroy target creature or planeswalker.
        addCard(Zone.HAND, playerB, "Dreadbore");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        // +1: Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1: Until your next turn");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Dreadbore", "Jace, Architect of Thought");
        attack(2, playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Jace, Architect of Thought", 0);
        assertPowerToughness(playerB, "Silvercoat Lion", 1, 2);

    }

}
