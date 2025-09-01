package org.mage.test.cards.single.rna;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class FontOfAgoniesTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.f.FontOfAgonies Font of Agonies} {B}
     * Enchantment
     * Whenever you pay life, put that many blood counters on this enchantment.
     * {1}{B}, Remove four blood counters from this enchantment: Destroy target creature.
     */
    private static final String font = "Font of Agonies";

    @Test
    public void test_Fetchland() {
        addCard(Zone.BATTLEFIELD, playerA, font, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Arid Mesa", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");
        addTarget(playerA, "Mountain");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 1);
        assertCounterCount(playerA, font, CounterType.BLOOD, 1);
    }

    @Test
    public void test_Pay2() {
        addCard(Zone.BATTLEFIELD, playerA, font, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Book of Rass", 1); // {2}, Pay 2 life: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 2);
        assertCounterCount(playerA, font, CounterType.BLOOD, 2);
    }
}
