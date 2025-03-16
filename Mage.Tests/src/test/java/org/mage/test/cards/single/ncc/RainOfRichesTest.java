package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class RainOfRichesTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.r.RainOfRiches Rain of Riches} {3}{R}{R}
     * Enchantment
     * When Rain of Riches enters the battlefield, create two Treasure tokens.
     * The first spell you cast each turn that mana from a Treasure was spent to cast has cascade.
     */
    private static final String rain = "Rain of Riches";

    @Test
    public void test_Using_Treasures() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, rain, 1);
        addCard(Zone.HAND, playerA, "Goblin Piker", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.LIBRARY, playerA, "Elite Vanguard", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rain, true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Goblin Piker");
        setChoice(playerA, "Red"); // choice for treasure mana
        setChoice(playerA, "Red"); // choice for treasure mana
        setChoice(playerA, true); // yes to Cascade

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Goblin Piker", 1);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
    }

    @Test
    public void test_Not_Using_Treasures() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, rain, 1);
        addCard(Zone.HAND, playerA, "Goblin Piker", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.LIBRARY, playerA, "Elite Vanguard", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rain, true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Goblin Piker");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Goblin Piker", 1);
        assertPermanentCount(playerA, "Elite Vanguard", 0);
    }

    @Test
    public void test_Cast_Two_Using_Treasures() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, rain, 1);
        addCard(Zone.HAND, playerA, "Raging Goblin", 2); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.LIBRARY, playerA, "Memnite", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rain, true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Raging Goblin");
        setChoice(playerA, "Red"); // choice for treasure mana
        setChoice(playerA, true); // yes to Cascade
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Raging Goblin");
        setChoice(playerA, "Red"); // choice for treasure mana

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Raging Goblin", 2);
        assertPermanentCount(playerA, "Memnite", 1);
    }

    @Test
    public void test_Cast_SomethingElse_Then_Cast_Using_Treasure() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, rain, 1);
        addCard(Zone.HAND, playerA, "Raging Goblin", 2); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.LIBRARY, playerA, "Memnite", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rain, true);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Raging Goblin", true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Raging Goblin");
        setChoice(playerA, "Red"); // choice for treasure mana
        setChoice(playerA, true); // yes to Cascade

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Raging Goblin", 2);
        assertPermanentCount(playerA, "Memnite", 1);
    }
}