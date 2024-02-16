package org.mage.test.cards.enchantments;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class EnchantedPermanentLKITest extends CardTestPlayerBase {

    // See issue #6326 and issue #11210

    private static final String skyrider = "Skyrider Trainee"; // {4}{W} 3/3
    // Skyrider Trainee has flying as long as it's enchanted.
    private static final String golem = "Thran Golem"; // {5} 3/3
    // As long as Thran Golem is enchanted, it gets +2/+2 and has flying, first strike, and trample.
    private static final String rest = "Winter's Rest"; // {1}{U} Aura
    // When Winter's Rest enters the battlefield, tap enchanted creature.
    // As long as you control another snow permanent, enchanted creature doesn't untap during its controller's untap step.
    private static final String bind = "Bind the Monster"; // {U} Aura
    // When Bind the Monster enters the battlefield, tap enchanted creature. It deals damage to you equal to its power.
    // Enchanted creature doesn't untap during its controller's untap step.
    private static final String disperse = "Disperse"; // {1}{U} Instant
    // Return target nonland permanent to its owner's hand.
    private static final String apathy = "Dreadful Apathy"; // {2}{W} Aura
    // Enchanted creature can't attack or block.
    private static final String apathyAbility = "{2}{W}: Exile enchanted creature.";
    private static final String flicker = "Flicker of Fate"; // {1}{W} Instant
    // Exile target creature or enchantment, then return it to the battlefield under its owner’s control.
    private static final String finesse = "Aura Finesse"; // {U} Instant
    // Attach target Aura you control to target creature. Draw a card.
    private static final String tundra = "Tundra"; // UW Dual land

    @Test
    public void testAuraRemoved() {
        addCard(Zone.BATTLEFIELD, playerA, tundra, 4);
        addCard(Zone.BATTLEFIELD, playerA, skyrider);
        addCard(Zone.HAND, playerA, rest);
        addCard(Zone.HAND, playerA, disperse);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rest, skyrider);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        checkAbility("Skyrider flying", 1, PhaseStep.PRECOMBAT_MAIN, playerA, skyrider, FlyingAbility.class, true);
        // Creature enchanted, but trigger on stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, disperse, rest);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, disperse, 1);
        assertTapped(skyrider, true);
        assertHandCount(playerA, rest, 1);
    }

    @Test
    public void testAuraRemovedDamaging() {
        addCard(Zone.BATTLEFIELD, playerA, tundra, 3);
        addCard(Zone.BATTLEFIELD, playerA, skyrider);
        addCard(Zone.HAND, playerA, bind);
        addCard(Zone.HAND, playerA, disperse);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bind, skyrider);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        checkAbility("Skyrider flying", 1, PhaseStep.PRECOMBAT_MAIN, playerA, skyrider, FlyingAbility.class, true);
        // Creature enchanted, but trigger on stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, disperse, bind);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, disperse, 1);
        assertTapped(skyrider, true);
        assertHandCount(playerA, bind, 1);
        assertLife(playerA, 17);
    }

    @Test
    public void testAuraFlickered() {
        addCard(Zone.BATTLEFIELD, playerA, tundra, 8);
        addCard(Zone.BATTLEFIELD, playerA, skyrider);
        addCard(Zone.BATTLEFIELD, playerA, golem);
        addCard(Zone.HAND, playerA, apathy);
        addCard(Zone.HAND, playerA, flicker);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, apathy, skyrider);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkAbility("Skyrider flying", 1, PhaseStep.PRECOMBAT_MAIN, playerA, skyrider, FlyingAbility.class, true);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, apathyAbility);
        // In response:
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, flicker, apathy);
        setChoice(playerA, golem); // when aura returns, choose what to enchant
        // Skyrider should be exiled by the ability resolving (LKI of Dreadful Apathy enchanted creature)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, flicker, 1);
        assertExileCount(skyrider, 1);
        assertAbility(playerA, golem, TrampleAbility.getInstance(), true); // now enchanted
    }

    @Test
    public void testAuraMoved() {
        addCard(Zone.BATTLEFIELD, playerA, tundra, 7);
        addCard(Zone.BATTLEFIELD, playerA, skyrider);
        addCard(Zone.BATTLEFIELD, playerA, golem);
        addCard(Zone.HAND, playerA, apathy);
        addCard(Zone.HAND, playerA, finesse);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, apathy, skyrider);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkAbility("Skyrider flying", 1, PhaseStep.PRECOMBAT_MAIN, playerA, skyrider, FlyingAbility.class, true);
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, apathyAbility);
        // In response:
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, finesse, apathy);
        addTarget(playerA, golem); // second target
        // Golem should be exiled by the ability resolving (same zcc Dreadful Apathy)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, finesse, 1);
        assertExileCount(golem, 1);
        assertAbility(playerA, skyrider, FlyingAbility.getInstance(), false); // no longer enchanted
    }

}
