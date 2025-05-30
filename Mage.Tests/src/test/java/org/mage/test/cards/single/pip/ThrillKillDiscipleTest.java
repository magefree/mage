package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ThrillKillDiscipleTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.ThrillKillDisciple Thrill-Kill Disciple} {2}{R}
     * Creature — Human Mercenary
     * Squad—{1}, Discard a card. (As an additional cost to cast this spell, you may pay its squad cost any number of times. When this creature enters the battlefield, create that many tokens that are copies of it.)
     * When Thrill-Kill Disciple dies, create a Junk token.
     * 3/2
     */
    private static final String disciple = "Thrill-Kill Disciple";

    @Test
    public void test_SquadWithCombinedCost() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, disciple);
        addCard(Zone.HAND, playerA, "Taiga", 2); // For discard
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, disciple);
        setChoice(playerA, true); // yes to Squad first time
        setChoice(playerA, true); // yes to Squad second time
        setChoice(playerA, false); // no to Squad first time
        setChoice(playerA, "Taiga", 2); // Chosen to be discarded

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Taiga", 2);
        assertPermanentCount(playerA, disciple, 3);
        assertTappedCount("Mountain", true, 5);
    }
}
