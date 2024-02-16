package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TheMillenniumCalendarTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TheMillenniumCalendar} <br>
     * The Millennium Calendar {1} <br>
     * Legendary Artifact <br>
     * Whenever you untap one or more permanents during your untap step, put that many time counters on The Millennium Calendar. <br>
     * {2}, {T}: Double the number of time counters on The Millennium Calendar. <br>
     * When there are 1,000 or more time counters on The Millennium Calendar, sacrifice it and each opponent loses 1,000 life. <br>
     */
    private static final String calendar = "The Millennium Calendar";

    @Test
    public void test_untap_effect_not_triggering() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, calendar);
        addCard(Zone.BATTLEFIELD, playerA, "Aphetto Alchemist"); // {T}: Untap target artifact or creature.

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{T}: Untap target artifact or creature.", "Aphetto Alchemist");

        setStopAt(1, PhaseStep.DRAW);
        execute();

        assertCounterCount(calendar, CounterType.TIME, 0);
    }

    @Test
    public void test_untap_trigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, calendar);
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin", 10);

        for (int i = 0; i < 10; ++i) {
            attack(1, playerA, "Raging Goblin", playerB);
        }

        setStopAt(3, PhaseStep.DRAW);
        execute();

        assertCounterCount(calendar, CounterType.TIME, 10);
        assertLife(playerB, 20 - 10);
    }
}
