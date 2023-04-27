package org.mage.test.cards.single.stx;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class StarPupilTest extends CardTestPlayerBase {

    private static final String pupil = "Star Pupil";
    private static final String murder = "Murder";
    private static final String lion = "Silvercoat Lion";

    @Test
    public void testPupil() {
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 4);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, pupil);
        addCard(Zone.HAND, playerA, murder);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pupil);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, murder, pupil);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(lion, CounterType.P1P1, 1);
    }
}
