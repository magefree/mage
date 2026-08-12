package org.mage.test.cards.single.hob;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class StingBilbosSwordTest extends CardTestPlayerBase {

    private static final String STING = "Sting, Bilbo's Sword";
    private static final String LION = "Silvercoat Lion";
    private static final String BEARS = "Grizzly Bears";

    @Test
    public void testEtbAddsHoneByOppCreaturesAndAttaches() {
        addCard(Zone.HAND, playerA, STING);
        addCard(Zone.BATTLEFIELD, playerA, LION);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.BATTLEFIELD, playerB, BEARS, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, STING);
        addTarget(playerA, LION);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(STING, CounterType.HONE, 2);
        assertAttachedTo(playerA, STING, LION, true);
        assertPowerToughness(playerA, LION, 4, 2);
    }

    @Test
    public void testEtbAddsHoneEvenIfNoAttachTarget() {
        addCard(Zone.HAND, playerA, STING);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.BATTLEFIELD, playerB, BEARS, 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, STING);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(STING, CounterType.HONE, 3);
    }
}
