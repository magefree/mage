package org.mage.test.cards.single.mmq;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ChoArrimAlchemistTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.ChoArrimAlchemist Cho-Arrim Alchemist} {W}
     * Creature — Human Spellshaper
     * {1}{W}{W}, {T}, Discard a card: The next time a source of your choice would deal damage to you this turn, prevent that damage. You gain life equal to the damage prevented this way.
     * 1/1
     */
    private static final String alchemist = "Cho-Arrim Alchemist";

    @Test
    public void test_DamageOnCreature_NoPrevent() {
        addCard(Zone.BATTLEFIELD, playerA, alchemist, 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // to discard
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Caelorna, Coral Tyrant"); // 0/8
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}{W}");
        setChoice(playerA, "Lightning Bolt"); // discard to activate
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);
        block(2, playerA, "Caelorna, Coral Tyrant", "Goblin Piker");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerA, "Caelorna, Coral Tyrant", 2); // no prevent
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void test_DamageOnYou_Prevent() {
        addCard(Zone.BATTLEFIELD, playerA, alchemist, 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // to discard
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}{W}");
        setChoice(playerA, "Lightning Bolt"); // discard to activate
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 2);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void test_DoubleStrike_Prevent_ThenConsumedAndNoPrevent() {
        addCard(Zone.BATTLEFIELD, playerA, alchemist, 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // to discard
        addCard(Zone.BATTLEFIELD, playerB, "Blade Historian", 1); // 2/3 "Attacking creatures you control have double strike."
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}{W}");
        setChoice(playerA, "Lightning Bolt"); // discard to activate
        setChoice(playerA, "Blade Historian"); // source to prevent from

        attack(2, playerB, "Blade Historian", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertTapped("Blade Historian", true);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);
    }
}
