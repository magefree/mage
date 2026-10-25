package org.mage.test.cards.single.mir;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.SandGolem Sand Golem} {5}, 3/3 artifact creature
 * When a spell or ability an opponent controls causes you to discard Sand Golem, return Sand Golem from your
 * graveyard to the battlefield with a +1/+1 counter on it at the beginning of the next end step.
 *
 * @author notgreat, Claude Opus 5.5
 */
public class SandGolemTest extends CardTestPlayerBase {

    private static final String golem = "Sand Golem";

    /**
     * The discard trigger sees the card in the graveyard, so the return at the end step finds the same object.
     */
    @Test
    public void testReturnsAfterOpponentMakesYouDiscardIt() {
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Mind Rot"); // {2}{B} sorcery: target player discards two cards
        addCard(Zone.HAND, playerA, golem);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Mind Rot", playerA);
        checkGraveyardCount("discarded", 2, PhaseStep.POSTCOMBAT_MAIN, playerA, golem, 1);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, golem, 1);
        assertPowerToughness(playerA, golem, 4, 4);
    }
}
