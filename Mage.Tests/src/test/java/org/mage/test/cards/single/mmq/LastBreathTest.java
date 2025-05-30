package org.mage.test.cards.single.mmq;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class LastBreathTest extends CardTestPlayerBase {

    // Exile target creature with power 2 or less. Its controller gains 4 life.
    private static final String lastBreath = "Last Breath"; // 1W Instant
    private static final String drake = "Wind Drake";

    @Test
    public void testControllerGains() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, lastBreath);
        addCard(Zone.BATTLEFIELD, playerB, drake);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lastBreath, drake);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, lastBreath, 1);
        assertExileCount(playerB, drake, 1);
        assertLife(playerA, 20);
        assertLife(playerB, 24);
    }

    @Test
    public void testControllerChanged() {
        String threaten = "Act of Treason";

        addCard(Zone.HAND, playerA, lastBreath);
        addCard(Zone.BATTLEFIELD, playerB, drake);
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 5);
        addCard(Zone.HAND, playerA, threaten);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, threaten, drake);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, lastBreath, drake);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, lastBreath, 1);
        assertExileCount(playerB, drake, 1);
        assertLife(playerA, 24);
        assertLife(playerB, 20);
    }
}
