package org.mage.test.cards.single.unf;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class EmbiggenTest extends CardTestPlayerBase {

    private static final String roar = "Relic's Roar";
    private static final String embiggen = "Embiggen";
    private static final String agent = "Blighted Agent";

    @Test
    public void testRoarFirst() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, agent);
        addCard(Zone.HAND, playerA, roar);
        addCard(Zone.HAND, playerA, embiggen);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, roar, agent, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, embiggen, agent, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, agent, 4 + 4 + 2, 3 + 4 + 2);
    }

    @Test
    public void testEmbiggenFirst() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, agent);
        addCard(Zone.HAND, playerA, roar);
        addCard(Zone.HAND, playerA, embiggen);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, embiggen, agent, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, roar, agent, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, agent, 4 + 3 + 1, 3 + 3 + 1);
    }
}
