package org.mage.test.cards.cost.splitcards;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SplitCardsReboundTest extends CardTestPlayerBase {

    @Ignore // Cast Through Time is broken on Split Cards.
    @Test
    public void test_FireIce() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Cast Through Time"); // Instant and sorcery spells you control have rebound.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, "Fire // Ice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fire");
        addTargetAmount(playerA, playerB, 2);

        checkExileCount("in exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fire // Ice", 1);

        setChoice(playerA, true); // yes to Rebound trigger
        setChoice(playerA, "Cast Ice"); // choose the side to cast
        addTarget(playerA, "Mountain");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
        assertGraveyardCount(playerA, "Fire // Ice", 1);
        assertTapped("Mountain", true);
        assertTapped("Swamp", false);
    }

}
