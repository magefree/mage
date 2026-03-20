package org.mage.test.cards.single.dsc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class AminatouVeilPiercerTest extends CardTestPlayerBase {

    private static final String aminatou = "Aminatou, Veil Piercer";
    private static final String mists = "Reach Through Mists";
    private static final String mastery = "Ugin's Mastery";

    @Test
    public void testUginsMastery() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, aminatou);
        addCard(Zone.LIBRARY, playerA, mastery, 3);
        addCard(Zone.HAND, playerA, mists);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mists);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, mastery, 1);
        assertGraveyardCount(playerA, mists, 1);
    }

    private static final String saga = "Urza's Saga";

    @Test
    public void testUrzasSaga() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, aminatou);
        addCard(Zone.LIBRARY, playerA, saga, 3);
        addCard(Zone.HAND, playerA, mists);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mists);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, saga, 0);
        assertHandCount(playerA, saga, 1);
        assertGraveyardCount(playerA, mists, 1);
    }
}
