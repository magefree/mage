package org.mage.test.cards.digital;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ConjureTest extends CardTestPlayerBase {

    private static final String trainer = "Wingsteed Trainer";
    private static final String pegasus = "Stormfront Pegasus";
    private static final String aberration = "Boneyard Aberration";
    private static final String murder = "Murder";
    private static final String skeleton = "Reassembling Skeleton";

    @Test
    public void testConjureToHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, trainer);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, trainer);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, trainer, 1);
        assertHandCount(playerA, pegasus, 1);
    }

    @Test
    public void testConjureToHandAndCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, trainer);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, trainer, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pegasus);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, trainer, 1);
        assertPermanentCount(playerA, pegasus, 1);
    }

    @Test
    public void testAberration() {
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, aberration);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, aberration);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{1}{B}:");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, skeleton, 2);
        assertGraveyardCount(playerA, murder, 1);
        assertExileCount(playerA, aberration, 1);
        assertPermanentCount(playerA, skeleton, 1);
    }
}
