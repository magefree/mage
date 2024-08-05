package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class SpreeTest extends CardTestPlayerBase {

    private static final String accident = "Unfortunate Accident";
    // Instant {B}
    // Spree
    // + {2}{B} -- Destroy target creature.
    // + {1} -- Create a 1/1 Mercenary token

    private static final String bear = "Grizzly Bears";

    @Test
    public void testFirstMode() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1 + 3);
        addCard(Zone.BATTLEFIELD, playerA, bear, 1);
        addCard(Zone.HAND, playerA, accident);

        setModeChoice(playerA, "1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, accident, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bear, 1);
        assertPermanentCount(playerA, "Mercenary Token", 0);
        assertTappedCount("Swamp", true, 1 + 3);
    }

    @Test
    public void testSecondMode() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1 + 1);
        addCard(Zone.HAND, playerA, accident);

        setModeChoice(playerA, "2");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, accident);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Mercenary Token", 1);
        assertTappedCount("Swamp", true, 1 + 1);
    }

    @Test
    public void testBothModes() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1 + 3 + 1);
        addCard(Zone.BATTLEFIELD, playerA, bear, 1);
        addCard(Zone.HAND, playerA, accident);

        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, accident, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bear, 1);
        assertPermanentCount(playerA, "Mercenary Token", 1);
        assertTappedCount("Swamp", true, 1 + 3 + 1);
    }

    private static final String electromancer = "Goblin Electromancer";

    @Test
    public void testReduction() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1 + 3 + 1 - 1);
        addCard(Zone.BATTLEFIELD, playerA, bear, 1);
        addCard(Zone.BATTLEFIELD, playerA, electromancer, 1);
        addCard(Zone.HAND, playerA, accident);

        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, accident, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bear, 1);
        assertPermanentCount(playerA, "Mercenary Token", 1);
        assertTappedCount("Swamp", true, 1 + 3 + 1 - 1);
    }
}
