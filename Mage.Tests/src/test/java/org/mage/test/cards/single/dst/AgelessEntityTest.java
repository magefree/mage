package org.mage.test.cards.single.dst;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AgelessEntityTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AgelessEntity Ageless Entity} {3}{G}{G}
     * Creature — Elemental
     * Whenever you gain life, put that many +1/+1 counters on Ageless Entity.
     * 4/4
     */
    private static final String entity = "Ageless Entity";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, entity);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "Dosan's Oldest Chant"); // You gain 6 life. Draw a card.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dosan's Oldest Chant");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 + 6);
        assertCounterCount(playerA, entity, CounterType.P1P1, 6);
    }

    @Test
    public void test_Lifelink() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, entity);
        addCard(Zone.BATTLEFIELD, playerA, "Child of Night"); // 2/1 lifelink

        attack(1, playerA, "Child of Night", playerB);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 2);
        assertCounterCount(playerA, entity, CounterType.P1P1, 2);
    }
}
