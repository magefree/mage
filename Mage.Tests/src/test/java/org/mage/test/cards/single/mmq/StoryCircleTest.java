package org.mage.test.cards.single.mmq;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class StoryCircleTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.StoryCircle Story Circle} {1}{W}{W}
     * Enchantment
     * As this enchantment enters, choose a color.
     * {W}: The next time a source of your choice of the chosen color would deal damage to you this turn, prevent that damage.
     */
    private static final String circle = "Story Circle";

    @Test
    public void test_DamageOnCreature_NoPrevent() {
        addCard(Zone.HAND, playerA, circle, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Caelorna, Coral Tyrant"); // 0/8
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, circle);
        setChoice(playerA, "Red"); // color chosen

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}");
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);
        block(2, playerA, "Caelorna, Coral Tyrant", "Goblin Piker");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerA, "Caelorna, Coral Tyrant", 2); // no prevent
        assertLife(playerB, 20);
        assertTappedCount("Plains", true, 4);
    }

    @Test
    public void test_DamageOnYou_Prevent() {
        addCard(Zone.HAND, playerA, circle, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, circle);
        setChoice(playerA, "Red"); // color chosen

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}");
        setChoice(playerA, "Goblin Piker"); // source to prevent from

        attack(2, playerB, "Goblin Piker", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertTappedCount("Plains", true, 4);
    }

    @Test
    public void test_DoubleStrike_Prevent_ThenConsumedAndNoPrevent() {
        addCard(Zone.HAND, playerA, circle, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Blade Historian", 1); // 2/3 "Attacking creatures you control have double strike."
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, circle);
        setChoice(playerA, "Red"); // color chosen

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}");
        setChoice(playerA, "Blade Historian"); // source to prevent from

        attack(2, playerB, "Blade Historian", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 2);
        assertTappedCount("Plains", true, 4);
    }

    @Test
    public void test_NoSourceOfChosenColor_NoPrevent() {
        addCard(Zone.HAND, playerA, circle, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, circle);
        setChoice(playerA, "Green"); // color chosen

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}");
        // no valid choice

        attack(2, playerB, "Goblin Piker", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 2);
        assertTappedCount("Plains", true, 4);
    }

    @Test
    public void test_SpellDamageOnYou_Prevent() {
        addCard(Zone.HAND, playerA, circle, 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, circle);
        setChoice(playerA, "Red"); // color chosen

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}", null, "Lightning Bolt");
        setChoice(playerA, "Lightning Bolt"); // source to prevent from

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertTappedCount("Plains", true, 4);
    }

    @Test
    public void test_SpellNotChosenColorDamageOnYou_NoPrevent() {
        addCard(Zone.HAND, playerA, circle, 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, circle);
        setChoice(playerA, "Green"); // color chosen

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}", null, "Lightning Bolt");
        // no valid choice

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 3);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertTappedCount("Plains", true, 4);
    }

    @Test
    public void test_TriggerDamageOnYou_Prevent() {
        addCard(Zone.HAND, playerA, circle, 1);
        addCard(Zone.HAND, playerB, "Blisterstick Shaman", 1); // etb deals 1 to any target
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, circle);
        setChoice(playerA, "Red"); // color chosen

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Blisterstick Shaman");
        addTarget(playerB, playerA); // target for Shaman trigger
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}", null, "stack ability (When");
        setChoice(playerA, "Blisterstick Shaman");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertPermanentCount(playerB, "Blisterstick Shaman", 1);
        assertTappedCount("Plains", true, 4);
    }

    @Test
    public void test_TriggerNotChosenColorDamageOnYou_NoPrevent() {
        addCard(Zone.HAND, playerA, circle, 1);
        addCard(Zone.HAND, playerB, "Blisterstick Shaman", 1); // etb deals 1 to any target
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, circle);
        setChoice(playerA, "Green"); // color chosen

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Blisterstick Shaman");
        addTarget(playerB, playerA); // target for Shaman trigger
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}", null, "stack ability (When");
        // no valid choice

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 1);
        assertPermanentCount(playerB, "Blisterstick Shaman", 1);
        assertTappedCount("Plains", true, 4);
    }

    @Test
    public void test_ActivationDamageOnYou_Prevent() {
        addCard(Zone.HAND, playerA, circle, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Anaba Shaman", 1); // {R}, {T}: This creature deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, circle);
        setChoice(playerA, "Red"); // color chosen

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{R},", playerA);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}", null, "stack ability ({R}");
        setChoice(playerA, "Anaba Shaman"); // source to prevent from

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTappedCount("Anaba Shaman", true, 1);
        assertLife(playerA, 20);
        assertTappedCount("Plains", true, 4);
    }

    @Test
    public void test_ActivationNotChosenColorDamageOnYou_NoPrevent() {
        addCard(Zone.HAND, playerA, circle, 1);
        addCard(Zone.BATTLEFIELD, playerB, "Anaba Shaman", 1); // {R}, {T}: This creature deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, circle);
        setChoice(playerA, "Green"); // color chosen

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{R},", playerA);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}", null, "stack ability ({R}");
        // no valid choice

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTappedCount("Anaba Shaman", true, 1);
        assertLife(playerA, 20 - 1);
        assertTappedCount("Plains", true, 4);
    }
}
