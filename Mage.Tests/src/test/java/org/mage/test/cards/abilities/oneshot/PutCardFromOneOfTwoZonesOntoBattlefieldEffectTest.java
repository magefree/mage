package org.mage.test.cards.abilities.oneshot;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Alex-Vasile
 */
public class PutCardFromOneOfTwoZonesOntoBattlefieldEffectTest extends CardTestPlayerBase {

    // −5: You may put a creature card with mana value less than or equal to the number of lands you control onto the battlefield from your hand or graveyard with two +1/+1 counters on it.
    private static final String nissa = "Nissa of Shadowed Boughs";

    // {4}{B}{R}
    // When Swift Warkite enters the battlefield, you may put a creature card with mana value 3 or less from your hand or graveyard onto the battlefield.
    // That creature gains haste.
    // Return it to your hand at the beginning of the next end step.
    private static final String swift = "Swift Warkite";

    // Simple 1/1 for Swift Warkite to put on the battlefield with its ETB
    private static final String sliver = "Metallic Sliver";

    // −2: You may put an Equipment card from your hand or graveyard onto the battlefield.
    private static final String nahiri = "Nahiri, the Lithomancer";

    // Equipment cards for Nahiri
    private static final String vorpal = "Vorpal Sword";
    private static final String axe = "Bloodforged Battle-Axe";

    /**
     * Test with no matching cards in hand or graveyard.
     */
    @Test
    public void testNoMatches() {
        addCard(Zone.BATTLEFIELD, playerA, nahiri);

        setStrictChooseMode(true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2");
        // The player should not be prompted for a choice

        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    /**
     * Test with matching cards only in graveyard.
     */
    @Test
    public void testOnlyGraveyardHasMatches() {
        addCard(Zone.BATTLEFIELD, playerA, nahiri);
        addCard(Zone.GRAVEYARD, playerA, vorpal);

        setStrictChooseMode(true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2");
        setChoice(playerA, vorpal);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, vorpal, 1);
    }

    /**
     * Test with matching cards only in hand.
     */
    @Test
    public void testOnlyHandHasMatches() {
        addCard(Zone.BATTLEFIELD, playerA, nahiri);
        addCard(Zone.HAND, playerA, vorpal);

        setStrictChooseMode(true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2");
        setChoice(playerA, vorpal);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, vorpal, 1);
    }

    /**
     * Test with matching cards in both hand and graveyard.
     */
    @Test
    public void testBothHandAndGraveyardHaveMatches() {
        addCard(Zone.BATTLEFIELD, playerA, nahiri);
        addCard(Zone.HAND, playerA, vorpal);
        addCard(Zone.GRAVEYARD, playerA, axe);

        setStrictChooseMode(true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2");
        setChoice(playerA, "Yes"); // For player this choice is "Hand" but tests require "Yes"
        setChoice(playerA, vorpal);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, vorpal, 1);
    }

    /**
     * Test {@link mage.cards.n.NissaOfShadowedBoughs Nissa of Shadowed Boughs}
     * Starting loyalty = 4
     * You may put a creature card with mana value less than or equal to the number of lands you control
     * onto the battlefield from your hand or graveyard
     * with two +1/+1 counters on it.
     */
    @Test
    public void testNissaCanPlay() {
        addCard(Zone.BATTLEFIELD, playerA, nissa);
        addCard(Zone.HAND, playerA, swift);  // {4}{B}{R}
        addCard(Zone.HAND, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Mountain");

        setStrictChooseMode(true);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "-5");
        setChoice(playerA, swift);
        setChoice(playerA, "Yes"); // Say yes to Swift Warkite's ETB (no further choice needed since there are no possible options

        setStopAt(1, PhaseStep.END_TURN);

        execute();

        assertGraveyardCount(playerA, nissa, 1);
        assertPermanentCount(playerA, swift, 1);
        assertCounterCount(swift, CounterType.P1P1, 2);
    }

    /**
     * Test {@link mage.cards.n.NissaOfShadowedBoughs Nissa of Shadowed Boughs}
     * Starting loyalty = 4
     * You may put a creature card with mana value less than or equal to the number of lands you control
     * onto the battlefield from your hand or graveyard
     * with two +1/+1 counters on it.
     */
    @Test
    public void testNissaCantPlay() {
        addCard(Zone.BATTLEFIELD, playerA, nissa);
        addCard(Zone.HAND, playerA, swift);  // {4}{B}{R}
        addCard(Zone.HAND, playerA, "Mountain");


        setStrictChooseMode(true);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "-5");

        setStopAt(1, PhaseStep.END_TURN);

        execute();

        assertGraveyardCount(playerA, nissa, 1);
    }

    /**
     * Test {@link mage.cards.s.SwiftWarkite Swift Warkite}
     * When Swift Warkite enters the battlefield, you may put a creature card with converted mana cost 3 or less from your hand or graveyard onto the battlefield.
     * That creature gains haste.
     * Return it to your hand at the beginning of the next end step.
     */
    @Test
    public void testSwiftWarkite() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, swift);
        addCard(Zone.HAND, playerA, sliver);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, swift);
        setChoice(playerA, "Yes"); // Yes to activating Swift Warkite's ETB
        setChoice(playerA, sliver); // Pick the sliver for the ETB

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, sliver, 1);
        assertAbility(playerA, sliver, HasteAbility.getInstance(), true);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);

        execute();

        assertHandCount(playerA, sliver, 1);
    }
}
