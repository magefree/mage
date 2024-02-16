
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PhyrexianMetamorphTest extends CardTestPlayerBase {

    @Test
    public void testCopyCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types.
        addCard(Zone.HAND, playerA, "Phyrexian Metamorph"); // {3}{U/P}
        addCard(Zone.HAND, playerA, "Cloudshift");

        //Flying
        // Vanishing 3 (This permanent enters the battlefield with three time counters on it. At the beginning of your upkeep, remove a time counter from it. When the last is removed, sacrifice it.)
        // When Aven Riftwatcher enters the battlefield or leaves the battlefield, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Aven Riftwatcher");  // 2/3

        // When Ponyback Brigade enters the battlefield or is turned face up, put three 1/1 red Goblin creature tokens onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Ponyback Brigade");  // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Metamorph");
        setChoice(playerA, "Aven Riftwatcher");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Aven Riftwatcher");
        setChoice(playerA, "Ponyback Brigade");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 22);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Cloudshift", 1);

        assertPermanentCount(playerA, "Ponyback Brigade", 1);
        assertPermanentCount(playerA, "Goblin Token", 3);

    }

    /**
     * An opponent cast Phyrexian Metamorph and cloned another opponent's
     * Maelstrom Wanderer(his Commander). The first opponent then dealt combat
     * damage with Brago, King Eternal and chose to flicker several permanents,
     * including the Phyrexian Metamorph/Maelstrom Wanderer, but that player was
     * not able to choose a new creature to clone when the Phyrexian Metamorph
     * re-entered the battlefield.
     */
    @Test
    public void testFlickerWithBrago() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types.
        addCard(Zone.HAND, playerA, "Phyrexian Metamorph"); // {3}{U/P}

        // Flying
        // When Brago, King Eternal deals combat damage to a player, exile any number of target nonland permanents you control, then return those cards to the battlefield under their owner's control.
        addCard(Zone.BATTLEFIELD, playerA, "Brago, King Eternal"); // 2/4

        // Creatures you control have haste.
        // Cascade, cascade
        addCard(Zone.BATTLEFIELD, playerB, "Maelstrom Wanderer");  // 7/5
        // When Ponyback Brigade enters the battlefield or is turned face up, put three 1/1 red Goblin creature tokens onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Ponyback Brigade");  // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Metamorph");
        setChoice(playerA, "Maelstrom Wanderer");

        attack(3, playerA, "Brago, King Eternal");
        addTarget(playerA, "Maelstrom Wanderer");
        setChoice(playerA, "Ponyback Brigade");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 18);

        assertPermanentCount(playerA, "Ponyback Brigade", 1);
        assertPermanentCount(playerA, "Goblin Token", 3);

    }

    /**
     * I had a Harmonic Sliver, my opponent played Phyrexian Metamorph copying
     * it. The resulting copy only had one instance of the artifact-enchantment
     * destroying ability, where it should have had two of them and triggered
     * twice (the Metamorph might have nothing to do with this)
     */
    @Test
    public void testHarmonicSliver() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types.
        addCard(Zone.HAND, playerA, "Phyrexian Metamorph"); // {3}{U/P}

        addCard(Zone.BATTLEFIELD, playerB, "Alloy Myr", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Kitesail", 1);
        // All Slivers have "When this permanent enters the battlefield, destroy target artifact or enchantment."
        addCard(Zone.BATTLEFIELD, playerB, "Harmonic Sliver"); // 2/4

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phyrexian Metamorph");
        setChoice(playerA, "Harmonic Sliver");
        addTarget(playerA, "Alloy Myr");
        addTarget(playerA, "Kitesail");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Harmonic Sliver", 1);

        assertGraveyardCount(playerB, "Alloy Myr", 1);
        assertGraveyardCount(playerB, "Kitesail", 1);

    }

    /**
     * If a Harmonic Sliver enters the battlefield the controller has to destroy
     * one artifact or enchantment
     */
    @Test
    public void testHarmonicSliverNative1() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // All Slivers have "When this permanent enters the battlefield, destroy target artifact or enchantment."
        addCard(Zone.HAND, playerA, "Harmonic Sliver");

        addCard(Zone.BATTLEFIELD, playerB, "Alloy Myr", 2); // 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Harmonic Sliver");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Harmonic Sliver", 1);

        assertGraveyardCount(playerB, "Alloy Myr", 1);

    }

    /**
     * If a Harmonic Sliver enters the battlefield and there is already one on
     * the battlefield the controller has to destroy two artifacts or
     * enchantments
     */
    @Test
    public void testHarmonicSliverNative2() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        addCard(Zone.HAND, playerA, "Harmonic Sliver");

        addCard(Zone.BATTLEFIELD, playerB, "Alloy Myr", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Kitesail", 1);
        // All Slivers have "When this permanent enters the battlefield, destroy target artifact or enchantment."
        addCard(Zone.BATTLEFIELD, playerB, "Harmonic Sliver"); // 2/4

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Harmonic Sliver");
        addTarget(playerA, "Alloy Myr");
        addTarget(playerA, "Kitesail");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Harmonic Sliver", 1);

        assertGraveyardCount(playerB, "Alloy Myr", 1);
        assertGraveyardCount(playerB, "Kitesail", 1);

    }

    /**
     * I cast Show and Tell, and put Sheoldred, Whispering One into play and my
     * opponent put Phyrexian Metamorph into play and they were able to clone my
     * Sheoldred, Whispering One.
     *
     * 6/1/2011 If Phyrexian Metamorph somehow enters the battlefield at the
     * same time as another permanent (due to Mass Polymorph or Liliana Vess's
     * third ability, for example), Phyrexian Metamorph can't become a copy of
     * that permanent. You may only choose a permanent that's already on the
     * battlefield.
     *
     * 400.6. If an object would move from one zone to another, determine what
     * event is moving the object. If the object is moving to a public zone, all
     * players look at it to see if it has any abilities that would affect the
     * move. Then any appropriate replacement effects, whether they come from
     * that object or from elsewhere, are applied to that event. If any effects
     * or rules try to do two or more contradictory or mutually exclusive things
     * to a particular object, that object's controller -- or its owner if it
     * has no controller -- chooses which effect to apply, and what that effect
     * does. (Note that multiple instances of the same thing may be mutually
     * exclusive; for example, two simultaneous "destroy" effects.) Then the
     * event moves the object.
     */
    @Test
    public void testShowAndTell() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Each player may put an artifact, creature, enchantment, or land card from their hand onto the battlefield.
        addCard(Zone.HAND, playerA, "Show and Tell"); // SORCERY {2}{U}

        // Swampwalk
        // At the beginning of your upkeep, return target creature card from your graveyard to the battlefield.
        // At the beginning of each opponent's upkeep, that player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Sheoldred, Whispering One");

        addCard(Zone.HAND, playerB, "Phyrexian Metamorph");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Show and Tell");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Sheoldred, Whispering One", 1);
        assertPermanentCount(playerB, "Sheoldred, Whispering One", 0);

        assertGraveyardCount(playerB, "Phyrexian Metamorph", 1);

    }
}
