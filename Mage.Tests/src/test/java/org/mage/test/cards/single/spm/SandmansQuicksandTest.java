package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class SandmansQuicksandTest extends CardTestPlayerBase {

    /*
    Sandman's Quicksand
    {1}{B}{B}
    Sorcery
    Mayhem {3}{B}
    All creatures get -2/-2 until end of turn. If this spell's mayhem cost was paid, creatures your opponents control get -2/-2 until end of turn instead.
    */
    private static final String sandmansQuicksand = "Sandman's Quicksand";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear

    2/2
    */
    private static final String bearCub = "Bear Cub";

    /*
    Thought Courier
    {1}{U}
    Creature - Human Wizard
    {tap}: Draw a card, then discard a card.
    1/1
    */
    private static final String thoughtCourier = "Thought Courier";

    @Test
    public void testSandmansQuicksand() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, sandmansQuicksand);
        addCard(Zone.BATTLEFIELD, playerA, bearCub);
        addCard(Zone.BATTLEFIELD, playerB, bearCub);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sandmansQuicksand);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bearCub, 1);
        assertGraveyardCount(playerB, bearCub, 1);
    }

    @Test
    public void testSandmansQuicksandMayhem() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, sandmansQuicksand);
        addCard(Zone.BATTLEFIELD, playerA, bearCub);
        addCard(Zone.BATTLEFIELD, playerA, thoughtCourier);
        addCard(Zone.BATTLEFIELD, playerB, bearCub);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw");
        setChoice(playerA, sandmansQuicksand);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sandmansQuicksand + " with Mayhem");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bearCub, 0);
        assertGraveyardCount(playerB, bearCub, 1);
    }
}