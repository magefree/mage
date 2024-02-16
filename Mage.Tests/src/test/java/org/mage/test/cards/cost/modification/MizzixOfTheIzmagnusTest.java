
package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class MizzixOfTheIzmagnusTest extends CardTestPlayerBase {

    @Test
    public void testSpellsFixedCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Whenever you cast an instant or sorcery spell with converted mana cost greater than the number of experience counters you have, you get an experience counter.
        // Instant and sorcery spells you cast cost {1} less to cast for each experience counter you have.
        addCard(Zone.BATTLEFIELD, playerA, "Mizzix of the Izmagnus");
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        // Incinerate deals 3 damage to any target. A creature dealt damage this way can't be regenerated this turn.
        addCard(Zone.HAND, playerA, "Incinerate"); // {1}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Incinerate", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Incinerate", 1);

        assertCounterCount(playerA, CounterType.EXPERIENCE, 2);
        assertLife(playerA, 20);
        assertLife(playerB, 14);

    }

    /**
     * Does not reduce the cost of {X} spells
     */
    @Test
    public void testSpellsVariableCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Whenever you cast an instant or sorcery spell with converted mana cost greater than the number of experience counters you have, you get an experience counter.
        // Instant and sorcery spells you cast cost {1} less to cast for each experience counter you have.
        addCard(Zone.BATTLEFIELD, playerA, "Mizzix of the Izmagnus");
        // Blaze deals X damage to any target.
        addCard(Zone.HAND, playerA, "Blaze", 2); // Sorcery - {X}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blaze", playerB);
        setChoice(playerA, "X=1");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blaze", playerB);
        setChoice(playerA, "X=2");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Blaze", 2);

        assertCounterCount(playerA, CounterType.EXPERIENCE, 2);
        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }

    /**
     * Test to reduce Buyback costs
     */
    @Test
    public void testSpellsBuybackCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Whenever you cast an instant or sorcery spell with converted mana cost greater than the number of experience counters you have, you get an experience counter.
        // Instant and sorcery spells you cast cost {1} less to cast for each experience counter you have.
        addCard(Zone.BATTLEFIELD, playerA, "Mizzix of the Izmagnus");// 2/2
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        // Target creature gets +3/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Seething Anger"); // {R} Buyback {3}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Seething Anger", "Mizzix of the Izmagnus");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertHandCount(playerA, "Seething Anger", 1);

        assertPowerToughness(playerA, "Mizzix of the Izmagnus", 5, 2);
        assertCounterCount(playerA, CounterType.EXPERIENCE, 1);
        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }

    /**
     * Test to reduce Flashback costs
     */
    @Test
    public void testReduceFlashbackCosts() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // Whenever you cast an instant or sorcery spell with converted mana cost greater than the number of experience counters you have, you get an experience counter.
        // Instant and sorcery spells you cast cost {1} less to cast for each experience counter you have.
        addCard(Zone.BATTLEFIELD, playerA, "Mizzix of the Izmagnus");// 2/2

        // Engulfing Flames deals 1 damage to target creature. It can't be regenerated this turn.
        // Flashback {3}{R} (You may cast this card from your graveyard for its flashback cost. Then exile it.)
        addCard(Zone.HAND, playerA, "Engulfing Flames"); // {R}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");// 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Engulfing Flames", "Silvercoat Lion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Engulfing Flames", 0);
        assertExileCount(playerA, "Engulfing Flames", 1);

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);

        assertCounterCount(playerA, CounterType.EXPERIENCE, 1);

    }
}
