package org.mage.test.cards.single.blb;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author jimga150
 */
public class AlaniaDivergentStormTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AlaniaDivergentStorm Alania, Divergent Storm} {3}{U}{R}
     * Legendary Creature — Otter Wizard
     * Whenever you cast a spell, if it's the first instant spell, the first sorcery spell, or the first Otter spell
     * other than Alania you've cast this turn, you may have target opponent draw a card. If you do, copy that spell.
     * You may choose new targets for the copy.
     */
    private static final String alania = "Alania, Divergent Storm";

    @Test
    public void test_TwoOtters() {
        // Test that the "first Otter spell other than Alania you've cast this turn" clause works

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Coruscation Mage");
        addCard(Zone.HAND, playerA, alania);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, alania, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Coruscation Mage", true);
        setChoice(playerA, "No"); // Offspring?
        addTarget(playerA, playerB); // Who draws?
        setChoice(playerA, "Yes"); // Copy spell?

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Copied
        assertPermanentCount(playerA, "Coruscation Mage", 2);
        // Not copied
        assertPermanentCount(playerA, alania, 1);
        // Opponent drew a card
        assertHandCount(playerB, 1);
    }

    @Test
    public void test_TwoOttersNextTurn() {
        // Test that the "first Otter spell other than Alania you've cast this turn" clause works on the next turn

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Coruscation Mage");
        addCard(Zone.HAND, playerA, "Stormcatch Mentor");
        addCard(Zone.HAND, playerA, alania);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, alania, true);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Stormcatch Mentor", true);
        addTarget(playerA, playerB); // Who draws?
        setChoice(playerA, "Yes"); // Copy spell?
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Coruscation Mage", true);
        setChoice(playerA, "No"); // Offspring?


        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        // Copied
        assertPermanentCount(playerA, "Stormcatch Mentor", 2);
        // Not copied
        assertPermanentCount(playerA, alania, 1);
        assertPermanentCount(playerA, "Coruscation Mage", 1);
        // Opponent drew a card (plus the one for turn draw)
        assertHandCount(playerB, 1 + 1);
    }

    @Test
    public void test_ThreeOttersAdventureInstant() {
        // Test that the "first Otter spell other than Alania you've cast this turn" clause excludes the third otter cast on the same turn you cast Alania
        // Also throws in an adventure otter, cast for creature and instant

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.HAND, playerA, "Coruscation Mage");
        addCard(Zone.HAND, playerA, "Frolicking Familiar", 2);
        addCard(Zone.HAND, playerA, alania);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, alania, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Frolicking Familiar", true);
        addTarget(playerA, playerB); // Who draws?
        setChoice(playerA, "Yes"); // Copy spell?
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Coruscation Mage", true);
        setChoice(playerA, "No"); // Offspring?
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blow Off Steam", true);
        setChoice(playerA, "Whenever you cast an instant", 2); // Add Frolicking Familiar triggers first
        setChoice(playerA, "Whenever you cast a noncreature"); // Add Coruscation Mage trigger
        // Alania's trigger will add last
        addTarget(playerA, playerB); // Who draws?
        setChoice(playerA, "Yes"); // Copy spell?
        addTarget(playerA, playerB);
        setChoice(playerA, "No"); // Change target?

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Copied
        assertPermanentCount(playerA, "Frolicking Familiar", 2);
        // Got boost from single cast of Blow Off Steam (copy =/= cast)
        assertPowerToughness(playerA, "Frolicking Familiar", 3, 3);
        // Not copied
        assertPermanentCount(playerA, "Coruscation Mage", 1);
        // Not copied
        assertPermanentCount(playerA, alania, 1);
        // Blow Off Steam copied, pinged twice, plus the Coruscation Mage ping
        assertLife(playerB, currentGame.getStartingLife() - 3);
        // opponent drew 2 cards
        assertHandCount(playerB, 2);
    }

    @Test
    public void test_TwoInstants() {
        // Test that the "first instant you've cast this turn" clause works

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Acrobatic Leap");
        addCard(Zone.HAND, playerA, "Ancestral Recall");
        addCard(Zone.HAND, playerA, alania);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, alania, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Acrobatic Leap", true);
        addTarget(playerA, playerB); // Who draws?
        setChoice(playerA, "Yes"); // Copy spell?
        addTarget(playerA, alania); // Target creature
        setChoice(playerA, "No"); // Change target?
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ancestral Recall", true);
        addTarget(playerA, playerA); // Target player

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Copied: Acrobatic Leap
        assertPowerToughness(playerA, alania, 3 + 2, 5 + 2*3);
        // Not copied: Ancestral Recall
        assertHandCount(playerA, 3);
        // Opponent drew a card
        assertHandCount(playerB, 1);
    }

    @Test
    public void test_TwoSorceries() {
        // Test that the "first sorcery you've cast this turn" clause works
        // Also copies an adventure sorcery

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.HAND, playerA, "Faerie Guidemother"); // adventure card
        addCard(Zone.HAND, playerA, "Maximize Velocity");
        addCard(Zone.HAND, playerA, alania);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, alania, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gift of the Fae", true);
        addTarget(playerA, playerB); // Who draws?
        setChoice(playerA, "Yes"); // Copy spell?
        addTarget(playerA, alania); // Target creature
        setChoice(playerA, "No"); // Change target?
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Maximize Velocity", true);
        addTarget(playerA, alania); // Target creature

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Copied: Gift of the Fae, Not copied: Maximize Velocity
        assertPowerToughness(playerA, alania, 3 + 2*2 + 1, 5 + 2 + 1);
        assertAbility(playerA, alania, FlyingAbility.getInstance(), true);
        assertAbility(playerA, alania, HasteAbility.getInstance(), true);
        // Opponent drew a card
        assertHandCount(playerB, 1);
    }

    @Test
    public void test_OtherCard() {
        // Test that card cast that is first of its type but is not an instant, sorcery, or otter will not trigger Alania

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Memnite");
        addCard(Zone.HAND, playerA, "Arcane Signet");
        addCard(Zone.HAND, playerA, "Ajani's Welcome");
        addCard(Zone.BATTLEFIELD, playerA, alania);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcane Signet", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ajani's Welcome", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Nothing copied
        assertPermanentCount(playerA, "Memnite", 1);
        assertPermanentCount(playerA, "Arcane Signet", 1);
        assertPermanentCount(playerA, "Ajani's Welcome", 1);
        // Opponent did not draw a card
        assertHandCount(playerB, 0);
    }

    @Test
    public void test_TwoOttersOpponentsHexproof() {
        // Test that Alania cannot copy spells if all opponents have hexproof

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.HAND, playerA, "Coruscation Mage");
        addCard(Zone.BATTLEFIELD, playerA, alania);
        addCard(Zone.HAND, playerB, "Blossoming Calm");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Blossoming Calm");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Coruscation Mage", true);
        setChoice(playerA, "No"); // Offspring?

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        // Not Copied
        assertPermanentCount(playerA, "Coruscation Mage", 1);
    }

}
